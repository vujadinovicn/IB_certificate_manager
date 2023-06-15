package com.certificate_manager.certificate_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certificate_manager.certificate_manager.dtos.ResponseMessageDTO;
import com.certificate_manager.certificate_manager.dtos.TokenDTO;
import com.certificate_manager.certificate_manager.services.interfaces.IOAuthService;

@RestController
@RequestMapping("/api/oauth")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class OAuthController {
	
	@Autowired
	IOAuthService oathService;

	@GetMapping("callback")
    public ResponseEntity<?> handleCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state) {
		System.out.println(code + " " + state);
		TokenDTO token = this.oathService.signUpWithGoogle(code);
		return new ResponseEntity<TokenDTO>(token, HttpStatus.OK);
	}
}
