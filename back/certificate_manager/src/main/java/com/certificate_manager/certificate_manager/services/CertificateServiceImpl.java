package com.certificate_manager.certificate_manager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.entities.Certificate;
import com.certificate_manager.certificate_manager.exceptions.CertificateNotFoundException;
import com.certificate_manager.certificate_manager.repositories.CertificateRepository;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateService;

@Service
public class CertificateServiceImpl implements ICertificateService {
	
	@Autowired
	private CertificateRepository allCertificates;
	
	@Override
	public List<CertificateDTO> getAll() {
		List<Certificate> certs = allCertificates.findAll();
		if (certs.size() == 0) {
			throw new CertificateNotFoundException();
		}
		List<CertificateDTO> ret = new ArrayList<CertificateDTO>();
		for (Certificate cert : certs) {
			ret.add(new CertificateDTO(cert));
		}
		return ret;
	}
}
