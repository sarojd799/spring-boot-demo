package com.demoapp.models;

public class LoginResponseVO {
	
	private String token = "";

	public LoginResponseVO(String token) {
	      this.token = token;
	}

	public String getToken() {
		return token;
	}
}
