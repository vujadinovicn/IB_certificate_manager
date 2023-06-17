package com.certificate_manager.certificate_manager.entities;

import java.time.LocalDateTime;

import com.certificate_manager.certificate_manager.dtos.CertificateRequestCreateDTO;
import com.certificate_manager.certificate_manager.enums.CertificateType;
import com.certificate_manager.certificate_manager.enums.RequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "requests")
public class CertificateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "is required")
	private LocalDateTime date;

	@NotNull(message = "is required")
	private RequestStatus status;

	private String rejectionReason;

	@NotNull(message = "is required")
	private LocalDateTime validTo;

	@NotEmpty(message = "is required")
	@Pattern(regexp = "^[0-9A-Za-z]+$")
	private String issuerSerialNumber;

	@NotNull(message = "is required")
	private CertificateType type;

	@ManyToOne
	private User requester;

	public CertificateRequest() {

	}

	public CertificateRequest(LocalDateTime date, RequestStatus status, LocalDateTime validTo,
			String issuerSerialNumber, CertificateType type, User requester) {
		super();
		this.date = date;
		this.status = status;
		this.validTo = validTo;
		this.issuerSerialNumber = issuerSerialNumber;
		this.type = type;
		this.requester = requester;
		this.rejectionReason = null;
	}

	public CertificateRequest(CertificateRequestCreateDTO dto, User user) {
		this.date = LocalDateTime.now();
		this.status = RequestStatus.PENDING;
		this.validTo = dto.getValidTo();
		this.issuerSerialNumber = dto.getIssuerSerialNumber();
		this.type = dto.getType();
		this.requester = user;
		this.rejectionReason = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public LocalDateTime getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDateTime validTo) {
		this.validTo = validTo;
	}

	public String getIssuerSerialNumber() {
		return issuerSerialNumber;
	}

	public void setIssuerSerialNumber(String issuerSerialNumber) {
		this.issuerSerialNumber = issuerSerialNumber;
	}

	public CertificateType getType() {
		return type;
	}

	public void setType(CertificateType type) {
		this.type = type;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
