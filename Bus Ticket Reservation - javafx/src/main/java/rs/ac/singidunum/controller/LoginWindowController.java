package rs.ac.singidunum.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.singidunum.Main;
import rs.ac.singidunum.api.ApiClient;
import rs.ac.singidunum.api.ApiInterface;
import rs.ac.singidunum.data.AdminDto;
import rs.ac.singidunum.data.SignInDto;

public class LoginWindowController {

	private Main main;
	private Stage primaryStage;
	private static AdminDto adminDto;

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label errorLabel;

	public static AdminDto getAdminDto() {
		return adminDto;
	}

	public static void setAdminDto(AdminDto adminDto) {
		LoginWindowController.adminDto = adminDto;
	}

	public void setMain(Main main, Stage primaryStage) {
		this.main = main;
		this.primaryStage = primaryStage;
	}

	public void signIn() {

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<AdminDto> call = apiInterface.login(new SignInDto(username.getText(), password.getText()));

		call.enqueue(new Callback<AdminDto>() {

			public void onResponse(Call<AdminDto> call, Response<AdminDto> response) {
				switch (response.code()) {
				case 200:
					adminDto = response.body();
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							main.mainWindow();
							primaryStage.hide();
							errorLabel.setText("");
						}
					});
					;
					break;
				case 400:
					Platform.runLater(() -> errorLabel.setText("Wrong email or password"));
					break;
				default:
					Platform.runLater(() -> errorLabel.setText("Server error"));
				}

			}

			public void onFailure(Call<AdminDto> call, Throwable t) {
				Platform.runLater(() -> errorLabel.setText("Server error"));
			}
		});

	}

}
