package rs.ac.singidunum.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.singidunum.api.ApiClient;
import rs.ac.singidunum.api.ApiInterface;
import rs.ac.singidunum.data.BusCompanyDto;

public class AddBusCompanyController {

	@FXML
	private TextField addBusCompanyTextField;
	@FXML
	private Button addBusCompanyButton;
	@FXML
	private Label statusLabel;

	public void handleAddBusCompanyButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<BusCompanyDto> call = apiInterface.addBusCompany(LoginWindowController.getAdminDto().getToken(),
				new BusCompanyDto(addBusCompanyTextField.getText()));
		call.enqueue(new Callback<BusCompanyDto>() {

			@Override
			public void onResponse(Call<BusCompanyDto> call, Response<BusCompanyDto> response) {
				switch (response.code()) {
				case 201:
					Platform.runLater(() -> statusLabel.setText("Bus company has been created"));
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Bus company already exists"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				default:
					Platform.runLater(() -> statusLabel.setText("Bus company already exists"));
				}
			}

			@Override
			public void onFailure(Call<BusCompanyDto> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));
			}
		});
	}

}
