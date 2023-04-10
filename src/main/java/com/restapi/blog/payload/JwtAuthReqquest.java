package com.restapi.blog.payload;

import lombok.Data;

@Data
public class JwtAuthReqquest {
	
	private String email;
	private String password;

}
