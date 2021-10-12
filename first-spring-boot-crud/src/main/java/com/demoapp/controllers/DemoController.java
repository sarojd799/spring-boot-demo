package com.demoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demoapp.services.AuthService;

@RestController
public class DemoController {
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/")
	public String welcomeMessage() {
		return "welcome to spring boot!";
	}
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello world!";
	}
}
