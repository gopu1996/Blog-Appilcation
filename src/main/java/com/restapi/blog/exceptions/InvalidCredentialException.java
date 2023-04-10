package com.restapi.blog.exceptions;

public class InvalidCredentialException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String resourceName;

	public InvalidCredentialException(String string) {
		super(String.format("%s",string));


	}

}
