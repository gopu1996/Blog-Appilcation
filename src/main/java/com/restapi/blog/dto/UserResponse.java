package com.restapi.blog.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserResponse {
	
	private int id;
	
	private String name;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String about;
	
	private Set<CommentDto> comments =  new HashSet<>();
	
	private Set<RolesDto> roles =  new HashSet<>();
	

}
