package com.demoapp.controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demoapp.application.CustomProperties;
import com.demoapp.constants.RequestURIMap;
import com.demoapp.dto.AuthDTO;
import com.demoapp.dto.UserDetailsDTO;
import com.demoapp.jwtutils.JWTUtils;
import com.demoapp.models.ErrorResponseVO;
import com.demoapp.models.LoginResponseVO;
import com.demoapp.models.RefreshTokenResponseVO;
import com.demoapp.services.AuthService;
import com.demoapp.services.UserDetailsUtils;

import io.jsonwebtoken.ExpiredJwtException;

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

	@Autowired
	private CustomProperties appProperties;

	public UserDetailsDTO updateUserLoggedIn(String email) {
		if (email != null) {
			Set<UserDetailsDTO> matchedDetails = userDetailsService.getUsersByEmail(email);
			if (matchedDetails.size() > 0) {
				matchedDetails.stream().forEach(user -> {
					userDetailsService.setUserLoggedIn(user, new Date());
				});
			}
		}

		return null;
	}

	public Cookie generateCookie(String token) {
		Cookie cookie = new Cookie("session_id", token);
		cookie.setHttpOnly(true);
	    Integer cookieTimeout = (appProperties.getTimeout()/1000) + (appProperties.getRefreshTimeout()/1000);
	    cookie.setMaxAge(cookieTimeout);
		return cookie;
	}

	@PostMapping(path = RequestURIMap.loginURI, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> loginUser(@RequestBody AuthDTO auth, HttpServletResponse response) throws Exception {

		String message = "";

		try {
			
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
		} catch (DisabledException e) {
			message = "USER_DISABLED";
		} catch (BadCredentialsException e) {
			message = "INVALID_CREDENTIALS";
		}

		if (message.length() > 0) {
			return ResponseEntity.ok(new ErrorResponseVO(200, message));
		} else {

			String accessToken = jwtUtils.generateJwtToken(auth.getUsername());

			String refreshToken = jwtUtils.generateRefreshToken(auth.getUsername());

			AuthDTO authDetails = authService.getUserByEmail(auth.getUsername());
			
			updateUserLoggedIn(auth.getUsername());

			response.addCookie(generateCookie(refreshToken));
			
			return ResponseEntity.ok().body(new LoginResponseVO(accessToken, authDetails.getUserRoles(), appProperties.getTimeout()));
		}
	}

	@GetMapping(path = RequestURIMap.regUserCheckURI)
	public boolean checkUserByName(@RequestParam("username") String username) {
		return authService.existsUserWithName(username);
	}

	@PostMapping(path = RequestURIMap.registerURI, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDetailsDTO saveNewUser(@RequestBody AuthDTO auth) {
		return authService.saveNewUser(auth);
	}

	@GetMapping("/api/refreshToken")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {

		String refToken = "";
		Cookie[] cookies = request.getCookies();
		
		try {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("session_id")) {
					refToken = cookie.getValue();
					break;
				}
			}
			if (refToken.length() > 0) {
				String username = jwtUtils.getUsernameFromToken(refToken);
				if(username != null) {
					AuthDTO auth  = authService.getUserByEmail(username);
					if(auth != null) {
						String accessToken = jwtUtils.generateJwtToken(username);
						String refreshToken = jwtUtils.generateRefreshToken(username);
						response.addCookie(generateCookie(refreshToken));
						RefreshTokenResponseVO refreshTokenResponse = new RefreshTokenResponseVO();
						refreshTokenResponse.setAccessToken(accessToken);
						refreshTokenResponse.setTimeout(appProperties.getTimeout());
						return ResponseEntity.ok(refreshTokenResponse);
					}
				}
				
			}
			throw new RuntimeException();
		} catch (Exception e) {
			System.out.println("Refresh token expired: "+e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	
	@PostMapping(path="/auth/{userId}/logout")
	public boolean logout(@PathVariable Integer userId, HttpServletRequest request, HttpServletResponse response) {
		
		if(userId == null) return false;
		Cookie cookie = new Cookie("session_id", "");
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return authService.clearLastLoggedIn(userId);
	}

}
