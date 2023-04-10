package com.restapi.blog.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.restapi.blog.dto.PostDto;
import com.restapi.blog.payload.PostPayload;

public interface PostsService {

	 
	 PostDto createPosts(PostDto postDto, Integer userId, Integer CategoryId, MultipartFile file) throws IOException;
	 
	 PostDto updatePosts(PostDto postDto, Integer postId, MultipartFile file) throws IOException;
	 
	 void deletePost(Integer postId);
	 
	 PostPayload getAllPost(Integer pageNumber, Integer pageSize, String sortBy);
	 
	 PostDto getPostById(Integer postId);
	 
	 List<PostDto> getPostByCategories(Integer categoryId);
	 
	 List<PostDto> getPostByUser(Integer userId);
	 
	 List<PostDto> searchPost(String keyword);
	 
}
