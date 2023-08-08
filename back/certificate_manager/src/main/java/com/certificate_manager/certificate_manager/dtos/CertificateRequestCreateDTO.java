package com.certificate_manager.certificate_manager.dtos;

import java.time.LocalDateTime;

import com.certificate_manager.certificate_manager.entities.CertificateRequest;
import com.certificate_manager.certificate_manager.enums.CertificateType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CertificateRequestCreateDTO {
	
	@Valid
	@NotNull(message="is required")
	private LocalDateTime validTo;
	
	@Valid
	@NotNull(message="is required")
	@Pattern(regexp = "^[0-9A-Za-z]+$")
	private String issuerSerialNumber;
	
	@Valid
	@NotNull(message="is required")
	private CertificateType type;
	
	public CertificateRequestCreateDTO() {}

	public CertificateRequestCreateDTO(@Valid @NotNull(message = "is required") LocalDateTime validTo,
			@Valid @NotNull(message = "is required") String issuerSerialNumber,
			@Valid @NotNull(message = "is required") CertificateType type) {
		super();
		this.validTo = validTo;
		this.issuerSerialNumber = issuerSerialNumber;
		this.type = type;
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
	
	
}
