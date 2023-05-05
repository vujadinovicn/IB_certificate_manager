package com.certificate_manager.certificate_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "There has been no SMS code previously sent.")
public class NoRequestForSMSVerification extends RuntimeException{

}
