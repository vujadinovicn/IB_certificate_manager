package com.certificate_manager.certificate_manager.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/mail")
public class MailController {
	
	@Autowired
	private IMailService service;
	
	@PostMapping
	public ResponseEntity send() {
		this.service.sendTest();
		return new ResponseEntity(HttpStatus.OK);
	}
}
