package com.certificate_manager.certificate_manager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificate_manager.certificate_manager.dtos.UserDTO;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insertPassenger(@RequestBody UserDTO dto) {
		System.out.println(dto);
		return new ResponseEntity<String>("eee", HttpStatus.OK);
	}

}
