package com.masai.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null) {
			SecretKey key = Keys.hmacShaKeyFor(SecurityContent.JWT_KEY.getBytes());
			
			String jwtToken = Jwts.builder()
					.setIssuer("Aditya")
					.setSubject("jwtToken")
					.claim("username", authentication.getName())
					.claim("authorities", getAutho(authentication.getAuthorities()))
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime()+15000000))
					.signWith(key).compact();
			response.setHeader(SecurityContent.JWT_HEADER, jwtToken);
		}
		filterChain.doFilter(request, response);
	}

	private String getAutho(Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated method stub
		List<String> auths = new ArrayList<String>();
		for(GrantedAuthority ga: authorities) {
			auths.add(ga.getAuthority());
		}
		return String.join(",",auths);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	
        return !request.getServletPath().equals("/logIn");	
	}
	

}
