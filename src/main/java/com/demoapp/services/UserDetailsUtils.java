package com.demoapp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.demoapp.dto.UserDetailsDTO;
import com.demoapp.repos.AuthRepository;
import com.demoapp.repos.UserDetailsRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UserDetailsUtils implements UserDetailsService {
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	
	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AuthDTO auth = authRepository.findByUsername(username);
		
		if(auth != null) {
			List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
			if(auth.getUserRoles().size() > 0) {
				auth.getUserRoles().forEach(r-> roles.add(new SimpleGrantedAuthority(r.getRoleName())));
			}
			return new User(username, new BCryptPasswordEncoder().encode(auth.getPassword()), roles);
		} 
		throw new UsernameNotFoundException("No Record found with provided username "+username);
	}
	
	
	public Set<UserDetailsDTO> getUsersByEmail(String email) {
		if (email != null && email.trim().length() > 0) {
     		Set<UserDetailsDTO> users = userDetailsRepository.findByEmailContaining(email).stream().collect(Collectors.toSet());
			return users;
		}
		throw new RuntimeException("Invalid argument");
	}
	
	
	public Set<UserDetailsDTO> getAllUsersNotLike(String email) {
		if(email != null && email.trim().length() > 0) {
			Set<AuthDTO> auths = authRepository.findByUsernameContaining(email).stream().collect(Collectors.toSet());
			Set<UserDetailsDTO> matchedUsers = new HashSet<UserDetailsDTO>();
			auths.stream().forEach(auth->{
				Set<UserDetailsDTO> users = userDetailsRepository.findByEmailContaining(email).stream().collect(Collectors.toSet());
				if(users.size() > 0) {
					matchedUsers.addAll(users);
				}
			});
			return matchedUsers;
		}
		throw new RuntimeException("Invalid argument");
	}

	
	public UserDetailsDTO getUserDetailsById(Integer id) {
		if(id != null) {
			return userDetailsRepository.findByUserDetailsId(id);	
		} 
		return null;
	}
	
	//Profile update
	public UserDetailsDTO updateUserInfo(UserDetailsDTO u) {
		return userDetailsRepository.save(u);
	}
	
	
	public UserDetailsDTO updateProfileImage(Integer userId, String fileName) {
		if(userId != null && fileName != null) {
			UserDetailsDTO u = userDetailsRepository.findByUserDetailsId(userId);
			if(u != null) {
				u.setImgURL(fileName);
				userDetailsRepository.save(u);
				return u;
			}
		}
		return null;
	}
	
	
	public void updateUserStatus(UserDetailsDTO user, String status) {
		if(user != null) {
			user.setStatus(status);
			userDetailsRepository.save(user);
		}
	}
	
	/*-to update the logged in time stamp in table-*/
	public void setUserLoggedIn(UserDetailsDTO userDetails, Date loggedInTime) {
		if(userDetails != null) {
			userDetails.setLoggedIn(loggedInTime);
			userDetailsRepository.save(userDetails);
		}
	}
	
	
	
	
	
}
