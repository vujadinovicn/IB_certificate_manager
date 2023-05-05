package com.certificate_manager.certificate_manager.sms;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.UserDTO;
import com.certificate_manager.certificate_manager.exceptions.UserNotRegisteredOrAlreadyVerifiedException;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SMSServiceImpl implements ISMSService{

	static final String message = "Your verification code for Certivus is %d. It's valid for two minutes.";
	
	@Value("${spring.twilio-account-sid}")
	private String twilioAccountSid;
	
	@Value("${spring.twilio-phone-number}")
	private String twilioPhoneNumber;
	
	@Value("${spring.twilio-auth-token}")
	private String twilioAuthToken;
    
	private Map<String, OneTimePassword> allOneTimePasswords = new HashMap<String, OneTimePassword>();
	
	@Autowired
	private IUserService userService;

	@Override
    public void sendSMS(UserDTO userDTO) {
		if (!userService.isUserRegisteredAndNotVerified(userDTO.getEmail()))
			throw new UserNotRegisteredOrAlreadyVerifiedException();
		
        Twilio.init(twilioAccountSid, twilioAuthToken);
        int verificationCode = generateCode();
        String text = String.format(message, verificationCode);

        Message.creator(new com.twilio.type.PhoneNumber(userDTO.getPhoneNumber()),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber), text).create();

        allOneTimePasswords.put(userDTO.getEmail(), new OneTimePassword(verificationCode, LocalDateTime.now()));
    }
    
	
	
	private boolean hasCodeExpired(OneTimePassword oneTimePassword) {
    	return LocalDateTime.now().isAfter(oneTimePassword.getTimeOfCreation().plusMinutes(2));
    }
    
    private int generateCode() {
    	Random rand = new Random();
        int min = 100000;
        int max = 999999;
        return min + rand.nextInt((max - min) + 1);
    }



	@Override
	public void sendNewSMS(UserDTO userDTO) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void activateBySMS(SMSActivationDTO smsActivationDTO) {
		// TODO Auto-generated method stub
		
	}

}