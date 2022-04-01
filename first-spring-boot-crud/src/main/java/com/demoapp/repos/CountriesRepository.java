package com.demoapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoapp.dto.CountryDTO;


public interface CountriesRepository extends JpaRepository<CountryDTO, Integer> {

	public CountryDTO findById(int countryId);
}
