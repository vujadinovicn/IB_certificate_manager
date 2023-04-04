package com.certificate_manager.certificate_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Certificate is not valid!")
public class CertificateNotValidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -113134117710322094L;

}
