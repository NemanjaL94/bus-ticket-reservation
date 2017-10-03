package rs.ac.singidunum.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.singidunum.api.ApiClient;
import rs.ac.singidunum.api.ApiInterface;
import rs.ac.singidunum.data.BusCompanyDto;
import rs.ac.singidunum.data.RouteDto;
import rs.ac.singidunum.data.RouteStopDto;
import rs.ac.singidunum.data.StopDto;

public class AddRouteController implements Initializable {

	@FXML
	private ComboBox<BusCompanyDto> busCompanyComboBox;

	@FXML
	private TextField availableTicketsTextField;

	@FXML
	private Button addRouteButton;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox<String> hoursComboBox;

	@FXML
	private ComboBox<String> minutesComboBox;

	@FXML
	private ComboBox<StopDto> cityComboBox;

	@FXML
	private TextField priceTextField;

	@FXML
	private Button addStopButton;

	@FXML
	private ListView<String> infoListView;

	@FXML
	private Button confirmButton;

	@FXML
	private Label confirmLabel;

	@FXML
	private Label routeLabel;

	private Long routeId;

	private List<RouteStopDto> routeStops;

	ObservableList<String> hours = FXCollections.observableArrayList();
	ObservableList<String> minutes = FXCollections.observableArrayList();
	ObservableList<StopDto> stops = FXCollections.observableArrayList();
	ObservableList<BusCompanyDto> busCompanies = FXCollections.observableArrayList();

	public void manageComponents() {
		datePicker.setDisable(false);
		hoursComboBox.setDisable(false);
		minutesComboBox.setDisable(false);
		cityComboBox.setDisable(false);
		priceTextField.setDisable(false);
		addStopButton.setDisable(false);
		busCompanyComboBox.setDisable(true);
		availableTicketsTextField.setDisable(true);
		addRouteButton.setDisable(true);
	}

	public void handleAddRouteButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		int busCompanyId = busCompanyComboBox.getValue().getBusCompanyId();
		int availableTickets = Integer.parseInt(availableTicketsTextField.getText());

		Call<Long> call = apiInterface.createRoute(LoginWindowController.getAdminDto().getToken(),
				new RouteDto(busCompanyId, availableTickets));
		call.enqueue(new Callback<Long>() {

			@Override
			public void onResponse(Call<Long> call, Response<Long> response) {
				switch (response.code()) {
				case 201:
					routeId = response.body();
					manageComponents();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							infoListView.getItems().add(busCompanyComboBox.getValue().toString());
							infoListView.getItems().add(availableTickets + "");
						}
					});
					break;
				case 400:
					Platform.runLater(() -> routeLabel.setText("Cannot create route"));
					break;
				case 403:
					Platform.runLater(() -> routeLabel.setText("Authentication failure"));
					break;
				}
			}

			@Override
			public void onFailure(Call<Long> call, Throwable t) {
				Platform.runLater(() -> routeLabel.setText("Server error"));

			}
		});
	}

	public void handleAddStopButton() {

		String arrivalTime = hoursComboBox.getValue() + ":" + minutesComboBox.getValue();
		String arrivalDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(Date.from(datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		RouteStopDto routeStopDto = new RouteStopDto(busCompanyComboBox.getValue().getBusCompanyId(),
				cityComboBox.getValue().getStopId(), arrivalTime, arrivalDate,
				Integer.parseInt(priceTextField.getText()));
		infoListView.getItems().add(cityComboBox.getValue().getCityName() + " " + routeStopDto.getArrivalDate() + " "
				+ routeStopDto.getArrivalTime() + " " + routeStopDto.getPrice());
		routeStops.add(routeStopDto);

		if (routeStops.size() >= 2 && confirmButton.isDisable()) {
			confirmButton.setDisable(false);
		}

	}

	public void handleConfirmButton() {

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<List<RouteStopDto>> call = apiInterface.createRouteStops(LoginWindowController.getAdminDto().getToken(),
				routeStops, routeId);
		call.enqueue(new Callback<List<RouteStopDto>>() {

			@Override
			public void onResponse(Call<List<RouteStopDto>> call, Response<List<RouteStopDto>> response) {
				switch (response.code()) {
				case 201:
					Platform.runLater(() -> confirmLabel.setText("Route was successfully created"));
					break;
				case 400:
					Platform.runLater(() -> confirmLabel.setText("Cannot create routes"));
					break;
				case 403:
					Platform.runLater(() -> confirmLabel.setText("Authentication failure"));
					break;
				case 500:
					Platform.runLater(() -> confirmLabel.setText("Duplicate route stop"));
					break;

				}
			}

			@Override
			public void onFailure(Call<List<RouteStopDto>> call, Throwable t) {
				Platform.runLater(() -> confirmLabel.setText("Server error"));

			}
		});
	}

	public void setBusCompanies(ApiInterface apiInterface) {
		Call<List<BusCompanyDto>> call = apiInterface.getBusCompanies(LoginWindowController.getAdminDto().getToken());
		call.enqueue(new Callback<List<BusCompanyDto>>() {

			@Override
			public void onResponse(Call<List<BusCompanyDto>> call, Response<List<BusCompanyDto>> response) {
				switch (response.code()) {
				case 200:
					for (BusCompanyDto busCompanyDto : response.body()) {
						busCompanies.add(busCompanyDto);
					}
					break;

				}
			}

			@Override
			public void onFailure(Call<List<BusCompanyDto>> call, Throwable t) {
				t.printStackTrace();
			}
		});
		busCompanyComboBox.setItems(busCompanies);
	}

	public void setStops(ApiInterface apiInterface) {
		Call<List<StopDto>> call = apiInterface.getStops(LoginWindowController.getAdminDto().getToken());
		call.enqueue(new Callback<List<StopDto>>() {

			@Override
			public void onResponse(Call<List<StopDto>> call, Response<List<StopDto>> response) {
				switch (response.code()) {
				case 200:
					for (StopDto stopDto : response.body()) {
						stops.add(stopDto);
					}
					break;
				}
			}

			@Override
			public void onFailure(Call<List<StopDto>> call, Throwable t) {
				t.printStackTrace();
			}
		});
		cityComboBox.setItems(stops);
	}

	public void setTime() {

		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				hours.add("0" + i);
				minutes.add("0" + i);

			} else if (i >= 10 && i < 24) {
				hours.add(i + "");
				minutes.add(i + "");
			} else {
				minutes.add(i + "");
			}
		}

		hoursComboBox.setItems(hours);
		minutesComboBox.setItems(minutes);
	}

	public void initialize(URL location, ResourceBundle resources) {

		availableTicketsTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("^[0-9]*$")) {
					Platform.runLater(() -> availableTicketsTextField.clear());
				}
			}
		});

		priceTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("^[0-9]*$")) {
					Platform.runLater(() -> priceTextField.clear());
				}
			}
		});

		routeStops = new ArrayList<>();

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		setBusCompanies(apiInterface);
		setStops(apiInterface);
		setTime();

	}

}
