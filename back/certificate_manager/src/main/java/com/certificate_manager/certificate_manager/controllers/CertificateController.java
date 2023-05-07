package com.certificate_manager.certificate_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.dtos.ResponseMessageDTO;
import com.certificate_manager.certificate_manager.dtos.WithdrawalReasonDTO;
import com.certificate_manager.certificate_manager.exceptions.UserNotFoundException;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Controller
@RequestMapping("api/certificate")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class CertificateController {
	
	@Autowired
	private ICertificateService certificateService;
	
	@Autowired
	private ICertificateGenerator certificateGenerator;
	
	
	@GetMapping(value = "")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> getAll() {
		System.out.println("tu");
		return new ResponseEntity<List<CertificateDTO>>(certificateService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/mine")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Sucessefully created root certificate."), HttpStatus.OK);
	}
	
	@GetMapping(value = "/validate/{serialNumber}")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> validateBySerialNumber(@PathVariable @NotEmpty String serialNumber){
		String validationMessage = "This certificate is valid!";
		if (!certificateService.validateBySerialNumber(serialNumber)) {
			validationMessage = "This certificate is not valid!";
		};
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO(validationMessage), HttpStatus.OK);
	}
	
	@PostMapping(value = "/validate-upload")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> validateByUpload(@Valid @RequestBody @NotEmpty String encodedFile){
		String validationMessage = "This certificate is valid!";
		System.out.println("neca");
		if (!certificateService.validateByUpload(encodedFile)) {
			validationMessage = "This certificate is not valid!";
		};
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO(validationMessage), HttpStatus.OK);
	}
	
	@PutMapping(value = "/withdraw/{serialNumber}")
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> withdraw(@PathVariable @NotEmpty String serialNumber, @Valid @RequestBody WithdrawalReasonDTO withdrawReasonDTO){
		this.certificateService.withdraw(serialNumber, withdrawReasonDTO);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Successfully withdraw of certificate"), HttpStatus.OK);
	}
}
