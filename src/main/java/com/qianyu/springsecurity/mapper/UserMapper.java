package com.qianyu.springsecurity.mapper;

import org.mapstruct.Mapper;

import com.qianyu.springsecurity.dto.UserDto;
import com.qianyu.springsecurity.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDto toUserDto(UserEntity user);

}
