package com.certificate_manager.certificate_manager.dtos;

import java.time.LocalDateTime;

import com.certificate_manager.certificate_manager.entities.CertificateRequest;
import com.certificate_manager.certificate_manager.enums.CertificateType;
import com.certificate_manager.certificate_manager.enums.RequestStatus;


public class CertificateRequestReturnedDTO {

	private int id;

	private LocalDateTime date;

	private RequestStatus status;

	private LocalDateTime validTo;

	private CertificateDTO issuer;

	private CertificateType type;

	public CertificateRequestReturnedDTO() {
		
	}

	public CertificateRequestReturnedDTO(int id, LocalDateTime date, RequestStatus status, LocalDateTime validTo,
			CertificateDTO issuer, CertificateType type) {
		super();
		this.id = id;
		this.date = date;
		this.status = status;
		this.validTo = validTo;
		this.issuer = issuer;
		this.type = type;
	}
	
	public CertificateRequestReturnedDTO(CertificateRequest request, CertificateDTO issuer) {
		this.id = request.getId();
		this.date = request.getDate();
		this.status = request.getStatus();
		this.validTo = request.getValidTo();
		this.issuer = issuer;
		this.type = request.getType();
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

	public CertificateDTO getIssuer() {
		return issuer;
	}

	public void setIssuer(CertificateDTO issuer) {
		this.issuer = issuer;
	}

	public CertificateType getType() {
		return type;
	}

	public void setType(CertificateType type) {
		this.type = type;
	}
	
	
}
