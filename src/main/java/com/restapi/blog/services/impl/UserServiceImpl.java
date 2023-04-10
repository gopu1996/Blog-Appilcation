package com.restapi.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restapi.blog.dto.UserDto;
import com.restapi.blog.entity.Roles;
import com.restapi.blog.entity.User;
import com.restapi.blog.exceptions.NoSuchRecordFoundException;
import com.restapi.blog.exceptions.UserAlreadyExistEception;
import com.restapi.blog.exceptions.UserNotFoundException;
import com.restapi.blog.repositories.RoleRepo;
import com.restapi.blog.repositories.UserRepo;
import com.restapi.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User savedUser = userRepo.save(user);	
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	 
		User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User ", "Id : ", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = userRepo.save(user);
		
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User ", "Id : ", userId));		
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<User>  users = userRepo.findAll(pageable);
		List<User> allUser = users.getContent();
		List<UserDto> userDto = allUser.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		
		if(userDto.isEmpty()) throw new NoSuchRecordFoundException("No Record Found");
		else return userDto;
	}

	@Override
	public void deleteUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User ", "Id : ", userId));
		userRepo.delete(user);	
	}
	
	private User dtoToUser(UserDto userdto) {
		User user = modelMapper.map(userdto, User.class);
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto =  modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userdto) throws UserAlreadyExistEception {
		

		User exist = userRepo.findByEmail(userdto.getEmail()).isPresent() ? userRepo.findByEmail(userdto.getEmail()).get(): null;	
		if(exist != null) throw  new UserAlreadyExistEception("Record Already exist");	
		User user = modelMapper.map(userdto, User.class);
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		Roles role =   repo.findById(102).get();
		user.getRoles().add(role);
		User newUser  = userRepo.save(user);
		
		return modelMapper.map(newUser, UserDto.class);
	}



}
