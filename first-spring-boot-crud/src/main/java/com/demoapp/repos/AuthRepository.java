package com.demoapp.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demoapp.dto.AuthDTO;

@Repository
public interface AuthRepository extends CrudRepository<AuthDTO, Integer> {
	public AuthDTO findByUsername(String name);
}
