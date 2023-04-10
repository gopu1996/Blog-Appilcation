package com.restapi.blog.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	
	   private Integer postId;
		
	   private String title;
	   
	   @Size(min=10,max=1000,message ="Discription must max of 100 characters")
	   private String content;
	   
	   private String imageName;

	   private Date addedDate;

	   private CategoryDto category;
   
	   private UserDto user;
	   
	   private Set<CommentDto> comments =  new HashSet<>();
	   

}
