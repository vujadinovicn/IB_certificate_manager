package com.certificate_manager.certificate_manager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificate_manager.certificate_manager.dtos.CredentialsDTO;
import com.certificate_manager.certificate_manager.dtos.ResetPasswordDTO;
import com.certificate_manager.certificate_manager.dtos.ResponseMessageDTO;
import com.certificate_manager.certificate_manager.dtos.TokenDTO;
import com.certificate_manager.certificate_manager.dtos.UserDTO;
import com.certificate_manager.certificate_manager.dtos.UserRetDTO;
import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.security.jwt.IJWTTokenService;
import com.certificate_manager.certificate_manager.security.jwt.TokenUtils;
import com.certificate_manager.certificate_manager.security.recaptcha.ValidateCaptcha;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ILoggingService;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ValidateCaptcha captchaValidator;
	
	@Autowired
	private ICertificateGenerator certificateGenerator;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private IJWTTokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ILoggingService loggingService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		loggingService.logUserInfo("Arrived request GET /api/user/{id}", logger);
		return new ResponseEntity<UserRetDTO>(this.userService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, @RequestHeader String captcha) {
		loggingService.logUserInfo("Arrived request POST /api/user/", logger);
		
		this.captchaValidator.validateCaptcha(captcha);
		loggingService.logServerInfo("Captcha validated successfully", logger);
		
		this.userService.register(userDTO);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("You have successfully registered!"), HttpStatus.OK);
	}
	
	@PostMapping(value = "send/verification/email/{email}")
	public ResponseEntity<?> sendVerificationMail(@PathVariable @NotEmpty(message = "Email is required") String email) {
		loggingService.logUserInfo("Arrived request POST /api/user/send/verification/email/{email}", logger);
		this.userService.sendEmailVerification(email); 
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("We sent you a verification code!"), HttpStatus.OK);
	}
	
	@PostMapping(value = "send/twofactor/email/{email}")
	public ResponseEntity<?> sendTwoFactorMail(@PathVariable @NotEmpty(message = "Email is required") String email) {
		loggingService.logUserInfo("Arrived request POST /api/user/send/twofactor/email/{email}", logger);
		this.userService.sendTwoFactorEmail(email); 
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("We sent you a verification code!"), HttpStatus.OK);
	}
	
	@GetMapping(value = "verify/twofactor/{activationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyTwoFactor(@PathVariable("activationId") @NotEmpty(message = "Activation code is required") String verificationCode, HttpServletRequest request) {
		loggingService.logUserInfo("Arrived request GET /api/user/verify/twofactor/{activationId}", logger);
		this.userService.verifyTwoFactor(verificationCode, tokenUtils.getToken(request));
		loggingService.logUserInfo("User signed in. Email=" + userService.getCurrentUser().getEmail(), logger);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("You have successfully signed in!"), HttpStatus.OK);
	}
	
	@GetMapping(value = "activate/{activationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyRegistration(@PathVariable("activationId") @NotEmpty(message = "Activation code is required") String verificationCode) {
		loggingService.logUserInfo("Arrived request GET /api/user/activate/{activationId}", logger);
		this.userService.verifyRegistration(verificationCode);
		loggingService.logUserInfo("Verified registration.", logger);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("You have successfully activated your account!"), HttpStatus.OK);
	}
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@Valid @RequestBody CredentialsDTO credentials, @RequestHeader String captcha) {
		System.out.println(credentials);
		loggingService.logUserInfo("Arrived request POST /api/user/login; Credentials="+credentials, logger);
		
		this.captchaValidator.validateCaptcha(captcha);
		loggingService.logServerInfo("Captcha validated successfully", logger);

		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
		} catch (BadCredentialsException e) {
			loggingService.logServerInfo("Bad credentials; Credentials="+credentials, logger);
			return new ResponseEntity<String>("Wrong username or password!", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails user = (UserDetails) authentication.getPrincipal();
		User userFromDb = this.userService.getUserByEmail(credentials.getEmail());
		
		if (!userFromDb.getVerified()) {
			loggingService.logServerInfo("Account with credentials=" + credentials + " have not been activated yet!", logger);
			return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("This account have not been activated yet!"), HttpStatus.UNAUTHORIZED);
		}
		
		String jwt = tokenUtils.generateToken(user, userFromDb);
		this.tokenService.createToken(jwt);
		
		loggingService.logServerInfo("Signed in new user. Email="+userFromDb.getEmail(), logger);
		return new ResponseEntity<TokenDTO>(new TokenDTO(jwt, jwt), HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "reset/password/email/{email}")
	public ResponseEntity<?> sendResetPasswordMail(@PathVariable @NotEmpty(message = "Email is required") String email) {
		System.err.println("usao");
		loggingService.logUserInfo("Arrived request GET /api/user/reset/password/email/{email}; Email="+email, logger);
		this.userService.sendResetPasswordMail(email);
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Email with reset code has been sent!"), HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "resetPassword")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
		loggingService.logUserInfo("Arrived request PUT /api/user/resetPassword", logger);
		this.userService.resetPassword(dto);	
		return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Password successfully changed!"), HttpStatus.NO_CONTENT);
	}
	

}
