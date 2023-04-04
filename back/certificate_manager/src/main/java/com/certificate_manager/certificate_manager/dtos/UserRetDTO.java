package com.certificate_manager.certificate_manager.dtos;

import com.certificate_manager.certificate_manager.entities.User;

public class UserRetDTO {

	private int id;
	private String name;
	private String lastName;
	private String email;

	public UserRetDTO() {
	}

	public UserRetDTO(int id, String name, String lastName, String email) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UserRetDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.lastName = user.getLastname();
		this.email = user.getEmail();
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
