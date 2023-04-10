package com.restapi.blog.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int id;
	
	@NotEmpty(message="Username is required")
	@Size(min=4,message ="Username must be min of 4 characters")
	private String name;
	
	@Email(message = "Email address is not valid ")
	@NotEmpty(message="Email is required")
	private String email;
	

	@Size(min=4,max=15,message ="password must be min of 4 and max of 15 characters")
	@NotEmpty(message="Password is required")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<CommentDto> comments =  new HashSet<>();
	
	private Set<RolesDto> roles =  new HashSet<>();
	

}
