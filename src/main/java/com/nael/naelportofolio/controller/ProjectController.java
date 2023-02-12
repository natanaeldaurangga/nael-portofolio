package com.nael.naelportofolio.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nael.naelportofolio.dto.AddProjectPictureRequest;
import com.nael.naelportofolio.dto.CreateProjectRequest;
import com.nael.naelportofolio.dto.ErrorResponse;
import com.nael.naelportofolio.model.Project;
import com.nael.naelportofolio.service.ProjectService;
import com.nael.naelportofolio.util.FileUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody CreateProjectRequest request, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(ErrorResponse.builder()
					.errors(ErrorResponse.extractErrorMsg(bindingResult.getFieldErrors()))
					.build());
		}
		Project project = projectService.newProject(request);
		return ResponseEntity.ok(project);
	}

	@GetMapping("/{secure_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findProject(@PathVariable("secure_id") String secureId){
		var project = projectService.getProject(secureId);
		if(!project.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(project.get());
	}

	@PutMapping("/addPicture/{project_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> uploadImage(@PathVariable("project_id") String secureId, @Valid @ModelAttribute AddProjectPictureRequest request, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest()
					.body(
							ErrorResponse.builder().errors(
									ErrorResponse.extractErrorMsg(bindingResult.getFieldErrors())
									).build()
							);
		}
		projectService.addPicture(request, secureId);
		return ResponseEntity.ok("Data berhasil disimpan");
	}

}
