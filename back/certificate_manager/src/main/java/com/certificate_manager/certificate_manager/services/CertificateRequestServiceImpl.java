package com.certificate_manager.certificate_manager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.dtos.CertificateRequestCreateDTO;
import com.certificate_manager.certificate_manager.dtos.CertificateRequestReturnedDTO;
import com.certificate_manager.certificate_manager.entities.Certificate;
import com.certificate_manager.certificate_manager.entities.CertificateRequest;
import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.exceptions.CertificateNotFoundException;
import com.certificate_manager.certificate_manager.repositories.CertificateRepository;
import com.certificate_manager.certificate_manager.repositories.CertificateRequestRepository;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateRequestService;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateService;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

@Service
public class CertificateRequestServiceImpl implements ICertificateRequestService {
	
	@Autowired
	private CertificateRequestRepository allRequests;
	
	@Autowired
	private ICertificateService certificateService;
	
	@Autowired
	private IUserService userService;

	@Override
	public List<CertificateRequestReturnedDTO> getAllForRequester() {
		User requester = userService.getCurrentUser();
		
		List<CertificateRequest> requests = allRequests.findAllForRequester(requester.getId());
		
		List<CertificateRequestReturnedDTO> ret = new ArrayList<CertificateRequestReturnedDTO>();
		for (CertificateRequest req : requests) {
			CertificateDTO issuer = certificateService.getBySerialNumber(req.getIssuerSerialNumber());
			ret.add(new CertificateRequestReturnedDTO(req, issuer));
		}
		return ret;
	}

}
