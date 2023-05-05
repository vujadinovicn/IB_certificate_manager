package com.certificate_manager.certificate_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "SMS code entered is incorrect!")
public class SMSCodeIncorrectException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2382631640046554993L;

}
