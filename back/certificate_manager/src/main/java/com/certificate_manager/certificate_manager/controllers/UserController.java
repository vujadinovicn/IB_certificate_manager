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
import org.springframework.validation.annotation.Validated;
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
import com.certificate_manager.certificate_manager.sms.ISMSService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICertificateGenerator certificateGenerator;
	
	@Autowired  
	private TokenUtils tokenUtils;
	
	@Autowired 
	private ISMSService smsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
		this.userService.register(userDTO);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("You have successfully registred."), HttpStatus.OK);
	}
	
	@PostMapping(value = "send/verification/email/{email}")
	public ResponseEntity<?> sendVerificationMail(@PathVariable @NotEmpty(message = "Email is required") String email) {
		this.userService.sendEmailVerification(email); 
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("We sent you a verification code!"), HttpStatus.OK);
	}
	
	@PostMapping(value = "send/verification/sms/{email}")
    public ResponseEntity<?> sendVerificationSMS(@PathVariable @NotEmpty(message = "Email is required") String email) {
		smsService.sendVerificationSMS(email);
    	return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Code sent successfully!"), HttpStatus.OK);
    } 
	 
	
	@GetMapping(value = "activate/{activationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyRegistration(@PathVariable("activationId") @NotEmpty(message = "Activation code is required") String verificationCode) {
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
	
	
	@GetMapping(value = "reset/password/email/{email}")
	public ResponseEntity<?> sendResetPasswordMail(@PathVariable @NotEmpty(message = "Email is required") String email) {
//		System.err.println("usao");
		this.userService.sendResetPasswordMail(email);
//		System.out.println("eee");
		return new ResponseEntity<String>("If this email exists, reset code has been sent to it!", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "reset/password/sms/{phoneNumber}")
	public ResponseEntity<?> sendResetPasswordSms(@PathVariable @NotEmpty(message = "Phone number is required") String phoneNumber) {
		this.smsService.sendResetSMS(phoneNumber);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("If this number exists, reset code has been sent to it!"), HttpStatus.NO_CONTENT);
	}

//	http://localhost:4388/api/user/vujadinovic01@gmail.com/reset/password/sms
	
	@PutMapping(value = "resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
		this.userService.resetPassword(dto);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Password successfully changed!"), HttpStatus.NO_CONTENT);
	}

}
