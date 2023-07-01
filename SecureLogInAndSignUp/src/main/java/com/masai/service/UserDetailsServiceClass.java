package com.masai.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.masai.model.User;
import com.masai.repository.UserRepository;
@Service
public class UserDetailsServiceClass implements UserDetailsService  {
	@Autowired
	private UserRepository uRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt = uRepo.findByEmail(username);
		
		if(opt.isPresent()) {
			User user = opt.get();
			return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorityGeneration(user.getRole()));
		}else {
			throw new BadCredentialsException("User Details not found with this username: "+username);
		}
	}
	private Collection<? extends GrantedAuthority> authorityGeneration(String role) {
		List<GrantedAuthority>gAutho = new ArrayList<>();	
		SimpleGrantedAuthority sGrAutho = new SimpleGrantedAuthority(role);
		gAutho.add(sGrAutho);
		return gAutho;
	}

}
