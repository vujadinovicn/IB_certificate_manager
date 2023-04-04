package com.certificate_manager.certificate_manager.services.interfaces;

import java.nio.file.AccessDeniedException;

import com.certificate_manager.certificate_manager.dtos.CertificateRequestCreateDTO;

public interface ICertificateRequestGenerator {

	public void generateCertificateRequest(CertificateRequestCreateDTO dto) throws AccessDeniedException;

}
