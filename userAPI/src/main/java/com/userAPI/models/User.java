package com.userAPI.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
public class User {
	
	@Id
	@NotNull
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "usr_role")
	private Roles role;
	
	@Size(min = 3, max = 24, message = "The fullname must contain between 3-24 characters")
	@NotNull(message = "The fullname cannot be null")
	@Column(name = "usr_fullname")
	private String fullName;
	
	@Size(min = 8, message = "The password must contain at least 8 characters, a letter and a number")
	@NotNull(message = "The password cannot be null")
	@JsonIgnore
	@Column(name = "usr_password")
	private String password;
	
	@Size(min = 8, message = "The phone number must contain at least 8 digits")
	@NotNull(message = "The email cannot be null")
	@Column(name = "usr_email", unique = true)
	private String email;
	
	@Size(min = 6, max = 24, message = "The limit is between 6 and 24 characters")
	@NotNull(message = "The phone number cannot be null")
	@Column(name = "usr_number")
	private String phoneNumber;
	
	@Size(max = 128, message = "The address allows up to 128 characters")
	@Column(name = "usr_address")
	private String address;
	
	@Size(min = 8, message = "The optional password must contain at least 8 characters")
	@JsonIgnore
	@Column(name = "usr_opt")
	private String optPassword;
	
	@Size(max = 128)
	@Column(name = "usr_pic")
	private String profilePic;
	
	// --------------------------------------------- CONSTRUCTORS ---------------------------------------------------- //
	
	public User() { }
	
	
	// --------------------------------------------- GETTERS ---------------------------------------------------- //
	
	public int getId() {
		return this.id;
	}
	
	public Roles getRole() {
		return this.role;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getOptPassword() {
		return this.optPassword;
	}
	
	public String getProfilePic() {
		return this.profilePic;
	}
	
	// --------------------------------------------- SETTERS ---------------------------------------------------- //
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setRole(Roles role) {
		this.role = role;
	}
	
	public void setFullName(String fullname) {
		this.fullName = fullname;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setOptPassword(String optPassword) {
		this.optPassword = optPassword;
	}
	
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

}
