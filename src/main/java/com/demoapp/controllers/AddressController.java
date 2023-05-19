package com.demoapp.controllers;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demoapp.dto.CityDTO;
import com.demoapp.dto.CountryDTO;
import com.demoapp.dto.StateDTO;
import com.demoapp.services.AddressService;
import com.demoapp.services.AuthService;

@RestController
public class AddressController {
	
	@Autowired
	private AddressService addressService;

	
	@PostMapping("/saveAllCountries")
	public boolean saveAllCountries(@RequestBody Collection<CountryDTO> countries) {
		try {
			return addressService.saveAllCountries(countries);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping("/saveAllStates")
	public boolean saveAllStates(@RequestBody Collection<StateDTO> states) {
		try {
			return addressService.saveAllStates(states);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping("/saveAllCities")
	public boolean saveAllCities(@RequestBody Collection<CityDTO> cities) {
		try {
			return addressService.saveAllCities(cities);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@GetMapping("/api/getAllCountries")
	public Collection<CountryDTO> getAllCountries() {
		return addressService.getAllCountries();
	}
	
	@GetMapping("/api/{countryId}/getAllStatesOfCountry")
	public Collection<StateDTO> getStatesForCountry(@PathVariable int countryId) {
		return addressService.getAllStatesOfCountries(countryId);
	}
	
	@GetMapping("/api/{stateId}/getAllCitiesOfState")
	public Collection<CityDTO> getCitiesForState(@PathVariable int stateId) {
		return addressService.getAllCitiesOfState(stateId);
	}
}
