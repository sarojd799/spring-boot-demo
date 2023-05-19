package com.demoapp.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demoapp.dto.UserDetailsDTO;
import com.demoapp.services.ConnectionsManagerService;

@RestController
public class ConnectionsController {
	
	
	@Autowired
	private ConnectionsManagerService conManager;
	
	
	@PostMapping(
			path= "/api/{userId}/saveNewConnection", 
			produces= MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE
	) 
	public boolean saveNewConnection(@PathVariable("userId") Integer userId, @RequestBody UserDetailsDTO user) {
		try {
			return conManager.saveNewCollection(userId, user);	
		} catch(Exception e) {
			throw e;
		}
	}

	@GetMapping(path= "/api/{userId}/getAllConnectionsOfUser", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Set<UserDetailsDTO> getAllConnectionsForUser(@PathVariable("userId") Integer userId) {
		try {
			return conManager.getAllConnectionsOfUser(userId);	
		} catch(Exception e) {
			throw e;
		}
	}

	
	@GetMapping(path= "/api/{userId}/getAllUsersWithKeyword", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Set<UserDetailsDTO> searchConnectionsWithKeyword(@PathVariable Integer userId,@RequestParam("keyword") String keyword) {
		try {
			return conManager.getAllUsersWithKeyword(userId, keyword);
		} catch(Exception e) {
			throw e;
		}
	}
	
}
