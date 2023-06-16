package com.certificate_manager.certificate_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.certificate_manager.certificate_manager.dtos.ResponseMessageDTO;
import com.certificate_manager.certificate_manager.services.interfaces.IScheduledService;
import com.certificate_manager.certificate_manager.sms.ISmsService;

import jakarta.transaction.Transactional;

@Controller
public class ScheduledController {
	
	@Autowired  
	private IScheduledService scheduledService; 

	@Autowired
	private ISmsService smsService;
	@Transactional
	@Scheduled(initialDelayString = "${initialdelay}", fixedDelayString = "${fixeddelay}")
	public void test() {
		//smsService.sendVerificationSMS("vujadinovic01@gmail.com");
    	//return new ResponseEntity<ResponseMessageDTO>(new ResponseMessageDTO("Code sent successfully!"), HttpStatus.OK);
		System.err.println("ajde");
		this.scheduledService.invalidateCertificatesWhichExpired();
	} 
} 
