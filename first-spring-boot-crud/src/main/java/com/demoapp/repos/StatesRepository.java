package com.demoapp.repos;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoapp.dto.CountryDTO;
import com.demoapp.dto.StateDTO;

public interface StatesRepository extends JpaRepository<StateDTO, Integer>{
	
	public StateDTO findByStateId(int stateId);
	
	public Collection<StateDTO> findByCountryId(CountryDTO countryId);
}
