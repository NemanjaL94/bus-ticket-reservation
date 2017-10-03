package rs.ac.singidunum.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.singidunum.api.ApiClient;
import rs.ac.singidunum.api.ApiInterface;
import rs.ac.singidunum.data.UserDto;

public class UserInfoController implements Initializable {

	@FXML
	private TableView<UserDto> userTableView;

	@FXML
	private TableColumn<UserDto, Integer> userIdColumn;

	@FXML
	private TableColumn<UserDto, String> usernameColumn;

	@FXML
	private TableColumn<UserDto, String> emailColumn;

	@FXML
	private TableColumn<UserDto, Boolean> activeColumn;

	private ObservableList<UserDto> users = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<List<UserDto>> call = apiInterface.getUsers(LoginWindowController.getAdminDto().getToken());
		call.enqueue(new Callback<List<UserDto>>() {

			@Override
			public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
				switch (response.code()) {
				case 200:
					for (UserDto userDto : response.body()) {
						users.add(userDto);
					}
					break;
				}
			}

			@Override
			public void onFailure(Call<List<UserDto>> call, Throwable t) {
				t.printStackTrace();

			}
		});

		userIdColumn.setCellValueFactory(new PropertyValueFactory<UserDto, Integer>("userId"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<UserDto, String>("username"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<UserDto, String>("email"));
		activeColumn.setCellValueFactory(new PropertyValueFactory<UserDto, Boolean>("active"));
		userTableView.setItems(users);
	}

}
