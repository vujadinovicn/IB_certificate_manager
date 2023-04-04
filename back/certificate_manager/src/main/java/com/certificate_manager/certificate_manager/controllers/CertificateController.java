package com.certificate_manager.certificate_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateService;

@Controller
@RequestMapping("api/certificate")
public class CertificateController {
	
	@Autowired
	private ICertificateService certificateService;
	
	@Autowired
	private ICertificateGenerator certificateGenerator;
	
	
	@GetMapping(value = "")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<List<CertificateDTO>>(certificateService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/root")
	public ResponseEntity<?> generateRoot() {
		certificateGenerator.generateSelfSignedCertificate();
		return new ResponseEntity<String>("Sucessefully created root certificate.", HttpStatus.OK);
	}
}
