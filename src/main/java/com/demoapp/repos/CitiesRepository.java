package com.demoapp.repos;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoapp.dto.CityDTO;
import com.demoapp.dto.CountryDTO;
import com.demoapp.dto.StateDTO;

public interface CitiesRepository extends JpaRepository<CityDTO, Integer> {
	
	public Collection<CityDTO> findByStateId(StateDTO stateId); 
}