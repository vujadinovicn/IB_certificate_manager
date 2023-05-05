package com.certificate_manager.certificate_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "SMS code has expired!")
public class SMSCodeExpiredException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5642172271171327234L;

}
