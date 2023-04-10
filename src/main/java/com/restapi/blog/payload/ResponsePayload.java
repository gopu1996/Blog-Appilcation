package com.restapi.blog.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayload {
	

	private String message;
	private String messageCode;
	private boolean sucess;
	private Object Data;
	
	
	

}
