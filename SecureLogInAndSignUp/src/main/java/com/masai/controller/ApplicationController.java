package com.masai.controller;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ApplicationException;
import com.masai.model.User;
import com.masai.service.ApplicationServiceInterfaceImplimentation;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ApplicationController {
	@Autowired
	private ApplicationServiceInterfaceImplimentation asi;
	@Autowired
	private PasswordEncoder pass;
	@PostMapping("/signIn")
	public ResponseEntity<User> signUpInUser(@RequestBody @Valid User user) throws ApplicationException {
		log.info("User is getting store in controller...");
		user.setPassword(pass.encode(user.getPassword()));
		User userr =asi.signUpInUser(user);
		return new ResponseEntity<>(userr,HttpStatus.ACCEPTED);
	}
//	@GetMapping("/users")
//	public ResponseEntity<List<User>> getAllUser(Authentication auth) throws Exception {
//		log.info("User auth in controller...");		 
//		List<User> users =asi.getAllUser(auth.getName());
//		return new ResponseEntity<>(users,HttpStatus.ACCEPTED);
//	}
	
	@GetMapping("/logIn")
	public ResponseEntity<User> logInUserDetails(Authentication auth) throws Exception {
		log.info("User auth in controller...");		 
		User user =asi.logInUserDetails(auth.getName());
		return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
	}
	
}
