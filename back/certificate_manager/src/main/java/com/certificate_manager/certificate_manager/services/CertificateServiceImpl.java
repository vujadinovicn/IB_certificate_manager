package com.certificate_manager.certificate_manager.services;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
import com.certificate_manager.certificate_manager.dtos.WithdrawReasonDTO;
import com.certificate_manager.certificate_manager.entities.Certificate;
import com.certificate_manager.certificate_manager.exceptions.CertificateNotFoundException;
import com.certificate_manager.certificate_manager.repositories.CertificateFileRepository;
import com.certificate_manager.certificate_manager.repositories.CertificateRepository;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateService;

@Service
public class CertificateServiceImpl implements ICertificateService {
	
	@Autowired
	private CertificateRepository allCertificates;
	
	@Autowired
	private CertificateFileRepository allFileCertificates;
	
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

	@Override
	public boolean validateBySerialNumber(String serialNumber){
		Certificate certificate = allCertificates.findBySerialNumber(serialNumber).orElseThrow(
				() -> new CertificateNotFoundException());
		try {
			if (this.hasCertificateExpired(certificate) || !certificate.isValid())
				return false;
			
			this.verify(certificate);
			
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
	
	@Override
	public boolean validateByUpload(String encodedFile){
		try {
			byte encodedCert[] = Base64.getDecoder().decode(encodedFile.split(",")[1]);
			ByteArrayInputStream inputStream  =  new ByteArrayInputStream(encodedCert);
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			System.out.println("dovde");
			X509Certificate cert = (X509Certificate)certFactory.generateCertificate(inputStream);
			return this.validateBySerialNumber(cert.getSerialNumber().toString());
		} catch (CertificateException e) {
			return false;
		}
	}
	
	private boolean hasCertificateExpired(Certificate certificate) {
		return !certificate.getValidTo().isAfter(LocalDateTime.now());
	}
	
	private void verify(Certificate certificate) throws Exception {
		X509Certificate currentCert509 = allFileCertificates.readX509Certificate(certificate.getSerialNumber());
		X509Certificate issuerCert509 = allFileCertificates.readX509Certificate(certificate.getIssuerSerialNumber());
		currentCert509.verify(issuerCert509.getPublicKey());
	}
    
	public CertificateDTO getBySerialNumber(String serialNumber) {
		Certificate cert = allCertificates.findBySerialNumber(serialNumber).orElse(null);
		
		if (cert == null) {
			throw new CertificateNotFoundException();
		}
		
		return new CertificateDTO(cert);
	}

	@Override
	public void withdraw(String serialNumber, WithdrawReasonDTO withdrawReasonDTO) {
		// TODO Auto-generated method stub
		
	}
}
