package com.certificate_manager.certificate_manager.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class WithdrawReasonDTO {
	@Valid
	@NotEmpty(message="is required")
	private String reason;
	
	public WithdrawReasonDTO() {};
	
	public WithdrawReasonDTO(String reason) {
		this.reason = reason;
	}
	
}
