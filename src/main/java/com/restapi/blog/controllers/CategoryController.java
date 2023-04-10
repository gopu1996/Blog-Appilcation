package com.restapi.blog.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.blog.dto.CategoryDto;
import com.restapi.blog.payload.ResponsePayload;
import com.restapi.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@PostMapping("/")
	public ResponseEntity<ResponsePayload> addCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto category = categoryService.createCategory(categoryDto);
		return new ResponseEntity<ResponsePayload>(new 
				  ResponsePayload("Category added sucessfully","Added.Sucess",true,category),HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<ResponsePayload> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto category = categoryService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<ResponsePayload>(new 
				  ResponsePayload("Category added sucessfully","Updated.Sucess",true,category),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<ResponsePayload> getCategorybyId(@PathVariable Integer categoryId){
	   CategoryDto category = categoryService.getCategory(categoryId);
	   return new ResponseEntity<ResponsePayload>(new 
	            ResponsePayload("Retrive Category sucessfully","Retrive.Sucess",true,category),HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllCategories")
	public ResponseEntity<ResponsePayload> getAllCategories(){
	   List<CategoryDto> category = categoryService.getAllCategory();
	   return new ResponseEntity<ResponsePayload>(new 
	            ResponsePayload("Retrive All Category sucessfully","Retrive.Sucess",true,category),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ResponsePayload> deleteUserById(@PathVariable Integer categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new 
				ResponsePayload("Category Deleted Sucessfully","Deleted.sucess",true, null),HttpStatus.OK);
	}

}
