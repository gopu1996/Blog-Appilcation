package com.restapi.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.restapi.blog.dto.UserDto;
import com.restapi.blog.payload.ResponsePayload;
import com.restapi.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<ResponsePayload> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUser = userService.createUser(userDto);
		
		return new ResponseEntity<>(new 
				ResponsePayload("User Created Sucessfully","creted.sucess",true,createdUser),HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<ResponsePayload> updateUser(@Valid @RequestBody UserDto userDto ,@PathVariable Integer userId){
		UserDto updatedUser = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(new 
				ResponsePayload("User Updated Sucessfully","Updated.sucess",true,updatedUser),HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ResponsePayload> getUserById(@PathVariable Integer userId){
		UserDto user = userService.getUserById(userId);
		return new ResponseEntity<>(new 
				ResponsePayload("User Retrive Sucessfully","Retrive.sucess",true,user),HttpStatus.OK);
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<ResponsePayload> getAllUsers(	
			@RequestParam(value = "page",defaultValue = "0",required =  false) Integer page, 
			@RequestParam(value = "pageSize",defaultValue = "5",required =  false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "id",required =  false) String  sortBy){
		List<UserDto> user = userService.getAllUser(page,pageSize,sortBy);
		return new ResponseEntity<>(new 
				ResponsePayload("Users Retrive Sucessfully","Retrive.sucess",true,user),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ResponsePayload> deleteUserById(@PathVariable Integer userId){
		userService.deleteUserById(userId);
		return new ResponseEntity<>(new 
				ResponsePayload("Users Deleted Sucessfully","Deleted.sucess",true, null),HttpStatus.OK);
	}
}
