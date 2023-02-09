package com.nael.naelportofolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nael.naelportofolio.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findBySecureId(String secureId);
	
	Optional<User> findByUsername(String username);
	
}
