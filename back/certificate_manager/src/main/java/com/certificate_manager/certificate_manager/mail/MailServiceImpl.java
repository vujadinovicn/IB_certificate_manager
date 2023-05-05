package com.certificate_manager.certificate_manager.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.mail.tokens.SecureToken;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

@Service
public class MailServiceImpl implements IMailService {
	
	@Autowired
	SendGrid sendGrid;
	
	@Override
	public void sendTest(String token) {
		Email from = new Email("certificate.manager.tsn@gmail.com", "Certificate Manager");
		String subject = "Hello";
		Email to = new Email("srdjan.stjepanovic01@gmail.com");
		Content c = new Content("text/plain", "message");
		Mail mail = new Mail(from, subject, to, c);
		
		Personalization personalization = new Personalization();
	    personalization.addTo(to);
	    personalization.addDynamicTemplateData("firstName", "Srki");
	    personalization.addDynamicTemplateData("code", token);
	    mail.addPersonalization(personalization);
		mail.setTemplateId("d-e29ed48afc794a1bbdea8aac6b177d42");
		
		Request req = new Request();
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = this.sendGrid.api(req);
			System.out.println(res.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendVerificationMail(User user, String token) {
		System.out.println(user.getName());
		System.out.println(token);
		Email from = new Email("certificate.manager.tsn@gmail.com", "Certificate Manager");
		String subject = "SignUp Verification";
		Email to = new Email(user.getEmail());
		Content c = new Content("text/plain", "message");
		Mail mail = new Mail(from, subject, to, c);
		
		Personalization personalization = new Personalization();
	    personalization.addTo(to);
	    personalization.addDynamicTemplateData("firstName", user.getName());
	    personalization.addDynamicTemplateData("code", token);
	    mail.addPersonalization(personalization);
		mail.setTemplateId("d-e29ed48afc794a1bbdea8aac6b177d42");
		
		Request req = new Request();
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = this.sendGrid.api(req);
			System.out.println(res.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendForgotPasswordMail(User user, String token) {
		Email from = new Email("certificate.manager.tsn@gmail.com", "Certificate Manager");
		String subject = "Password reset";
		Email to = new Email(user.getEmail());
		
		Personalization personalization = new Personalization();
	    personalization.addTo(to);
	    personalization.addDynamicTemplateData("link", "http://localhost:4200/reset-password?code=" + token);
		
	    Mail mail = new Mail();
	    mail.setFrom(from);
	    mail.setSubject(subject);
	    mail.addPersonalization(personalization);
		mail.setTemplateId("d-ba583170bac644a5b0d2795dfe4bc34b ");
	    
		Request req = new Request();
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = this.sendGrid.api(req);
			System.out.println(res.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
