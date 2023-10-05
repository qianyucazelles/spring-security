package com.qianyu.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianyu.springsecurity.entity.Product;
import com.qianyu.springsecurity.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}
	
	

}
