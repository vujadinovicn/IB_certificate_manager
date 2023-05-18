package com.certificate_manager.certificate_manager.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;

public class JWTTokenServiceImpl implements IJWTTokenService {
	
	@Autowired
	private JWTTokenRepository tokenRepo;
	
	@Override
	public void createToken(String token) {
		JWTToken jwt = new JWTToken(token);
		this.tokenRepo.save(jwt);
		this.tokenRepo.flush();
	}
	
	@Override
	public JWTToken findByToken(String token) {
		return this.tokenRepo.findByToken(token).orElse(null);
	}
	
	@Override
	public void invalidateToken(String token) {
		JWTToken jwt = this.tokenRepo.findByToken(token).orElse(null);
		
		if (jwt != null) {
			jwt.setValid(false);
			this.tokenRepo.save(jwt);
			this.tokenRepo.flush();
		}
	}
	
	@Override
	public boolean isValid(String token) {
		JWTToken jwt = this.tokenRepo.findByToken(token).orElse(null);
		
		if (jwt != null && jwt.isValid()) {
			return true;
		}
		return false;
	}
}
