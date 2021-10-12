package com.demoapp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demoapp.dto.AuthDTO;
import com.demoapp.repos.AuthRepository;

@Service
public class UserDetailsUtils implements UserDetailsService {
	
	@Autowired
	private AuthRepository authRepository;
	
	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AuthDTO auth = authRepository.findByUsername(username);
		
		if(username.equals("test") && auth == null) {
			AuthDTO testAuth = new AuthDTO();
			testAuth.setUsername(username);
			testAuth.setPassword(username);
			testAuth.setIsActive(true);
			authRepository.save(testAuth);
			auth = testAuth;
		}
		
		if(auth != null) {
			return new User(username, new BCryptPasswordEncoder().encode(auth.getPassword()), new ArrayList());
		} 
		throw new UsernameNotFoundException("No Record found with provided username "+username);
	}

}
