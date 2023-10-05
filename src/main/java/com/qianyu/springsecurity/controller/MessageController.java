package com.qianyu.springsecurity.controller;

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
	
	
	@GetMapping("/welcomes")
	public String welcomeS() {
		return "Welcome Secured";
	}
	

}
