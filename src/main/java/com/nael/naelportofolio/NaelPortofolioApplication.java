package com.nael.naelportofolio;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nael.naelportofolio.model.Role;
import com.nael.naelportofolio.repository.RoleRepository;

@SpringBootApplication
public class NaelPortofolioApplication {
	
	@Autowired
	RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(NaelPortofolioApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			String[] roleNames = {"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"};
			
			List<Role> roles = new LinkedList<>();
			
			for(String name: roleNames) {
				roles.add(Role.builder().name(name).build());
			}
			roleRepository.saveAll(roles);
		};
	}
}
