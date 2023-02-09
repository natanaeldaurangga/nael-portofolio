package com.nael.naelportofolio.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Long id;
	
	@Column(name = "title", length = 100)
	private String title;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@OneToMany
	@JoinColumn(name = "book_id", nullable = false)
	private Set<ProjectLink> links = new HashSet<>();
	
	@OneToMany
	@JoinColumn(name = "book_id", nullable = true)
	private Set<ProjectPicture> pictures = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "projects_tech_n_tools", joinColumns = {
			@JoinColumn(name = "project_id")
	}, inverseJoinColumns = {
			@JoinColumn(name = "tech_n_tool_id")
	})
	private Set<ProjectTechTool> techTool = new HashSet<>();
	
}
