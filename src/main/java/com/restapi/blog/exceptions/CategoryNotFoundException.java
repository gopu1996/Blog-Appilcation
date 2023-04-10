package com.restapi.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	long fieldValue;

	public CategoryNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s %s ", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
}
