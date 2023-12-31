package com.qianyu.springsecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.qianyu.springsecurity.config.CustomUserDetails;
import com.qianyu.springsecurity.entity.UserEntity;
import com.qianyu.springsecurity.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserEntity> user = userRepository.findByName(username);
		
		return user.map(CustomUserDetails::new)
				.orElseThrow(()->new UsernameNotFoundException("User not found"));
	}

}
