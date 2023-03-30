package com.certificate_manager.certificate_manager.dtos;

public class UserDTO {
	
	private String name;

	private String lastname;
	
	private String email;
	
	private String phoneNumber;
	
	private String password;
	
	public UserDTO() {
		
	}

	public UserDTO(String name, String lastname, String email, String phoneNumber, String password) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
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
	
	
	
}