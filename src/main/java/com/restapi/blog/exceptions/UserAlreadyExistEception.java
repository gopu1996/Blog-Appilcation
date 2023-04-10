package com.restapi.blog.exceptions;

public class UserAlreadyExistEception extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String resourceName;

	public UserAlreadyExistEception(String string) {
		super(String.format("%s",string));
	}

}
