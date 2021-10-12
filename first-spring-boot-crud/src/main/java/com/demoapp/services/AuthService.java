package com.demoapp.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoapp.dto.AuthDTO;
import com.demoapp.dto.RoleDTO;
import com.demoapp.repos.AuthRepository;
import com.demoapp.repos.RolesRepository;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insertLoginDetails() {
		rolesRepository.saveAll(Arrays.asList(new RoleDTO("USER"), new RoleDTO("ADMIN")));
		RoleDTO role = (RoleDTO) StreamSupport.stream(rolesRepository.findAll().spliterator(), false)
				.filter(r-> r.getRoleName().equals("USER"))
				.map(RoleDTO.class::cast)
				.findFirst()
				.orElse(null);
		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		roles.add(role);
		
		Set<AuthDTO> auths = new HashSet<AuthDTO>();
		AuthDTO auth = new AuthDTO();
		auth.setUsername("test");
		auth.setPassword("test");
		auth.setIsActive(true);
		auth.setUserRoles(roles);
		auths.add(auth);
		authRepository.saveAll(auths);
	}
	
	public boolean validateUser(AuthDTO auth) {
		if(authRepository.count() == 0) {
			this.insertLoginDetails();
		}
		return StreamSupport.stream(authRepository.findAll().spliterator(), false)
		 .anyMatch(d-> d.getUsername().equals(auth.getUsername()) && d.getPassword().equals(auth.getPassword()));
	}
}
