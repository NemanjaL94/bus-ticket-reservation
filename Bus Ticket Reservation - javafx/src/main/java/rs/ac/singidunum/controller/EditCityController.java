package rs.ac.singidunum.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
import rs.ac.singidunum.data.StopDto;

public class EditCityController implements Initializable {

	@FXML
	private TableView<StopDto> cityTable;

	@FXML
	private TableColumn<StopDto, Integer> idColumn;

	@FXML
	private TableColumn<StopDto, String> cityColumn;

	@FXML
	private TextField idTextField;

	@FXML
	private TextField cityTextField;

	@FXML
	private Label statusLabel;

	private ObservableList<StopDto> stopList = FXCollections.observableArrayList();

	public void fillEditData(StopDto stopDto) {
		idTextField.setText(stopDto.getStopId().toString());
		cityTextField.setText(stopDto.getCityName());
	}

	public void handleUpdateButton() {

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Integer stopId = Integer.parseInt(idTextField.getText());

		Call<StopDto> call = apiInterface.updateStop(LoginWindowController.getAdminDto().getToken(), stopId,
				cityTextField.getText().toString());

		call.enqueue(new Callback<StopDto>() {

			@Override
			public void onResponse(Call<StopDto> call, Response<StopDto> response) {
				switch (response.code()) {
				case 200:
					StopDto stopDto = cityTable.getSelectionModel().getSelectedItem();
					for (int i = 0; i < stopList.size(); i++) {
						if (stopId == stopList.get(i).getStopId()) {
							stopDto.setCity(cityTextField.getText());
							cityTable.refresh();
						}
					}
					Platform.runLater(() -> statusLabel.setText("Update was successful"));
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Failed to update city"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				default:
					Platform.runLater(() -> statusLabel.setText("City name is already in use"));
				}

			}

			@Override
			public void onFailure(Call<StopDto> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));
			}
		});
	}

	public void handleDeleteButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Integer stopId = Integer.parseInt(idTextField.getText());

		if (deleteConfirmation()) {
			Call<Void> call = apiInterface.deleteStop(LoginWindowController.getAdminDto().getToken(), stopId);
			call.enqueue(new Callback<Void>() {

				@Override
				public void onResponse(Call<Void> call, Response<Void> response) {
					switch (response.code()) {
					case 200:
						for (int i = 0; i < stopList.size(); i++) {
							if (stopId == stopList.get(i).getStopId()) {
								stopList.remove(i);
								cityTable.refresh();
							}
						}
						Platform.runLater(() -> statusLabel.setText("City was removed successfully"));
						break;
					case 400:
						Platform.runLater(() -> statusLabel.setText("Failed to delete city"));
						break;
					case 403:
						Platform.runLater(() -> statusLabel.setText("Authentication failure"));
						break;
					default:
						Platform.runLater(() -> statusLabel.setText("Cannot delete city"));
					}
				}

				@Override
				public void onFailure(Call<Void> call, Throwable t) {
					Platform.runLater(() -> statusLabel.setText("Server error"));
				}
			});
		}
	}

	public void initialize(URL location, ResourceBundle resources) {

		idColumn.setCellValueFactory(new PropertyValueFactory<StopDto, Integer>("stopId"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<StopDto, String>("cityName"));

		cityTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> fillEditData(newValue));

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<List<StopDto>> call = apiInterface.getStops(LoginWindowController.getAdminDto().getToken());

		call.enqueue(new Callback<List<StopDto>>() {

			@Override
			public void onResponse(Call<List<StopDto>> call, Response<List<StopDto>> response) {
				switch (response.code()) {
				case 200:
					for (StopDto stopDto : response.body()) {
						stopList.add(stopDto);
					}
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Could not load cities"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				}
			}

			@Override
			public void onFailure(Call<List<StopDto>> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));

			}
		});

		cityTable.setItems(stopList);

	}

	public boolean deleteConfirmation() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Confirmation");
		alert.setHeaderText("City deletion");
		alert.setContentText("You are about to delete a city from database");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

}
