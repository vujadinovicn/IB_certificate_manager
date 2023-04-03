package com.certificate_manager.certificate_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificate_manager.certificate_manager.dtos.UserDTO;
import com.certificate_manager.certificate_manager.exceptions.UserAlreadyExistsException;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
		try {
			this.userService.register(userDTO);
			return new ResponseEntity<String>("You have successfully registered!", HttpStatus.OK);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<String>("User with this email already exists!", HttpStatus.NOT_FOUND);
		}
	}

}
