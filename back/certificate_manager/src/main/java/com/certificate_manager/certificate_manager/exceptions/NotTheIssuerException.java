package com.certificate_manager.certificate_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Only the issuer can manage certificate requests.")
public class NotTheIssuerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5177496587580335467L;

}
