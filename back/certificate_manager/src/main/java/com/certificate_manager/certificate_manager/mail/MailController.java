package com.certificate_manager.certificate_manager.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.enums.SecureTokenType;
import com.certificate_manager.certificate_manager.mail.tokens.ISecureTokenService;
import com.certificate_manager.certificate_manager.mail.tokens.SecureToken;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/mail")
public class MailController {
	
	@Autowired
	private IMailService service;
	
	@Autowired
	private ISecureTokenService tokenService;
	
	@PostMapping
	public ResponseEntity send() {
		this.service.sendTest("1234");
		return new ResponseEntity(HttpStatus.OK);
	}
}
