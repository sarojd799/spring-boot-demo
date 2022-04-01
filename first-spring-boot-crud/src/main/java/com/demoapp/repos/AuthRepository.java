package com.demoapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import com.demoapp.dto.AuthDTO;

@Repository
public interface AuthRepository extends JpaRepository<AuthDTO, Integer> {
   
	
	public AuthDTO findByUsername(String name);
   
	public Streamable<AuthDTO> findByUsernameContaining(String username);
	
	public Streamable<AuthDTO> findByUsernameNot(String username);
	
	
	
	
	
	
	
	
	
	
    public Streamable<AuthDTO> findUserExcluding(String userName, String currentUser);
}
