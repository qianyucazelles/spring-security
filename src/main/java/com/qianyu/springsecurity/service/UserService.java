package com.qianyu.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qianyu.springsecurity.entity.UserEntity;
import com.qianyu.springsecurity.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public String addUser(UserEntity user) {
		user.setPassword(
				encoder.encode(user.getPassword())
				);
		
		userRepository.save(user);
		
		return "user added";
		
	}

}
