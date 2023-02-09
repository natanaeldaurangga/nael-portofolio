package com.nael.naelportofolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nael.naelportofolio.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findBySecureId(String secureId);

}
