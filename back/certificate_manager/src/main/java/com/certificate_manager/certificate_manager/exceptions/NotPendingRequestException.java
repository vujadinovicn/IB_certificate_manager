package com.certificate_manager.certificate_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Only pending requests can be processed.")
public class NotPendingRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8613544922707985432L;

}
