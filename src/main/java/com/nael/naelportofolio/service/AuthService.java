package com.nael.naelportofolio.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nael.naelportofolio.dto.AuthenticationRequest;
import com.nael.naelportofolio.dto.AuthenticationResponse;
import com.nael.naelportofolio.dto.RegisterRequest;
import com.nael.naelportofolio.model.User;
import com.nael.naelportofolio.repository.RoleRepository;
import com.nael.naelportofolio.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		User user = User.builder()
				.fullName(request.getFullName())
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.roles(roleRepository.findByNameIn(request.getRoles()))
				.build();
		
		userRepository.save(user);
		
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
								request.getUsername(), request.getPassword()
							)
				);
		var user = userRepository.findByUsername(request.getUsername()).get();
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	
	
}
