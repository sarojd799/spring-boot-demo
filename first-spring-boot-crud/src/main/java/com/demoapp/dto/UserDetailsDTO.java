package com.demoapp.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity(name="user_details")
@Table(name="user_details")
@SecondaryTable(name="login_details", pkJoinColumns=@PrimaryKeyJoinColumn(name="login_id", referencedColumnName="user_id"))
public class UserDetailsDTO implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="user_id")
	private int userDetailsId;
	
	@Column(table="login_details", name="user_name")
	private String email;
	
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="phone")
	private String phone;

	@Column(name="status")
	private String status;
	
	@Column(name="gender")
	private String gender;

	@Column(name="dob")
	@Temporal(TemporalType.DATE)
	private Date  dob;
	
	@Column(name="image_url")
	private String imgURL;
	
	@Column(name="loggedIn")
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date loggedIn;

	
	private boolean isConnection;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="user_friends",
	joinColumns={@JoinColumn(name="user_id")},
	inverseJoinColumns={@JoinColumn(name="friend_id")})
	@JsonBackReference
	private Collection<UserDetailsDTO> connections = new HashSet<UserDetailsDTO>();
	

	@Embedded
	private AddressVO address;
	
	
	//to string
	@Override
	public String toString() {
		return "UserDetailsDTO [userDetailsId=" + userDetailsId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phone=" + phone + ", status=" + status + ", gender=" + gender + ", dob=" + dob
				+ "connections=" + connections + "]";
	}

	//getter setters
	
	public AddressVO getAddress() {
		return address;
	}

	public void setAddress(AddressVO address) {
		this.address = address;
	}
	
	public boolean isConnection() {
		return isConnection;
	}

	public void setConnection(boolean isConnection) {
		this.isConnection = isConnection;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserDetailsId() {
		return userDetailsId;
	}

	public void setUserDetailsId(int userDetailsId) {
		this.userDetailsId = userDetailsId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Collection<UserDetailsDTO> getConnections() {
		return connections;
	}

	public void setConnections(Collection<UserDetailsDTO> connections) {
		this.connections = connections;
	}
	
	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
	public Date getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Date loggedIn) {
		this.loggedIn = loggedIn;
	}
}
