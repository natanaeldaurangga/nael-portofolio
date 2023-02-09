package com.nael.naelportofolio.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
	
	private String fullName;

	private String username;
	
	private String password;

	private Set<String> roles = new HashSet<>();
	
}
