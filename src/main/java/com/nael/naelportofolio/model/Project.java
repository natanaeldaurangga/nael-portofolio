package com.nael.naelportofolio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE projects SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false OR deleted IS NULL")
public class Project extends AbstractBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@Column(name = "title", length = 100)
	private String title;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ProjectLink> links = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ProjectPicture> pictures = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "projects_tech_n_tools", joinColumns = {
			@JoinColumn(name = "project_id")
	}, inverseJoinColumns = {
			@JoinColumn(name = "tech_n_tool_id")
	})
	private List<ProjectTechTool> techTool = new ArrayList<>();
	
}
