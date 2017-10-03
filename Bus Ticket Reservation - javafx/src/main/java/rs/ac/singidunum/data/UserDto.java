package rs.ac.singidunum.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDto {
	
	@SerializedName("userId")
	@Expose
	private Integer userId;

	@SerializedName("username")
	@Expose
	private String username;

	@SerializedName("email")
	@Expose
	private String email;
	
	@SerializedName("active")
	@Expose
	private Boolean active;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", username=" + username + ", email=" + email + ", active=" + active + "]";
	}
	
	
	
}
