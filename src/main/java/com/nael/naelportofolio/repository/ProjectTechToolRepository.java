package com.nael.naelportofolio.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nael.naelportofolio.model.ProjectTechTool;

public interface ProjectTechToolRepository extends JpaRepository<ProjectTechTool, Long> {

	Optional<ProjectTechTool> findByName(String name);

	Set<ProjectTechTool> findByNameIn(Set<String> names);
	
}
