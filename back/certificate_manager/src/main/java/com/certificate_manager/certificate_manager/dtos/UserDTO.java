package com.certificate_manager.certificate_manager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
	
	@NotEmpty(message="is required")
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$", message="format is not valid")
	private String name;

	@NotEmpty(message="is required")
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$", message="format is not valid")
	private String lastname;
	
	@NotEmpty(message="is required")
	@Email(message="format is not valid")
	private String email;
	
	@NotEmpty(message="is required")
	@Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*", message="format is not valid")
	private String phoneNumber;
	
	@NotEmpty(message="is required")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")
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