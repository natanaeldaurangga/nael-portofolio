package com.nael.naelportofolio.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nael.naelportofolio.model.ProjectTechTool;

@Repository
public interface ProjectTechToolRepository extends JpaRepository<ProjectTechTool, Long> {

	Optional<ProjectTechTool> findByName(String name);

	List<ProjectTechTool> findByNameIn(Set<String> names);
	
	boolean existsByName(String name);
	
}
