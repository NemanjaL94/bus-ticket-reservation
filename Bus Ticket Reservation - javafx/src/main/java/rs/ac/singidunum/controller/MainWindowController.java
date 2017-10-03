package rs.ac.singidunum.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.singidunum.Main;
import rs.ac.singidunum.api.ApiClient;
import rs.ac.singidunum.api.ApiInterface;

public class MainWindowController implements Initializable {

	private Main main;
	private Stage mainWindowStage;
	private Stage primaryStage;

	@FXML
	private AnchorPane root;

	@FXML
	private SplitPane splitPane;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Button logoutButton;

	@FXML
	private Button homeButton;

	@FXML
	private ImageView imageView;

	public SplitPane getSplitPane() {
		return splitPane;
	}

	public Label getWelcomeLabel() {
		return welcomeLabel;
	}

	public void setWelcomeLabel(Label welcomeLabel) {
		this.welcomeLabel = welcomeLabel;
	}

	public void setMain(Main main, Stage mainWindowStage, Stage primaryStage) {
		this.main = main;
		this.mainWindowStage = mainWindowStage;
		this.primaryStage = primaryStage;

	}

	public void handleHomeButton() throws IOException {
		splitPane.getItems().set(1,
				FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/UserInfoView.fxml")));
	}

	public void initialize(URL location, ResourceBundle resources) {
		welcomeLabel.setText("Welcome " + LoginWindowController.getAdminDto().getUsername());

		try {
			splitPane.getItems().set(1,
					FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/UserInfoView.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void handleLogoutButton() {
		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<Void> call = apiInterface.logout(LoginWindowController.getAdminDto().getToken());

		call.enqueue(new Callback<Void>() {

			@Override
			public void onResponse(Call<Void> call, Response<Void> response) {
				if (response.code() == 200) {
					LoginWindowController.getAdminDto().setToken(null);
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							mainWindowStage.getScene().getWindow().hide();
							primaryStage.show();
						}
					});
				}
			}

			@Override
			public void onFailure(Call<Void> call, Throwable t) {
				t.printStackTrace();
			}
		});
	}

	public void handleBusCompanyButton() throws IOException {
		splitPane.getItems().set(1,
				FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/AddBusCompanyView.fxml")));
	}

	public void handleCitiesButton() throws IOException {
		splitPane.getItems().set(1, FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/AddCityView.fxml")));
	}

	public void handleRouteButton() throws IOException {
		splitPane.getItems().set(1,
				FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/AddRouteView.fxml")));
	}

	public void handleEditBusCompanyButton() throws IOException {
		splitPane.getItems().set(1,
				FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/EditBusCompanyView.fxml")));
	}

	public void handleEditCityButton() throws IOException {
		splitPane.getItems().set(1,
				FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/EditCityView.fxml")));
	}

	public void handleEditRouteButton() throws IOException {
		splitPane.getItems().set(1,
				FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/EditRouteView.fxml")));
	}

	public void handleEditReservationButton() throws IOException {
		splitPane.getItems().set(1,
				FXMLLoader.load(getClass().getResource("/rs/ac/singidunum/view/EditReservationView.fxml")));
	}

}
