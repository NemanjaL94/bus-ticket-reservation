package rs.ac.singidunum.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminDto {

	@SerializedName("token")
	@Expose
	private String token;

	@SerializedName("username")
	@Expose
	private String username;

	@SerializedName("roleName")
	@Expose
	private String roleName;
	
	

	public AdminDto(String token, String username, String roleName) {
		this.token = token;
		this.username = username;
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AdminDto [token=" + token + ", username=" + username + ", roleName=" + roleName + "]";
	}
	
	

}
