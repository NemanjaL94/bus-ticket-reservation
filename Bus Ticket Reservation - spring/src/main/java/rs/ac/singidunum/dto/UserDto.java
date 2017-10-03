package rs.ac.singidunum.dto;

import javax.validation.constraints.Null;

import rs.ac.singidunum.data.User;

public class UserDto {
	@Null
	private Long userId;

	private String username;

	private String password;

	private String email;
	@Null
	private Boolean active;

	@Null
	private String token;

	public UserDto() {

	}

	public UserDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public UserDto(User user) {
		this.userId = user.getUserId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.active = user.getActive();
	}

	public Long getUserId() {
		return userId;
	}

	public UserDto(Long userId, String username, String password, String email, Boolean active, String token) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.active = active;
		this.token = token;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
