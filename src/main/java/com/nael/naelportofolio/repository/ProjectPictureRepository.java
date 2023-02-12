package com.nael.naelportofolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nael.naelportofolio.model.ProjectPicture;

public interface ProjectPictureRepository extends JpaRepository<ProjectPicture, Long> {

	Optional<ProjectPicture> findBySecureId(String secureId);
	
}
