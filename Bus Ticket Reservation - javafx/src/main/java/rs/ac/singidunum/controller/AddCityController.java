package rs.ac.singidunum.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.singidunum.api.ApiClient;
import rs.ac.singidunum.api.ApiInterface;
import rs.ac.singidunum.data.StopDto;

public class AddCityController implements Initializable {

	@FXML
	private TextField addCityTextField;

	@FXML
	private Button addCityButton;

	@FXML
	private Label statusLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void handleAddCityButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<StopDto> call = apiInterface.addStop(LoginWindowController.getAdminDto().getToken(),
				new StopDto(addCityTextField.getText()));
		call.enqueue(new Callback<StopDto>() {

			@Override
			public void onResponse(Call<StopDto> call, Response<StopDto> response) {
				switch (response.code()) {
				case 201:
					Platform.runLater(() -> statusLabel.setText("Stop has been created"));
					break;
				case 400:
					Platform.runLater(() -> statusLabel.setText("Stop already exists"));
					break;
				case 403:
					Platform.runLater(() -> statusLabel.setText("Authentication failure"));
					break;
				default:
					Platform.runLater(() -> statusLabel.setText("Stop already exists"));
				}

			}

			@Override
			public void onFailure(Call<StopDto> call, Throwable t) {
				Platform.runLater(() -> statusLabel.setText("Server error"));

			}
		});
	}
}
