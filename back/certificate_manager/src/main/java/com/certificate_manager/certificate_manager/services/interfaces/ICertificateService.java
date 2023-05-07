package com.certificate_manager.certificate_manager.services.interfaces;

import java.util.List;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.dtos.WithdrawalReasonDTO;
import com.certificate_manager.certificate_manager.entities.Certificate;

public interface ICertificateService {

	public List<CertificateDTO> getAll();
	
	public boolean validateBySerialNumber(String serialNumber);
	
	public boolean validateByUpload(String encodedFile);
	
	public CertificateDTO getBySerialNumber(String serialNumber);
	
	public void withdraw(String serialNumber, WithdrawalReasonDTO withdrawReasonDTO);
	
	public List<CertificateDTO> getAllForUser();

	public List<Certificate> getRootCertificate();

	public List<Certificate> getAllCertificatesWithCurrentCertificateAsIssuer(Certificate certificate);

	public void invalidateCurrentAndBelow(Certificate cert, String reason);

}
