package com.certificate_manager.certificate_manager.sms;

public class SMSActivationDTO {
	private String username;
	private String code;
	
	public SMSActivationDTO() {
		
	}
	
	public SMSActivationDTO(String username, String code) {
		this.username = username;
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public String getCode() {
		return code;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}