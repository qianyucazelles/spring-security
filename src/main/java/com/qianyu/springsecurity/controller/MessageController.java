package com.qianyu.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianyu.springsecurity.entity.UserEntity;
import com.qianyu.springsecurity.service.UserService;


@RestController
@RequestMapping("")
public class MessageController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}
	
	@PostMapping("/register")
	public String register(@RequestBody UserEntity user) {
		user.setRoles("ROLE_USER");
		return userService.addUser(user);
	}
	
	@PostMapping("/registeradmin")
	public String registerAdmin(@RequestBody UserEntity user) {
		user.setRoles("ROLE_ADMIN");
		return userService.addUser(user);
	}
	
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String welcomeU() {
		return "Welcome User";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String welcomeA() {
		return "Welcome Admin";
	}
	

}
