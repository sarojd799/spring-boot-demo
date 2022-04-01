package com.demoapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="cities")
@Entity(name="cities")
public class CityDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="city_id")
	private int cityId;
	
	@Column(name="city_name")
	private String cityName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	@JsonIgnore
	private StateDTO stateId;
	
	
	

	public CityDTO() {
		super();
	}


	public CityDTO(String cityName, StateDTO stateId) {
		super();
		this.cityName = cityName;
		this.stateId = stateId;
	}
	
	
	@Override
	public String toString() {
		return "CityDTO [cityId=" + cityId + ", cityName=" + cityName + "]";
	}
	
	
	
	public int getCityId() {
		return cityId;
	}


	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public StateDTO getStateId() {
		return stateId;
	}


	public void setStateId(StateDTO stateId) {
		this.stateId = stateId;
	}
	
	
}
