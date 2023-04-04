package com.certificate_manager.certificate_manager.controllers;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.dtos.CertificateRequestDTO;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateRequestGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("api/certificate")
public class CertificateController {
	
	@Autowired
	private ICertificateService certificateService;
	
	@Autowired
	private ICertificateGenerator certificateGenerator;
	
	@Autowired
	private ICertificateRequestGenerator requestGenerator;
	
	
	@GetMapping(value = "")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<List<CertificateDTO>>(certificateService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/root")
	public ResponseEntity<?> generateRoot() {
		certificateGenerator.generateSelfSignedCertificate();
		return new ResponseEntity<String>("Sucessefully created root certificate.", HttpStatus.OK);
	}
	
	@PostMapping(value = "/request")
	public ResponseEntity<?> generateCertificateRequest(@RequestBody CertificateRequestDTO dto) throws AccessDeniedException {
		requestGenerator.generateCertificateRequest(dto);
		return new ResponseEntity<String>("Successfully created certificate request", HttpStatus.OK);
	}
	
	@GetMapping(value = "validate/{serialNumber}")
	public ResponseEntity<?> validate(@PathVariable String serialNumber){
		certificateService.validate(serialNumber);
		return null;
	}
}
