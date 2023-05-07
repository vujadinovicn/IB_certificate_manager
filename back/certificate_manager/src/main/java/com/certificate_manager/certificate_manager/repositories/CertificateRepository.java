package com.certificate_manager.certificate_manager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.certificate_manager.certificate_manager.entities.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

	public Optional<Certificate> findBySerialNumber(String serialNumber);
	
	@Query(value = "select * from \"certificates\" where \"issuer_serial_number\" = :serialNumber", nativeQuery=true)
	public List<Certificate> getAllCertificatesWithCurrentCertificateAsIssuer(String serialNumber);

	@Query("select c from Certificate c where c.issuedTo.id = ?1")
	public List<Certificate> findAllForUser(long userId);
	
}
