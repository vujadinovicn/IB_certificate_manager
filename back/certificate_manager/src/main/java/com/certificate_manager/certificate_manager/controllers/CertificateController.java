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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.dtos.WithdrawReasonDTO;
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
		return new ResponseEntity<List<CertificateDTO>>(certificateService.getAll(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/root")
	public ResponseEntity<?> generateRoot() {
		certificateGenerator.generateSelfSignedCertificate();
		return new ResponseEntity<String>("Sucessefully created root certificate.", HttpStatus.OK);
	}
	
	@GetMapping(value = "/validate/{serialNumber}")
//	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> validateBySerialNumber(@PathVariable String serialNumber){
		String validationMessage = "This certificate is valid!";
		if (!certificateService.validateBySerialNumber(serialNumber)) {
			validationMessage = "This certificate is not valid!";
		};
		return new ResponseEntity<String>(validationMessage, HttpStatus.OK);
	}
	
	@PostMapping(value = "/validate-upload")
//	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> validateByUpload(@RequestBody String encodedFile){
		String validationMessage = "This certificate is valid!";
		System.out.println("neca");
		if (!certificateService.validateByUpload(encodedFile)) {
			validationMessage = "This certificate is not valid!";
		};
		return new ResponseEntity<String>(validationMessage, HttpStatus.OK);
	}
	
	@PutMapping(value = "/withdraw/{serialNumber}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> withdraw(@PathVariable String serialNumber, @RequestBody WithdrawReasonDTO withdrawReasonDTO){
		this.certificateService.withdraw(serialNumber, withdrawReasonDTO);
		return new ResponseEntity<String>(validationMessage, HttpStatus.OK);
	}
}
