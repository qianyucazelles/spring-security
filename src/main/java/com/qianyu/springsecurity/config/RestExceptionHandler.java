package com.qianyu.springsecurity.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianyu.springsecurity.dto.ErrorDto;
import com.qianyu.springsecurity.exception.AppException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(value = {AppException.class} )
	@ResponseBody
	public ResponseEntity<ErrorDto> handleException(AppException e){
		return ResponseEntity.status(e.getHttpStatus())
				.body(new ErrorDto(e.getMessage()));
		
	}

}
