package rs.ac.singidunum;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rs.ac.singidunum.controller.LoginWindowController;
import rs.ac.singidunum.controller.MainWindowController;

public class Main extends Application {

	private Stage primaryStage;
	private Stage mainWindowStage = new Stage();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		loginWindow();
	}

	public void loginWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/rs/ac/singidunum/view/LoginWindowView.fxml"));
			AnchorPane pane = loader.load();

			Scene scene = new Scene(pane);
			LoginWindowController loginWindowController = loader.getController();
			loginWindowController.setMain(this, primaryStage);

			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/rs/ac/singidunum/view/MainWindowView.fxml"));
			AnchorPane pane = loader.load();

			Scene scene = new Scene(pane);
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.setMain(this, mainWindowStage, primaryStage);

			mainWindowStage.setResizable(false);
			mainWindowStage.setScene(scene);
			mainWindowStage.show();
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
