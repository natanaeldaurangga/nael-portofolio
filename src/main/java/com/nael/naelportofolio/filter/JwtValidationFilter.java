package com.nael.naelportofolio.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nael.naelportofolio.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
			)
					throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		String jwt = null;
		String username = null;

		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			jwt = authHeader.substring(7);
			username = jwtService.extractUsername(jwt);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, 
					jwtService.extractAuthorities(jwt)
					);
			authToken.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request)
					);
			SecurityContextHolder.getContext().setAuthentication(authToken);

		} catch (ExpiredJwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// TODO Auto-generated method stub
		return request.getServletPath().equals("api/v1/auth/**");
	}



}
