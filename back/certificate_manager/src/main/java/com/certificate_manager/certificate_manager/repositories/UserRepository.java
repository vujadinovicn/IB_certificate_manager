package com.certificate_manager.certificate_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certificate_manager.certificate_manager.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
