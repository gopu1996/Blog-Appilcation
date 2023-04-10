package com.restapi.blog.exceptions;

public class ImageNotFoundException extends Exception {
	
	
	private static final long serialVersionUID = 1L;
	String resourceName;

	public ImageNotFoundException(String resourceName) {
		super(String.format("%s",resourceName));
		this.resourceName = resourceName;

	}
}
