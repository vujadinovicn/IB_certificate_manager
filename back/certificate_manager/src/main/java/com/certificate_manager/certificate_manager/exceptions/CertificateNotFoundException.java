package com.certificate_manager.certificate_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Certificate not found!")
public class CertificateNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6662315756436014103L;

}
