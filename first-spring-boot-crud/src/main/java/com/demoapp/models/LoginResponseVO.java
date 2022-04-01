package com.demoapp.models;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;

import com.demoapp.dto.RoleDTO;

public class LoginResponseVO {
	
	private String token = "";
	
	private Collection<?> roles;
	
	public Integer timeout;
	
	
	
	
	public LoginResponseVO(String token, Collection<?> roles, Integer timeout) {
	      this.token = token;
	      this.roles = roles;
	      this.timeout = timeout;
	}
	
	public Collection<?> getRoles() {
		return roles;
	}

	public void setRoles(Collection<?> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
	
}
