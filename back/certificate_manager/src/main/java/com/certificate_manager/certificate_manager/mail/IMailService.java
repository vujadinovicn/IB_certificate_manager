package com.certificate_manager.certificate_manager.mail;

import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.mail.tokens.SecureToken;

public interface IMailService {

	public void sendTest(String token);

	public void sendVerificationMail(User user, String token);

	public void sendForgotPasswordMail(User user, String token);

}
