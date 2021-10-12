package com.demoapp.dto;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Table(name="login_details")
@Entity(name="auth_entity")
public class AuthDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="login_id")
	private int userId;
	
	@Column(name="user_name")
	private String username;
	
	private String password;
	
	@Column(name="is_active")
	private Boolean isActive;
	
	@Column(name="user_roles")
	@OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_map", joinColumns = {@JoinColumn(name = "login_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<RoleDTO> userRoles;
	
	  
	public AuthDTO() {}

	public AuthDTO(String username, String password, Boolean isActive, Set<RoleDTO> userRoles) {
		super();
		this.username = username;
		this.password = password;
		this.isActive = isActive;
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "AuthDTO [username=" + username + ", password=" + password + ", isActive=" + isActive+ ", userRoles=" + userRoles + "]";
	}

	/*-getters and setters-*/

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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<RoleDTO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set userRoles) {
		this.userRoles = userRoles;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
