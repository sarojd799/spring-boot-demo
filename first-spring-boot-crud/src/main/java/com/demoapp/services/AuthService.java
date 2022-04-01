package com.demoapp.services;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoapp.dto.AuthDTO;
import com.demoapp.dto.RoleDTO;
import com.demoapp.dto.UserDetailsDTO;
import com.demoapp.repos.AuthRepository;
import com.demoapp.repos.RolesRepository;
import com.demoapp.repos.UserDetailsRepository;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	public boolean validateUser(AuthDTO auth) {
		return StreamSupport.stream(authRepository.findAll().spliterator(), false)
		 .anyMatch(d-> d.getUsername().equals(auth.getUsername()) && d.getPassword().equals(auth.getPassword()));
	}
	
	public boolean existsUserWithName(String username) {
		AuthDTO auth = authRepository.findByUsername(username);
		return auth != null;
	}
	
	public UserDetailsDTO saveNewUser(AuthDTO auth) {
		RoleDTO role = rolesRepository.findByRoleName("USER");
		UserDetailsDTO u = null;
		if(role != null && auth != null) {
			UserDetailsDTO newUserDetails = new UserDetailsDTO();
			newUserDetails.setEmail(auth.getUsername());
 			u = userDetailsRepository.save(newUserDetails);
 			auth.setUserId(u.getUserDetailsId());
 			auth.setIsActive(false);
			auth.setUserRoles(Arrays.asList(role).stream().collect(Collectors.toSet()));
			authRepository.save(auth);
		}
		System.out.println("user after save"+ u);
		return u;
	}
	
	
	public AuthDTO getUserByEmail(String email) {
		if (email != null) {
     		return authRepository.findByUsername(email);
		}
		return null;
	}
	
	public boolean clearLastLoggedIn(Integer userId) {
		if(userId != null) {
			UserDetailsDTO u = userDetailsRepository.findByUserDetailsId(userId);
			if(u != null) {
				u.setLoggedIn(null);
				userDetailsRepository.save(u);
				return true;
			}
			
		}
		return false;
	}
	

}
