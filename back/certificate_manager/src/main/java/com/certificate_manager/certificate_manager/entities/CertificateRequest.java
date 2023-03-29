package com.certificate_manager.certificate_manager.entities;

import java.time.LocalDateTime;

import com.certificate_manager.certificate_manager.enums.CertificateType;
import com.certificate_manager.certificate_manager.enums.RequestStatus;

public class CertificateRequest {

	private int id;

	private LocalDateTime date;

	private RequestStatus status;

	private LocalDateTime validTo;

	private long issuerSerialNumber;

	private CertificateType type;

	private User requester;

	public CertificateRequest() {

	}

	public CertificateRequest(int id, LocalDateTime date, RequestStatus status, LocalDateTime validTo,
			long issuerSerialNumber, CertificateType type, User requester) {
		super();
		this.id = id;
		this.date = date;
		this.status = status;
		this.validTo = validTo;
		this.issuerSerialNumber = issuerSerialNumber;
		this.type = type;
		this.requester = requester;
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

	public long getIssuerSerialNumber() {
		return issuerSerialNumber;
	}

	public void setIssuerSerialNumber(long issuerSerialNumber) {
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

}
