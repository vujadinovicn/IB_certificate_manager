package com.certificate_manager.certificate_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificate_manager.certificate_manager.dtos.CredentialsDTO;
import com.certificate_manager.certificate_manager.dtos.ResetPasswordDTO;
import com.certificate_manager.certificate_manager.dtos.ResponseMessageDTO;
import com.certificate_manager.certificate_manager.dtos.TokenDTO;
import com.certificate_manager.certificate_manager.dtos.UserDTO;
import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.security.jwt.TokenUtils;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICertificateGenerator certificateGenerator;
	
	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
		this.userService.register(userDTO);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("You have successfully registred."), HttpStatus.OK);
	}
	
	@GetMapping(value = "activate/{activationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyRegistration(@PathVariable("activationId") String verificationCode) {
		this.userService.verifyRegistration(verificationCode);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("You have successfully activated your account!"), HttpStatus.OK);
	}
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@Valid @RequestBody CredentialsDTO credentials) {
		System.out.println(credentials);
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<String>("Wrong username or password!", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails user = (UserDetails) authentication.getPrincipal();
		User fullUser = this.userService.getUserByEmail(credentials.getEmail());
		if (!fullUser.getVerified()) {
			return new ResponseEntity<String>("This account hasn't been activated yet.", HttpStatus.BAD_REQUEST);
		}
		String jwt = tokenUtils.generateToken(user, fullUser.getId());

		return new ResponseEntity<TokenDTO>(new TokenDTO(jwt, jwt), HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "{email}/resetPasswordEmail")
	public ResponseEntity<?> resetPassword(@PathVariable String email) {
		this.userService.sendResetPasswordMail(email);
		return new ResponseEntity<String>("Email with reset code has been sent!", HttpStatus.NO_CONTENT);
	}

	
	@PutMapping(value = "{id}/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
		this.userService.resetPassword(dto);
		return new ResponseEntity<String>("Password successfully changed!", HttpStatus.NO_CONTENT);
	}

}
