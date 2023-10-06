package com.qianyu.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianyu.springsecurity.dto.LoginDto;
import com.qianyu.springsecurity.dto.UserDto;
import com.qianyu.springsecurity.entity.UserEntity;
import com.qianyu.springsecurity.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private UserService userService;
	
	@PostMapping("/register")
	public UserDto register(@RequestBody UserEntity user) {
		user.setRoles("ROLE_USER");
		return userService.addUser(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto ){
		
		UserDto userDto = userService.login(loginDto);
		
		return ResponseEntity.ok(userDto);
		
	}
	
	@PostMapping("/registeradmin")
	public UserDto registerAdmin(@RequestBody UserEntity user) {
		user.setRoles("ROLE_ADMIN,ROLE_USER");
		return userService.addUser(user);
	}

}
