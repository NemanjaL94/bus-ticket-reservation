package rs.ac.singidunum.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Role {

	public enum RoleName {
		USER, ADMIN
	}

	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private Long roleId;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "role_name")
	@Enumerated(EnumType.STRING)
	private RoleName roleName;



	public Role() {

	}
	
	public Role(RoleName roleName) {
		this.roleName = roleName;
	}

	public Role(RoleName roleName, User user) {
		this.roleName = roleName;
		this.user = user;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public RoleName getName() {
		return roleName;
	}

	public void setName(RoleName roleName) {
		this.roleName = roleName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
