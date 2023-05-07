package com.certificate_manager.certificate_manager.services.interfaces;

import java.util.List;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.dtos.WithdrawReasonDTO;

public interface ICertificateService {

	public List<CertificateDTO> getAll();
	
	public boolean validateBySerialNumber(String serialNumber);
	public boolean validateByUpload(String encodedFile);
	public CertificateDTO getBySerialNumber(String serialNumber);

	public void withdraw(String serialNumber, WithdrawReasonDTO withdrawReasonDTO);

}
