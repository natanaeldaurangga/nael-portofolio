package com.nael.naelportofolio.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nael.naelportofolio.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Set<Role> findByNameIn(Set<String> roles);
	
}
