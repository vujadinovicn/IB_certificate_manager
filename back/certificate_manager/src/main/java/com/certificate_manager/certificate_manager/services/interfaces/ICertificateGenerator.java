package com.certificate_manager.certificate_manager.services.interfaces;

import java.security.cert.X509Certificate;

import com.certificate_manager.certificate_manager.entities.CertificateRequest;

public interface ICertificateGenerator {
	
	public X509Certificate generateCertificate(CertificateRequest request);
}
