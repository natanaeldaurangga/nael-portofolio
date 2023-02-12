package com.nael.naelportofolio.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.util.Predicates;
import org.springframework.stereotype.Service;

import com.nael.naelportofolio.model.ProjectTechTool;
import com.nael.naelportofolio.repository.ProjectTechToolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TechToolService {

	private final ProjectTechToolRepository repository;

	public ProjectTechTool createTechTool(String techTool) {
		if(repository.existsByName(techTool)) {
			return repository.findByName(techTool).get();
		}
		return repository.save(ProjectTechTool.builder()
				.name(techTool)
				.build());
	}

	public List<ProjectTechTool> createTechTools(Set<String> techTools){
		List<ProjectTechTool> listTechTools = techTools.stream().map(e -> {
			return createTechTool(e);
		}).collect(Collectors.toList());
		return listTechTools;
	}





}
