package com.certificate_manager.certificate_manager.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class MailServiceImpl implements IMailService {
	
	@Autowired
	SendGrid sendGrid;
	
	@Override
	public void sendTest() {
		Email from = new Email("stjepanovic.nis@gmail.com", "Certificate Manager");
		String subject = "Hello";
		Email to = new Email("srdjan.stjepanovic01@gmail.com");
		Content c = new Content("text/plain", "message");
		Mail mail = new Mail(from, subject, to, c);
		
		Request req = new Request();
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = this.sendGrid.api(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
