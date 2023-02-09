package com.nael.naelportofolio.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProjectRequest {

	private String title;
	
	private String descritpion;
	
	private Long startDate;
	
	private Long endDate;
	
	private Set<String> links = new HashSet<>();

	private Set<String> techTool = new HashSet<>();
	
}
