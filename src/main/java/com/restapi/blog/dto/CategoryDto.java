package com.restapi.blog.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotEmpty(message = "Category Title Must required")
	@Size(min=10)
	private String categoryTitle;
	
	@Size(min=10,max=100,message ="Discription must max of 100 characters")
	private String categoryDiscription;
}
