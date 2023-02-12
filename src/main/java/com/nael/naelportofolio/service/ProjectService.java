package com.nael.naelportofolio.service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
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
import com.nael.naelportofolio.util.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepo;
	
	private final ProjectLinkRepository linkRepo;
	
	private final ProjectPictureRepository pictureRepo;
		
	private final TechToolService techToolService;
	
	private static LocalDate convertEpochToLocalDate(Long epoch) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
	}
	
	public Project newProject(CreateProjectRequest request) {
		Project project = new Project();
		project.setDescription(request.getDescription());
		project.setStartDate(convertEpochToLocalDate(request.getStartDate()));
		project.setEndDate(convertEpochToLocalDate(request.getEndDate()));
		project.setTechTool(techToolService.createTechTools(request.getTechTool()));

		List<ProjectLink> links = request.getLinks().stream().map(link -> {
			ProjectLink projectLink = new ProjectLink();
			projectLink.setLink(link);
			projectLink.setProject(project);
			return projectLink;
		}).collect(Collectors.toList());
		
		project.setLinks(links);
		return projectRepo.save(project);
	}
	
	public Optional<Project> getProject(String secureId) {
		return projectRepo.findBySecureId(secureId);
	}
	
	// TODO: Lanjut bikin custom validation untuk MultipartFile
	public boolean addPicture(AddProjectPictureRequest request, String secureId) throws IOException {
		Optional<Project> projectOpt = projectRepo.findBySecureId(secureId);
		if(projectOpt.isEmpty()) {
			return false;
		}
		
		Project project = projectOpt.get();
		
		ProjectPicture picture = new ProjectPicture();
		picture.setPath(FileUtil.uploadImage(request.getPicture()));
		picture.setIsCover(false);
		picture.setProject(project);
		picture.setName(request.getName());
		picture.setDescription(request.getDescription());
		
		List<ProjectPicture> projectPicture = project.getPictures();
		
		projectPicture.add(picture);
		
		project.setPictures(projectPicture);
		
		projectRepo.save(project);
		return true;
	}
	
	public boolean removePicture(String secureId) {
		Optional<ProjectPicture> picture = pictureRepo.findBySecureId(secureId);
		if(picture.isEmpty())return false;
		pictureRepo.delete(picture.get());
		return true;
	}
	
}
