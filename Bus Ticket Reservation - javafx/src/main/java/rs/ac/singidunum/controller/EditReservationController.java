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
import rs.ac.singidunum.data.ReservationDto;

public class EditReservationController implements Initializable {
	@FXML
	private TableView<ReservationDto> reservationTableView;

	@FXML
	private TableColumn<ReservationDto, Integer> userIdColumn;

	@FXML
	private TableColumn<ReservationDto, Integer> routeIdColumn;

	@FXML
	private TableColumn<ReservationDto, Integer> ticketsColumn;

	@FXML
	private TableColumn<ReservationDto, Double> priceColumn;

	@FXML
	private TextField userIdTextField;

	@FXML
	private TextField routeIdTextField;

	@FXML
	private TextField ticketsTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private Label statusLabel;

	private Long reservationId;

	private ObservableList<ReservationDto> reservationList = FXCollections.observableArrayList();

	public void fillEditData(ReservationDto reservationDto) {
		reservationId = reservationDto.getReservationId();
		userIdTextField.setText(reservationDto.getUserId().toString());
		routeIdTextField.setText(reservationDto.getRouteId().toString());
		ticketsTextField.setText(reservationDto.getNumberOfTickets().toString());
		priceTextField.setText(reservationDto.getPrice().toString());
	}

	public void handleUpdateButton() {

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

		Call<ReservationDto> call = apiInterface.updateReservation(LoginWindowController.getAdminDto().getToken(),
				reservationId, Integer.parseInt(ticketsTextField.getText()),
				Double.parseDouble(priceTextField.getText()));

		call.enqueue(new Callback<ReservationDto>() {

			@Override
			public void onResponse(Call<ReservationDto> call, Response<ReservationDto> response) {
				switch (response.code()) {
				case 200:
					ReservationDto reservationDto = reservationTableView.getSelectionModel().getSelectedItem();
					for (int i = 0; i < reservationList.size(); i++) {
						if (reservationId == reservationList.get(i).getReservationId()) {
							reservationDto.setNumberOfTickets(response.body().getNumberOfTickets());
							reservationDto.setPrice(response.body().getPrice());
							reservationTableView.refresh();
						}
					}
					Platform.runLater(() -> statusLabel.setText("Update was successful"));
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Failed to update reservation"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				default:
					Platform.runLater(() -> statusLabel.setText("Reservation already exists"));
				}
			}

			@Override
			public void onFailure(Call<ReservationDto> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));
			}
		});

	}

	public void handleDeleteButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

		if (deleteConfirmation()) {
			Call<Void> call = apiInterface.deleteReservation(LoginWindowController.getAdminDto().getToken(),
					reservationId);
			call.enqueue(new Callback<Void>() {

				@Override
				public void onResponse(Call<Void> call, Response<Void> response) {
					switch (response.code()) {
					case 200:
						for (int i = 0; i < reservationList.size(); i++) {
							if (reservationId == reservationList.get(i).getReservationId()) {
								reservationList.remove(i);
								reservationTableView.refresh();
							}
						}
						Platform.runLater(() -> statusLabel.setText("Reservation was removed successfully"));
						break;
					case 400:
						Platform.runLater(() -> statusLabel.setText("Failed to delete reservation"));
						break;
					case 403:
						Platform.runLater(() -> statusLabel.setText("Authentication failure"));
						break;
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

		userIdColumn.setCellValueFactory(new PropertyValueFactory<ReservationDto, Integer>("userId"));
		routeIdColumn.setCellValueFactory(new PropertyValueFactory<ReservationDto, Integer>("routeId"));
		ticketsColumn.setCellValueFactory(new PropertyValueFactory<ReservationDto, Integer>("numberOfTickets"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<ReservationDto, Double>("price"));

		reservationTableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> fillEditData(newValue));

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<List<ReservationDto>> call = apiInterface.getReservations(LoginWindowController.getAdminDto().getToken());

		call.enqueue(new Callback<List<ReservationDto>>() {

			@Override
			public void onResponse(Call<List<ReservationDto>> call, Response<List<ReservationDto>> response) {
				switch (response.code()) {
				case 200:
					for (ReservationDto reservationDto : response.body()) {
						reservationList.add(reservationDto);
					}
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Couldn ot load reservations"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				}
			}

			@Override
			public void onFailure(Call<List<ReservationDto>> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));

			}
		});

		reservationTableView.setItems(reservationList);

	}

	public boolean deleteConfirmation() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Confirmation");
		alert.setHeaderText("Reservation deletion");
		alert.setContentText("You are about to delete a reservation from database");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
}
