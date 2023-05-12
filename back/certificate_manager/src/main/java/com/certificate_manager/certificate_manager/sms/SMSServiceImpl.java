package com.certificate_manager.certificate_manager.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.enums.SecureTokenType;
import com.certificate_manager.certificate_manager.mail.tokens.ISecureTokenService;
import com.certificate_manager.certificate_manager.mail.tokens.SecureToken;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;



@Service
public class SMSServiceImpl implements ISMSService {
	static final String message = "Your verification code for Certivus is %s. It's valid for two minutes.";
	
	@Value("${spring.twilio-account-sid}")
	private String twilioAccountSid;
	
	@Value("${spring.twilio-phone-number}")
	private String twilioPhoneNumber;
	
	@Value("${spring.twilio-auth-token}")
	private String twilioAuthToken;
    	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISecureTokenService tokenService;

	@Override
    public void sendVerificationSMS(String email) {
		User user = userService.getUserByEmail(email);
		if (user.getVerified()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account has been already verified.");
		}
		SecureToken token = tokenService.createToken(user, SecureTokenType.REGISTRATION);
		
        Twilio.init(twilioAccountSid, twilioAuthToken);
        String text = String.format(message, token.getToken());

        Message.creator(new com.twilio.type.PhoneNumber(user.getPhoneNumber()),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber), text).create();

    }
	
	@Override
	public void sendResetSMS(String email) {
		User user = userService.getUserByEmail(email);
		SecureToken token = tokenService.createToken(user, SecureTokenType.FORGOT_PASSWORD);
		
		 Twilio.init(twilioAccountSid, twilioAuthToken); 
	        String text = String.format(message, token.getToken());

	        Message.creator(new com.twilio.type.PhoneNumber(user.getPhoneNumber()),
	                new com.twilio.type.PhoneNumber(twilioPhoneNumber), text).create();
	}
	

}
