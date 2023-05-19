package com.demoapp.repos;

import com.demoapp.dto.AuthDTO;
import com.demoapp.dto.UserDetailsDTO;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface UserDetailsRepository extends JpaRepository<UserDetailsDTO, Integer> {
	
	public UserDetailsDTO findByUserDetailsId(Integer userId); 
	
	public Streamable<UserDetailsDTO> findByUserDetailsIdIn(Collection<Integer> userIds);

	public Streamable<UserDetailsDTO> findByEmail(AuthDTO email);
	
	public Streamable<UserDetailsDTO> findUserByEmailLike(AuthDTO userEmail);
	
	public Streamable<UserDetailsDTO> findByEmailNot(String email);
	
	public Streamable<UserDetailsDTO> findByEmailContaining(String userEmail);
}
	