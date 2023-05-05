package com.certificate_manager.certificate_manager.sms;

public class SMSActivationDTO {
	private String email;
	private String code;
	
	public SMSActivationDTO() {
		
	}
	
	public SMSActivationDTO(String email, String code) {
		this.email = email;
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public String getCode() {
		return code;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}