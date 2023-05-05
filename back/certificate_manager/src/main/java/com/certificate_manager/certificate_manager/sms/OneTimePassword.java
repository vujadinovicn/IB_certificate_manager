package com.certificate_manager.certificate_manager.sms;

import java.time.LocalDateTime;

public class OneTimePassword {
	private int code;
	private LocalDateTime timeOfCreation;
	
	public OneTimePassword() {
		
	}
	
	public OneTimePassword(int code, LocalDateTime timeOfCreation) {
		this.code = code;
		this.timeOfCreation = timeOfCreation;
	}

	public int getCode() {
		return code;
	}

	public LocalDateTime getTimeOfCreation() {
		return timeOfCreation;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setTimeOfCreation(LocalDateTime timeOfCreation) {
		this.timeOfCreation = timeOfCreation;
	}

}

