package com.certificate_manager.certificate_manager.entities;

import com.certificate_manager.certificate_manager.enums.UserRole;

public class User {

	private int id;

	private String name;

	private String lastname;

	private String email;

	private String phoneNumber;

	private String password;

	private Boolean verified;

	private UserRole role;

	public User() {

	}

	public User(int id, String name, String lastname, String email, String phoneNumber, String password,
			Boolean verified) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.verified = verified;
		this.role = UserRole.USER;
	}

	public User(int id, String name, String lastname, String email, String phoneNumber, String password,
			Boolean verified, UserRole role) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.verified = verified;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
