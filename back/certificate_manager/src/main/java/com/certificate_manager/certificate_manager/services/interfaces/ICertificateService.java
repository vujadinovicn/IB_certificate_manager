package com.certificate_manager.certificate_manager.services.interfaces;

import java.util.List;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;

public interface ICertificateService {

	public List<CertificateDTO> getAll();

	public boolean validate(String serialNumber);

	public CertificateDTO getBySerialNumber(String serialNumber);

	public List<CertificateDTO> getAllForUser();

}
