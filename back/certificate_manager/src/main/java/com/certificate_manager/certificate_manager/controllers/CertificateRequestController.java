package com.certificate_manager.certificate_manager.controllers;

import java.nio.file.AccessDeniedException;
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

import com.certificate_manager.certificate_manager.dtos.CertificateRequestCreateDTO;
import com.certificate_manager.certificate_manager.dtos.CertificateRequestReturnedDTO;
import com.certificate_manager.certificate_manager.dtos.ResponseMessageDTO;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateRequestGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateRequestService;

import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("api/certificate/request")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateRequestController {
	
	@Autowired
	private ICertificateRequestGenerator requestGenerator;
	
	@Autowired
	private ICertificateRequestService requestService;

	@GetMapping(value = "")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> getAllRequests() {
		return new ResponseEntity<List<CertificateRequestReturnedDTO>>(requestService.getAllForRequester(), HttpStatus.OK);
	}
	
	@PostMapping(value = "")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> generateCertificateRequest(@RequestBody CertificateRequestCreateDTO dto) throws AccessDeniedException {
		requestGenerator.generateCertificateRequest(dto);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Successfully created certificate request"), HttpStatus.OK);
	}
	
	@PutMapping(value = "/accept/{id}")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> acceptCertificateRequest(@PathVariable long id) {
		this.requestService.acceptRequest(id);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Request successfully accepted. Certificate generated."), HttpStatus.OK);
	}
	
	@PutMapping(value = "/deny/{id}")
//	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> denyCertificateRequest(@PathVariable long id, @RequestBody @NotNull String rejectionReason) {
		this.requestService.denyRequest(id, rejectionReason);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Request successfully denied."), HttpStatus.OK);
	}

}
