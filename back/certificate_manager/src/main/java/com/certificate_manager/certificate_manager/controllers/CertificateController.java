package com.certificate_manager.certificate_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.exceptions.UserNotFoundException;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateService;

@Controller
@RequestMapping("api/certificate")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateController {
	
	@Autowired
	private ICertificateService certificateService;
	
	@Autowired
	private ICertificateGenerator certificateGenerator;
	
	
	@GetMapping(value = "")
	public ResponseEntity<?> getAll() {
		System.out.println("tu");
		return new ResponseEntity<List<CertificateDTO>>(certificateService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/mine")
	public ResponseEntity<?> getAllForUser() {
		try {
			return new ResponseEntity<List<CertificateDTO>>(certificateService.getAllForUser(), HttpStatus.OK);
		} catch(UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/root")
	public ResponseEntity<?> generateRoot() {
		certificateGenerator.generateSelfSignedCertificate();
		return new ResponseEntity<String>("Sucessefully created root certificate.", HttpStatus.OK);
	}
	
	@GetMapping(value = "/validate/{serialNumber}")
//	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> validate(@PathVariable String serialNumber){
		String validationMessage = "This certificate is valid!";
		if (!certificateService.validate(serialNumber)) {
			validationMessage = "This certificate is not valid!";
		};
		return new ResponseEntity<String>(validationMessage, HttpStatus.OK);
	}
}
