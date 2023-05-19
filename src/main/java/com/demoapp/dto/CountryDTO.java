package com.demoapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="countries")
@Table(name="countries")
public class CountryDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="country_id")
	private int countryId;
	
	@Column(name="country_name")
	private String countryName;
	
	@Column(name="country_code")
	private String countryCode;


	@Column(name="phone_code")
	private int phoneCode;


	public CountryDTO() {
		super();
	}

	@Override
	public String toString() {
		return "CountryDTO [countryId=" + countryId + ", countryName=" + countryName + "]";
	}

	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public int getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(int phoneCode) {
		this.phoneCode = phoneCode;
	}

	public CountryDTO(String countryName) {
		super();
		this.countryName = countryName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
