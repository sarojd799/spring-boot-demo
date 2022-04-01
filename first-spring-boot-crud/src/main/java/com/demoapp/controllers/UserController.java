package com.demoapp.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demoapp.application.FileStorageService;
import com.demoapp.dto.UserDetailsDTO;
import com.demoapp.jwtutils.JWTUtils;
import com.demoapp.models.LoginResponseVO;
import com.demoapp.services.UserDetailsUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UserController {

	@Autowired
	private UserDetailsUtils userDetailsService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	FileStorageService storageService;

	
	@GetMapping(path= "/getAllUsersExcept", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Set<UserDetailsDTO> getAllUsersExcept(@RequestParam("username") String username) {
		try {
			return userDetailsService.getAllUsersNotLike(username);	
		} catch(Exception e) {
			throw e;
		}
	}
	
	@GetMapping(path= "/api/getAllUsersByMatch", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Set<UserDetailsDTO> getAllUserByMatch(@RequestParam("username") String username) {
		try {
			return userDetailsService.getUsersByEmail(username);	
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@PostMapping(
			path= "/api/{userId}/updateUserInfo", 
			produces= MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE
	) 
	public ResponseEntity<UserDetailsDTO> updateUserInfo(@PathVariable("userId") Integer userId, @RequestBody UserDetailsDTO user) {
		try {
			
			Boolean tokenRequired = false;
			UserDetailsDTO existingUser = userDetailsService.getUserDetailsById(userId);
			if(userId != null) {
				if(existingUser != null) {
					tokenRequired = (!existingUser.getEmail().equals(user.getEmail().trim()));
				}
			}
			
			UserDetailsDTO updatedUser = userDetailsService.updateUserInfo(user);
			if(tokenRequired) {
				UserDetails userDetail = userDetailsService.loadUserByUsername(updatedUser.getEmail());
				
				HttpHeaders headers = new HttpHeaders();
			    headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+ jwtUtils.generateJwtToken(userDetail.getUsername()));
			    headers.add("access-control-expose-headers", "Authorization");
			    
				return ResponseEntity.ok().headers(headers).body(updatedUser);
			}
			return ResponseEntity.ok().body(updatedUser);	
		} catch(Exception e) {
			throw e;
		}
	}

	@RequestMapping(
			value = "/api/{userId}/uploadProfileImage", 
			method = RequestMethod.POST, 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
    public UserDetailsDTO uploadUserImage(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) throws Exception {
		 
		
		try {
			if(file == null || userId == null) throw new RuntimeException("Missing request details");
			
			Path filePath = storageService.save(file);
			
			return (filePath != null)? userDetailsService.updateProfileImage(userId, filePath.getFileName().toString()): null;
			
		} catch(Exception e) {
			throw e;
		}
    }

	
	
	@GetMapping(value="/api/{userId}/getProfileById")
	public UserDetailsDTO getProfileById(@PathVariable Integer userId) {
		
		try {
			return (userId != null) ? userDetailsService.getUserDetailsById(userId): null;
		} catch(Exception e) {
			throw e;
		}
	}


}
