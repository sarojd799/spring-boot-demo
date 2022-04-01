package com.demoapp.dto;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="app_roles")
@Table(name="app_roles")
public class RoleDTO {
	
	public RoleDTO() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="role_name")
	private String roleName;
	
	@ManyToMany(mappedBy="userRoles")
	@JsonBackReference
	private Collection<AuthDTO> auths;
	
    
	public Collection<AuthDTO> getAuths() {
		return auths;
	}

	public void setAuths(Collection<AuthDTO> auths) {
		this.auths = auths;
	}

	public RoleDTO(String roleName) {
		super();
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
