package com.restapi.blog.exceptions;

public class UnauthorizedException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	String resourceName;
	
	
	

	public UnauthorizedException(String resourceName) {
		super(String.format("%s",resourceName));
	}

}
