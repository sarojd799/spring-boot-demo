package com.demoapp.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoapp.dto.CityDTO;
import com.demoapp.dto.CountryDTO;
import com.demoapp.dto.StateDTO;
import com.demoapp.repos.CitiesRepository;
import com.demoapp.repos.CountriesRepository;
import com.demoapp.repos.StatesRepository;

@Service
public class AddressService {

	@Autowired
	private CountriesRepository countryRepository;
	
	@Autowired
	private StatesRepository stateRepository;
	
	@Autowired
	private CitiesRepository citiesRepository;
	
	
	public boolean saveAllCountries(Collection<CountryDTO> countries) {
		List<CountryDTO> savedCountries = countryRepository.saveAll(countries);
		return savedCountries.size() > 0;
	}
	
	public boolean saveAllStates(Collection<StateDTO> states) {
		List<StateDTO> savedStates = stateRepository.saveAll(states);
		return savedStates.size() > 0;
	}
	
	public boolean saveAllCities(Collection<CityDTO> cities) {
		List<CityDTO> savedcities = citiesRepository.saveAll(cities);
		return savedcities.size() > 0;
	}





    public Collection<CountryDTO> getAllCountries(){
    	return countryRepository.findAll().stream().collect(Collectors.toSet());
    }
    
    public Collection<StateDTO> getAllStatesOfCountries(int countryId){
    	CountryDTO country = countryRepository.findById(countryId);
    	if(country != null) {
    		return stateRepository.findByCountryId(country).stream().collect(Collectors.toSet());
    	}
    	return new HashSet<StateDTO>();
    }
    
    public Collection<CityDTO> getAllCitiesOfState(int stateId){
    	System.out.println(stateId);
    	StateDTO state = stateRepository.findByStateId(stateId);
    	System.out.println(state);
    	if(state != null) {
    		return citiesRepository.findByStateId(state).stream().collect(Collectors.toSet()); 
    	}
    	return new HashSet<CityDTO>();
    }










}
