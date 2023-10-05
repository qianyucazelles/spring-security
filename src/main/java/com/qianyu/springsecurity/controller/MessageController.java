package com.qianyu.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
public class MessageController {
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome";
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
