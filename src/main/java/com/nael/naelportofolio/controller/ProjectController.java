package com.nael.naelportofolio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nael.naelportofolio.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

}
