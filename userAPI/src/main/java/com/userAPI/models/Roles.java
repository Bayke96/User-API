package com.userAPI.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Role")
public class Roles {
	
	@Id
	@NotNull
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	@NotNull(message = "The field name cannot be null")
	@Column(name = "role_name", unique = true)
	@Size(min = 3, max = 48, message = "The role name must contain between 3-48 characters")
	public String name = "User";
	
	@NotNull
	@Min(0)
	@Column(name = "role_members")
	public int ammountMembers = 0;
	
	// --------------------------------------------- CONSTRUCTORS ---------------------------------------------------- //
	
	public Roles() { }
	
	public Roles(int id) {
		this.id = id;
	}
	
	public Roles(String name) {
		this.name = name;
	}
	
	// --------------------------------------------- GETTERS ---------------------------------------------------- //
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAmmountMembers() {
		return this.ammountMembers;
	}
	
	// --------------------------------------------- SETTERS ---------------------------------------------------- //
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAmmountMembers(int ammount) {
		this.ammountMembers = ammount;
	}

}
