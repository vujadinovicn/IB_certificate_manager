package com.certificate_manager.certificate_manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.certificate_manager.certificate_manager.entities.CertificateRequest;

public interface CertificateRequestRepository extends JpaRepository<CertificateRequest, Long> {

	@Query("select cr from CertificateRequest cr where cr.requester.id = ?1")
	public List<CertificateRequest> findAllForRequester(long requesterId);
}
