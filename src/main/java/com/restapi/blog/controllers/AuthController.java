package com.restapi.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.blog.dto.UserDto;
import com.restapi.blog.dto.UserResponse;
import com.restapi.blog.entity.User;
import com.restapi.blog.exceptions.InvalidCredentialException;
import com.restapi.blog.exceptions.UserAlreadyExistEception;
import com.restapi.blog.exceptions.UserDisableException;
import com.restapi.blog.payload.JwtAuthReqquest;
import com.restapi.blog.payload.ResponsePayload;
import com.restapi.blog.security.JwtTokenHelper;
import com.restapi.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/app/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public ResponseEntity<ResponsePayload> login(@RequestBody JwtAuthReqquest jwtAuthReqquest) throws Exception{
		
		authenticate(jwtAuthReqquest.getEmail(),jwtAuthReqquest.getPassword());
		
		 UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthReqquest.getEmail());
	
		 String TOKEN = jwtTokenHelper.generateToken(userDetails);
	
		 HttpHeaders responseHeaders = new HttpHeaders();
		 
		 responseHeaders.set("Token", TOKEN);
		
		return new ResponseEntity<ResponsePayload>( new ResponsePayload("Login sucessfully","Login.Sucess",true,userDetails),responseHeaders, HttpStatus.OK );
		
	}
	

	private void authenticate(String userName, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
		try {
			authenticationManager.authenticate(authenticationToken);
		}catch (DisabledException e) {
			throw new UserDisableException("User is Disable");
		}
		catch (BadCredentialsException e) {
			throw new InvalidCredentialException("Please Check Your UserName and Password");
		}
	}
	
	@PostMapping("/registerUser")
	public ResponseEntity<ResponsePayload> registerNewUser(@Valid @RequestBody UserDto userDto) throws UserAlreadyExistEception{
		UserDto user = userService.registerUser(userDto);
		UserResponse mapUser =	mapper.map(user, UserResponse.class);
		return new ResponseEntity<>(new 
				ResponsePayload("User Created Sucessfully","creted.sucess",true,mapUser),HttpStatus.CREATED);
	}
	
}
