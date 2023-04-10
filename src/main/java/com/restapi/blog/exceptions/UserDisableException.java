package com.restapi.blog.exceptions;

public class UserDisableException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String resourceName;

	public UserDisableException(String string) {
		super(String.format("%s",string));
	}
}
