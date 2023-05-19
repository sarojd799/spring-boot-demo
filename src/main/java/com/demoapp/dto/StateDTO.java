package com.demoapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Table(name="states")
@Entity(name="states")
public class StateDTO {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="state_id")
    private int stateId;
    
	@Column(name="state_name")
    private String stateName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@JsonIgnore
	private CountryDTO countryId;
	
	
	
	
	public StateDTO() {
		super();
	}


	public StateDTO(String stateName) {
		super();
		this.stateName = stateName;
	}
	

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	
	public CountryDTO getCountryId() {
		return countryId;
	}

	public void setCountry_id(CountryDTO countryId) {
		this.countryId = countryId;
	}
    
}
