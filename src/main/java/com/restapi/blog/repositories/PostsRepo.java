package com.restapi.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restapi.blog.entity.Category;
import com.restapi.blog.entity.Post;
import com.restapi.blog.entity.User;

public interface PostsRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findAllByUser(User user);
	List<Post> findAllByCategory(Category category);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> findByTitle(@Param("key") String tile);

}
