package com.restapi.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restapi.blog.entity.User;
import com.restapi.blog.exceptions.UserNotFoundException;
import com.restapi.blog.repositories.UserRepo;

@Service
public class CustomUserService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		User user =  userRepo.findByEmail(username).
				orElseThrow(()-> new UserNotFoundException("User ", "Id : ", username));
	return user;
	}

}
