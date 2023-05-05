package com.certificate_manager.certificate_manager.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificate_manager.certificate_manager.dtos.UserDTO;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:61160")
@RequestMapping("/api/sms")
public class SMSController {
	
	@Autowired
	private ISMSService smsService;
	
	@PostMapping("")
//	@PreAuthorize("isAnonymous()")
    public ResponseEntity<?> sendSMS(@Valid @RequestBody UserDTO userDTO) {
		System.err.println("EEEEEEEEEEEEEEEE");
		smsService.sendSMS(userDTO);
    	return new ResponseEntity<String>("Code sent successfully!", HttpStatus.OK);
    }
      
    @PutMapping("")
    public ResponseEntity<?> sendNewSMS(@RequestBody UserDTO userDTO) throws Exception {
    	smsService.sendNewSMS(userDTO);
    	return new ResponseEntity<String>("New code sent successfully!", HttpStatus.OK);
    }
    
    @PutMapping("/activate")
    public ResponseEntity<?> activateBySMS(@RequestBody SMSActivationDTO smsActivationDTO) throws Exception {
    	smsService.activateBySMS(smsActivationDTO);
    	return new ResponseEntity<String>("Your account has been successfully activated!", HttpStatus.OK);
    }

}