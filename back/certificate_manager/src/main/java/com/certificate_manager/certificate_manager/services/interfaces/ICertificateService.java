package com.certificate_manager.certificate_manager.services.interfaces;

import java.util.List;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.entities.Certificate;

public interface ICertificateService {

	public List<CertificateDTO> getAll();
	
	public void validate(String serialNumber);

}
