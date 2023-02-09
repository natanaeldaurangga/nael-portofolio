package com.nael.naelportofolio.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@MappedSuperclass
@Table(indexes = {
		@Index(name = "uk_secure_id", columnList = "secure_id")
})
@Where(clause = "deleted=false or deleted is null")
public abstract class AbstractBaseEntity implements Serializable{
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1987506371260880124L;
	
	@Column(name = "secure_id", nullable = false, unique = true)
	private String secureId = UUID.randomUUID().toString();
	
	@Column(name = "deleted", columnDefinition = "boolean default false")
	private Boolean deleted;
	
}
