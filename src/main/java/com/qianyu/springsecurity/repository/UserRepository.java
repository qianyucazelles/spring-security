package com.qianyu.springsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qianyu.springsecurity.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByName(String username);



}
