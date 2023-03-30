package com.certificate_manager.certificate_manager.entities;

import java.time.LocalDateTime;

import com.certificate_manager.certificate_manager.enums.CertificateType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="certificates")
public class Certificate {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private long serialNumber;

	private LocalDateTime validFrom;

	private LocalDateTime validTo;

	private long issuerSerialNumber;

	private boolean valid;

	private CertificateType type;

	@ManyToOne
	private User issuedTo;

	public Certificate() {

	}

	public Certificate(int id, long serialNumber, LocalDateTime validFrom, LocalDateTime validTo,
			long issuerSerialNumber, boolean valid, CertificateType type, User issuedTo) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.issuerSerialNumber = issuerSerialNumber;
		this.valid = valid;
		this.type = type;
		this.issuedTo = issuedTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public LocalDateTime getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDateTime getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDateTime validTo) {
		this.validTo = validTo;
	}

	public long getIssuerSerialNumber() {
		return issuerSerialNumber;
	}

	public void setIssuerSerialNumber(long issuerSerialNumber) {
		this.issuerSerialNumber = issuerSerialNumber;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public CertificateType getType() {
		return type;
	}

	public void setType(CertificateType type) {
		this.type = type;
	}

	public User getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(User issuedTo) {
		this.issuedTo = issuedTo;
	}

}
