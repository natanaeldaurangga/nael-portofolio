package com.nael.naelportofolio.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nael.naelportofolio.dto.AddProjectPictureRequest;
import com.nael.naelportofolio.dto.CreateProjectRequest;
import com.nael.naelportofolio.model.Project;
import com.nael.naelportofolio.model.ProjectLink;
import com.nael.naelportofolio.model.ProjectPicture;
import com.nael.naelportofolio.repository.ProjectLinkRepository;
import com.nael.naelportofolio.repository.ProjectPictureRepository;
import com.nael.naelportofolio.repository.ProjectRepository;
import com.nael.naelportofolio.repository.ProjectTechToolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepo;
	
	private final ProjectLinkRepository linkRepo;
	
	private final ProjectPictureRepository pictureRepo;
	
	private final ProjectTechToolRepository techToolRepo;
	
	private static LocalDate convertEpochToLocalDate(Long epoch) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
	}
	
	public Project newProject(CreateProjectRequest request) {
		Set<ProjectLink> links = request.getLinks().stream().map(link -> {
			return new ProjectLink().builder()
					.link(link)
					.build();
		}).collect(Collectors.toSet());
		
		Project project = Project.builder()
				.description(request.getDescritpion())
				.startDate(convertEpochToLocalDate(request.getStartDate()))
				.endDate(convertEpochToLocalDate(request.getEndDate()))
				.links(links)
				.techTool(techToolRepo.findByNameIn(request.getTechTool()))
				.build();
		
		projectRepo.save(project);
		return project;
	}
	// TODO: Lanjut bikin custom validation untuk MultipartFile
	public boolean addPicture(Set<AddProjectPictureRequest> requests, String secureId) {
		Optional<Project> projectOpt = projectRepo.findBySecureId(secureId);
		if(projectOpt.isEmpty()) {
			return false;
		}
		Project project = projectOpt.get();
//		Set<ProjectPicture> projectPictures = requests.;
//		project.setPictures();
		return true;
	}
	
}
