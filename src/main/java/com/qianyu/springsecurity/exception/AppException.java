package com.qianyu.springsecurity.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;
	
	public AppException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		
	}


	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
	

}