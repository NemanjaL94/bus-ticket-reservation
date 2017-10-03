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
import rs.ac.singidunum.data.BusCompanyDto;

public class EditBusCompanyController implements Initializable {

	@FXML
	private TableView<BusCompanyDto> busCompanyTable;

	@FXML
	private TableColumn<BusCompanyDto, Integer> idColumn;

	@FXML
	private TableColumn<BusCompanyDto, String> busCompanyColumn;

	@FXML
	private TextField idTextField;

	@FXML
	private TextField busCompanyTextField;

	@FXML
	private Label statusLabel;

	private ObservableList<BusCompanyDto> busCompanyList = FXCollections.observableArrayList();

	public void fillEditData(BusCompanyDto busCompanyDto) {
		idTextField.setText(busCompanyDto.getBusCompanyId().toString());
		busCompanyTextField.setText(busCompanyDto.getBusCompanyName());
	}

	public void handleUpdateButton() {

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Integer busCompanyId = Integer.parseInt(idTextField.getText());

		Call<BusCompanyDto> call = apiInterface.updateBusCompany(LoginWindowController.getAdminDto().getToken(),
				busCompanyId, busCompanyTextField.getText());

		call.enqueue(new Callback<BusCompanyDto>() {

			@Override
			public void onResponse(Call<BusCompanyDto> call, Response<BusCompanyDto> response) {
				switch (response.code()) {
				case 200:
					BusCompanyDto busCompanyDto = busCompanyTable.getSelectionModel().getSelectedItem();
					for (int i = 0; i < busCompanyList.size(); i++) {
						if (busCompanyId == busCompanyList.get(i).getBusCompanyId()) {
							busCompanyDto.setBusCompanyName(response.body().getBusCompanyName());
							busCompanyTable.refresh();
						}
					}
					Platform.runLater(() -> statusLabel.setText("Update was successful"));
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Failed to update bus company"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				default:
					Platform.runLater(() -> statusLabel.setText("Bus company name is already in use"));
				}

			}

			@Override
			public void onFailure(Call<BusCompanyDto> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));
			}
		});
	}

	public void handleDeleteButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Integer busCompanyId = Integer.parseInt(idTextField.getText());

		if (deleteConfirmation()) {
			Call<Void> call = apiInterface.deleteBusCompany(LoginWindowController.getAdminDto().getToken(),
					busCompanyId);
			call.enqueue(new Callback<Void>() {

				@Override
				public void onResponse(Call<Void> call, Response<Void> response) {
					switch (response.code()) {
					case 200:
						for (int i = 0; i < busCompanyList.size(); i++) {
							if (busCompanyId == busCompanyList.get(i).getBusCompanyId()) {
								busCompanyList.remove(i);
								busCompanyTable.refresh();
							}
						}
						Platform.runLater(() -> statusLabel.setText("Bus company was removed successfully"));
						break;
					case 400:
						Platform.runLater(() -> statusLabel.setText("Failed to delete bus company"));
						break;
					case 403:
						Platform.runLater(() -> statusLabel.setText("Authentication failure"));
						break;
					default:
						Platform.runLater(() -> statusLabel.setText("Cannot delete stop"));
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

		idColumn.setCellValueFactory(new PropertyValueFactory<BusCompanyDto, Integer>("busCompanyId"));
		busCompanyColumn.setCellValueFactory(new PropertyValueFactory<BusCompanyDto, String>("busCompanyName"));

		busCompanyTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> fillEditData(newValue));

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<List<BusCompanyDto>> call = apiInterface.getBusCompanies(LoginWindowController.getAdminDto().getToken());
		call.enqueue(new Callback<List<BusCompanyDto>>() {

			@Override
			public void onResponse(Call<List<BusCompanyDto>> call, Response<List<BusCompanyDto>> response) {
				switch (response.code()) {
				case 200:
					for (BusCompanyDto busCompanyDto : response.body()) {
						busCompanyList.add(busCompanyDto);
					}
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Could not load bus companies"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				}
			}

			@Override
			public void onFailure(Call<List<BusCompanyDto>> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));

			}
		});

		for (BusCompanyDto b : busCompanyList) {
			System.out.println(b);
		}
		busCompanyTable.setItems(busCompanyList);
	}

	public boolean deleteConfirmation() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Confirmation");
		alert.setHeaderText("Bus Company deletion");
		alert.setContentText("You are about to delete a bus company from database");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

}
