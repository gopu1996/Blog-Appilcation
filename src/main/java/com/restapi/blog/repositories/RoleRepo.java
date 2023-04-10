package com.restapi.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.blog.entity.Roles;

public interface RoleRepo extends JpaRepository<Roles, Integer>{
	
	

}
