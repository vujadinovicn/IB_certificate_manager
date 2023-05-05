package com.certificate_manager.certificate_manager.sms;

import com.certificate_manager.certificate_manager.dtos.UserDTO;

public interface ISMSService {
	
	public void sendSMS(UserDTO userDTO);
	
	public void sendNewSMS(UserDTO userDTO);
	  
	public void activateBySMS(SMSActivationDTO smsActivationDTO);

}