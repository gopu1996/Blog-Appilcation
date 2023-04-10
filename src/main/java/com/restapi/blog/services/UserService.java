
package com.restapi.blog.services;

import java.util.List;

import com.restapi.blog.dto.UserDto;
import com.restapi.blog.exceptions.UserAlreadyExistEception;

public interface UserService {
 
	 UserDto registerUser(UserDto user) throws UserAlreadyExistEception;
	 UserDto createUser(UserDto user);
	 UserDto updateUser(UserDto user,Integer userId);
	 UserDto getUserById(Integer userId);
	 List<UserDto> getAllUser(Integer page, Integer pageSize, String sortBy);
	 void deleteUserById(Integer userId);
}
