package com.qianyu.springsecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qianyu.springsecurity.dto.UserDto;
import com.qianyu.springsecurity.entity.UserEntity;
import com.qianyu.springsecurity.exception.AppException;
import com.qianyu.springsecurity.mapper.UserMapper;
import com.qianyu.springsecurity.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserMapper userMapper;
	
	public UserDto addUser(UserEntity user) {
		
		Optional<UserEntity> oUser = userRepository.findByName(user.getName());
		
		if (oUser.isPresent()) {
			throw new AppException("username is already used.", HttpStatus.BAD_REQUEST);
		}
		
		user.setPassword(
				encoder.encode(user.getPassword())
				);
		
		userRepository.save(user);
		
		return userMapper.toUserDto(user);
		
	}

}
