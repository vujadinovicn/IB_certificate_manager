package com.certificate_manager.certificate_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certificate_manager.certificate_manager.entities.UsedPassword;

public interface UsedPasswordRepository extends JpaRepository<UsedPassword, Long>{

}
