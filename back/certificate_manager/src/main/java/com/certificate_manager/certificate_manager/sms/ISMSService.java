package com.certificate_manager.certificate_manager.sms;

import com.certificate_manager.certificate_manager.dtos.UserDTO;

public interface ISMSService {
			  
	public void sendVerificationSMS(String email);

	public void sendResetSMS(String phoneNumber);

}