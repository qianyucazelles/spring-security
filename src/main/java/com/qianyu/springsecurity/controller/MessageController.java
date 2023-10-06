package com.qianyu.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianyu.springsecurity.dto.LoginDto;
import com.qianyu.springsecurity.dto.UserDto;
import com.qianyu.springsecurity.entity.Product;
import com.qianyu.springsecurity.entity.UserEntity;
import com.qianyu.springsecurity.service.ProductService;
import com.qianyu.springsecurity.service.UserService;


@RestController
@RequestMapping("")
public class MessageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/welcome")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Welcome");
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
	
	@GetMapping("/products")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Product> getProducts(){
		return productService.getAllProducts();
	}
	

}
