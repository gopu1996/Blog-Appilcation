package com.restapi.blog.exceptions;

public class NoSuchRecordFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;

	public NoSuchRecordFoundException(String resourceName) {
		super(String.format("%s",resourceName));
		this.resourceName = resourceName;

	}
}
