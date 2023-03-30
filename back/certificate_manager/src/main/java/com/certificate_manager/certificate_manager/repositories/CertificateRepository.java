package com.certificate_manager.certificate_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certificate_manager.certificate_manager.entities.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

}
