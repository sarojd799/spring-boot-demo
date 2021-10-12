package com.demoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demoapp.dto.AuthDTO;
import com.demoapp.jwtutils.JWTUtils;
import com.demoapp.models.ErrorResponseVO;
import com.demoapp.models.LoginResponseVO;
import com.demoapp.services.AuthService;
import com.demoapp.services.UserDetailsUtils;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsUtils userDetailsService;

	@Autowired
	private JWTUtils jwtUtils;

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity loginUser(@RequestBody AuthDTO auth) throws Exception {
		
		String message = "";

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
		} catch (DisabledException e) {
			message = "USER_DISABLED";
		} catch (BadCredentialsException e) {
			message = "INVALID_CREDENTIALS";
		}

		if(message.length() > 0) {
			return ResponseEntity.ok(new ErrorResponseVO(200,message));
		} else {
			UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getUsername());
			String jwtToken = jwtUtils.generateJwtToken(userDetails);
			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, "auth="+jwtToken)
			        .body(new LoginResponseVO(jwtToken));
		}
		

		// return authService.validateUser(auth);
	}
}
