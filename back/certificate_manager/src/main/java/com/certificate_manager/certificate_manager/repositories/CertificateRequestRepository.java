package com.certificate_manager.certificate_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certificate_manager.certificate_manager.entities.CertificateRequest;

public interface CertificateRequestRepository extends JpaRepository<CertificateRequest, Long> {

}
