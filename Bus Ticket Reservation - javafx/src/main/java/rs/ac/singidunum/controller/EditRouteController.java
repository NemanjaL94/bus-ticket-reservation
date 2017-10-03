package rs.ac.singidunum.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.singidunum.api.ApiClient;
import rs.ac.singidunum.api.ApiInterface;
import rs.ac.singidunum.data.FullRouteDto;
import rs.ac.singidunum.data.UpdateRouteDto;

public class EditRouteController implements Initializable {

	@FXML
	private TableView<FullRouteDto> routeTableView;

	@FXML
	private TableColumn<FullRouteDto, Integer> routeIdColumn;

	@FXML
	private TableColumn<FullRouteDto, String> cityColumn;

	@FXML
	private TableColumn<FullRouteDto, String> timeColumn;

	@FXML
	private TableColumn<FullRouteDto, String> dateColumn;

	@FXML
	private TableColumn<FullRouteDto, Double> priceColumn;

	@FXML
	private TextField cityTextField;

	@FXML
	private ComboBox<String> hoursComboBox;

	@FXML
	private ComboBox<String> minutesComboBox;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextField priceTextField;

	@FXML
	private Label statusLabel;

	private Integer routeStopId;

	ObservableList<String> hours = FXCollections.observableArrayList();
	ObservableList<String> minutes = FXCollections.observableArrayList();
	ObservableList<FullRouteDto> fullRouteList = FXCollections.observableArrayList();

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

	public void handleUpdateButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

		String arrivalTime = hoursComboBox.getValue() + ":" + minutesComboBox.getValue();
		String arrivalDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(Date.from(datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		Call<UpdateRouteDto> call = apiInterface.updateRouteStop(LoginWindowController.getAdminDto().getToken(),
				new UpdateRouteDto(routeStopId, arrivalTime, arrivalDate, Integer.parseInt(priceTextField.getText())));
		call.enqueue(new Callback<UpdateRouteDto>() {

			@Override
			public void onResponse(Call<UpdateRouteDto> call, Response<UpdateRouteDto> response) {
				switch (response.code()) {
				case 200:
					FullRouteDto routeStopDto = routeTableView.getSelectionModel().getSelectedItem();
					for (int i = 0; i < fullRouteList.size(); i++) {
						if (routeStopId == fullRouteList.get(i).getRouteStopId()) {
							routeStopDto.setArrivalTime(response.body().getArrivalTime());
							routeStopDto.setArrivalDate(response.body().getArrivalDate());
							routeStopDto.setFare(response.body().getPrice());

							routeTableView.refresh();
						}
					}
					Platform.runLater(() -> statusLabel.setText("Update was successful"));
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Failed to update route stop"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				default:
					Platform.runLater(() -> statusLabel.setText("Route stop already exists"));
				}
			}

			@Override
			public void onFailure(Call<UpdateRouteDto> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));
			}
		});
	}

	public void handleDeleteButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

		if (deleteConfirmation()) {
			Call<Void> call = apiInterface.deleteRouteStop(LoginWindowController.getAdminDto().getToken(), routeStopId);
			call.enqueue(new Callback<Void>() {

				@Override
				public void onResponse(Call<Void> call, Response<Void> response) {
					switch (response.code()) {
					case 200:
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								for (int i = 0; i < fullRouteList.size(); i++) {
									if (routeStopId == fullRouteList.get(i).getRouteStopId()) {
										fullRouteList.remove(i);
										routeTableView.refresh();
									}
								}
								
							}
						});
						Platform.runLater(() -> statusLabel.setText("Route stop was removed successfully"));
						break;
					case 400:
						Platform.runLater(() -> statusLabel.setText("Failed to delete route stop"));
						break;
					case 403:
						Platform.runLater(() -> statusLabel.setText("Authentication failure"));
						break;
					default:
						Platform.runLater(() -> statusLabel.setText("Cannot delete route stop"));
					}
				}

				@Override
				public void onFailure(Call<Void> call, Throwable t) {
					Platform.runLater(() -> statusLabel.setText("Server error"));
				}
			});
		}
	}

	public void fillEditData(FullRouteDto fullRouteDto) {
		routeStopId = fullRouteDto.getRouteStopId();
		cityTextField.setText(fullRouteDto.getCityName());
		hoursComboBox.setValue(fullRouteDto.getArrivalTime().substring(0, 2));
		minutesComboBox.setValue(fullRouteDto.getArrivalTime().substring(3, 5));
		priceTextField.setText(fullRouteDto.getFare().toString());
		datePicker.setValue(LocalDate.parse(fullRouteDto.getArrivalDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		routeIdColumn.setCellValueFactory(new PropertyValueFactory<FullRouteDto, Integer>("routeId"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<FullRouteDto, String>("cityName"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<FullRouteDto, String>("arrivalTime"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<FullRouteDto, String>("arrivalDate"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<FullRouteDto, Double>("fare"));

		priceTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("^[0-9]*$")) {
					Platform.runLater(() -> priceTextField.clear());
				}
			}
		});

		routeTableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> fillEditData(newValue));

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<List<FullRouteDto>> call = apiInterface.getFullRoutes(LoginWindowController.getAdminDto().getToken());
		call.enqueue(new Callback<List<FullRouteDto>>() {

			@Override
			public void onResponse(Call<List<FullRouteDto>> call, Response<List<FullRouteDto>> response) {
				switch (response.code()) {
				case 200:
					for (FullRouteDto fullRouteDto : response.body()) {
						fullRouteList.add(fullRouteDto);
					}
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Could not load routes"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				}
			}

			@Override
			public void onFailure(Call<List<FullRouteDto>> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));

			}
		});

		setTime();
		routeTableView.setItems(fullRouteList);

	}

	public boolean deleteConfirmation() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Confirmation");
		alert.setHeaderText("Route stop deletion");
		alert.setContentText("You are about to delete a route stop from database");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
}
