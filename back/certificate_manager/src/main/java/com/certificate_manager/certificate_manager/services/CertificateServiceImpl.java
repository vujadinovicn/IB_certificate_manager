package com.certificate_manager.certificate_manager.services;

import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.CertificateDTO;
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
	public void validate(String serialNumber) throws Exception {
		Certificate certificate = allCertificates.findBySerialNumber(serialNumber).orElseThrow(
				() -> new CertificateNotFoundException());
		if (this.hasCertificateExpired(certificate))
			System.out.println("Sertificate has expired!");
		this.verify(certificate);
	}
	
	private boolean hasCertificateExpired(Certificate certificate) {
		return certificate.getValidTo().isAfter(LocalDateTime.now());
	}
	
	private void verify(Certificate certificate) throws Exception {
		X509Certificate currentCert509 = allFileCertificates.readX509Certificate(certificate.getSerialNumber());
		X509Certificate issuerCert509 = allFileCertificates.readX509Certificate(certificate.getIssuerSerialNumber());
		currentCert509.verify(issuerCert509.getPublicKey());
	}
}
