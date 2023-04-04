package com.certificate_manager.certificate_manager.services;

import java.nio.file.AccessDeniedException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.CertificateRequestCreateDTO;
import com.certificate_manager.certificate_manager.entities.CertificateRequest;
import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.enums.CertificateType;
import com.certificate_manager.certificate_manager.enums.UserRole;
import com.certificate_manager.certificate_manager.exceptions.CertificateNotFoundException;
import com.certificate_manager.certificate_manager.repositories.CertificateRepository;
import com.certificate_manager.certificate_manager.repositories.CertificateRequestRepository;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateRequestGenerator;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

@Service
public class CertificateRequestGenerator implements ICertificateRequestGenerator {
	
	@Autowired
	private CertificateRequestRepository allRequests;
	
	@Autowired
	private CertificateRepository allCertificates;
	
	@Autowired 
	private ICertificateGenerator certificateGenerator;
	
	@Autowired
	private IUserService userService;
	
	@Override 
	public void generateCertificateRequest(CertificateRequestCreateDTO dto) throws AccessDeniedException {
		User user = userService.getCurrentUser();
		if (user.getRole() == UserRole.ADMIN && dto.getType() == CertificateType.ROOT) {
			certificateGenerator.generateSelfSignedCertificate();
		}
		else {
			createRequestByDto(dto, user);
		}
		
	}
	
	private void createRequestByDto(CertificateRequestCreateDTO dto, User user) throws AccessDeniedException {
		if (dto.getValidTo().isBefore(LocalDateTime.now())) {
			throw new DateTimeException("Incorrect date!");
		}
		else if (allCertificates.findBySerialNumber(dto.getIssuerSerialNumber()).isEmpty()) {
			throw new CertificateNotFoundException();
		}
		else if (user.getRole() != UserRole.ADMIN && dto.getType() == CertificateType.ROOT) {
			throw new AccessDeniedException(null);
		}
		
		CertificateRequest request = new CertificateRequest(dto, userService.getCurrentUser());
		this.allRequests.save(request);
		this.allRequests.flush();
		
		if (user.getRole() == UserRole.ADMIN) {
			certificateGenerator.generateCertificate(request);
		}
	}
	
}
