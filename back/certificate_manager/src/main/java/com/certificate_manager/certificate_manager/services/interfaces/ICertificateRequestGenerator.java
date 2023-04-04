package com.certificate_manager.certificate_manager.services.interfaces;

import java.nio.file.AccessDeniedException;

import com.certificate_manager.certificate_manager.dtos.CertificateRequestDTO;

public interface ICertificateRequestGenerator {

	public void generateCertificateRequest(CertificateRequestDTO dto) throws AccessDeniedException;

}
