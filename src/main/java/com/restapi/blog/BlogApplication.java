package com.restapi.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.restapi.blog.entity.Roles;
import com.restapi.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo repo;
	
	
		
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	
	
	@Bean 
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			Roles role = new Roles();
			role.setId(101);
			role.setName("ADMIN_USER");
			
			Roles role1 = new Roles();
			role1.setId(102);
			role1.setName("NORMAL_USER");	
			List<Roles> roles = List.of(role,role1);
			repo.saveAll(roles);
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

}
