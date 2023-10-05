package com.qianyu.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianyu.springsecurity.entity.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findByName(String name);
	

}
