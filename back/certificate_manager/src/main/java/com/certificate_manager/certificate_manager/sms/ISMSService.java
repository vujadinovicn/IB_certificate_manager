package com.certificate_manager.certificate_manager.sms;

public interface ISMSService {
	
	public void sendVerificationSMS(String email);

	public void sendResetSMS(String email);
}
