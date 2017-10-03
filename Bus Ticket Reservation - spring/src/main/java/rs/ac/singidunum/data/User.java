package rs.ac.singidunum.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import rs.ac.singidunum.dto.UserDto;

@Entity
@JsonInclude(Include.NON_NULL)
public class User {
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "active")
	@Type(type = "numeric_boolean")
	private Boolean active;

	@Column(name = "token")
	private String token;

	public User() {

	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", active=" + active + ", token=" + token;
	}

	public User(Long userId, String username, String password, String email, Boolean active, String token) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.active = active;
		this.token = token;
	}

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public User(UserDto userDto) {
		setActive(userDto.getActive());
		setEmail(userDto.getEmail());
		setPassword(userDto.getPassword());
		setUsername(userDto.getUsername());
		setUserId(userDto.getUserId());
		setToken(userDto.getToken());
	}

	public Long getUserId() {
		return userId;
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
		if (active == null) {
			this.active = false;
		} else {
			this.active = active;
		}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
