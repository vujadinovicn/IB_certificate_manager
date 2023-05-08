package com.certificate_manager.certificate_manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certificate_manager.certificate_manager.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByPhoneNumber(String phoneNumber);
}
