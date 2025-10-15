package com.example.kalps.services;

import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.kalps.entity.userentity;
import com.example.kalps.repository.userRepository;

@Component

public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private userRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Fetch user from database
		userentity user = userRepo.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		
		
		return new User(user.getUsername(),user.getPassword(),Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
	}
	
	 
 
}
