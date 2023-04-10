package com.restapi.blog.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restapi.blog.dto.PostDto;
import com.restapi.blog.entity.Category;
import com.restapi.blog.entity.Post;
import com.restapi.blog.entity.User;
import com.restapi.blog.exceptions.CategoryNotFoundException;
import com.restapi.blog.exceptions.NoSuchRecordFoundException;
import com.restapi.blog.exceptions.PostNotFoundException;
import com.restapi.blog.exceptions.UserNotFoundException;
import com.restapi.blog.payload.PostPayload;
import com.restapi.blog.repositories.CategoryRepo;
import com.restapi.blog.repositories.PostsRepo;
import com.restapi.blog.repositories.UserRepo;
import com.restapi.blog.services.FileService;
import com.restapi.blog.services.PostsService;


@Service

public class PostsServiceImpl implements PostsService{

	@Autowired
	private PostsRepo postsRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@Override
	public PostDto createPosts(PostDto postDto, Integer userId, Integer categoryId, MultipartFile file) throws IOException {
		User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User ", "Id : ", userId));
		Category  category = categoryRepo.findById(categoryId).orElseThrow(() -> new 
		         CategoryNotFoundException("Category ", "Id : ", categoryId));
		
		Post post = modelMapper.map(postDto, Post.class);
		String fileName  =	fileService.uploadImage(path, file);
		String uri =  ServletUriComponentsBuilder.fromCurrentContextPath()
	        		.path("api/post/image/"+fileName).toUriString();
	    post.setImageName(uri);
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		
		
		return modelMapper.map(postsRepo.save(post), PostDto.class);
	}


	@Override
	public PostDto updatePosts(PostDto postDto, Integer postId , MultipartFile file) throws IOException {
	
		Post post = postsRepo.findById(postId).orElseThrow(()-> new PostNotFoundException("Post ", "Id : ", postId));
		 String fileName  =	fileService.uploadImage(path, file);
		 String uri =  ServletUriComponentsBuilder.fromCurrentContextPath()
	        		.path("api/post/image/"+fileName).toUriString();
		post.setImageName(uri);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
    
		return modelMapper.map(postsRepo.save(post), PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postsRepo.findById(postId).orElseThrow(()-> new PostNotFoundException("Post ", "Id : ", postId));
		postsRepo.delete(post);
	}

	@Override
	public PostPayload getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
		
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Post>  page = postsRepo.findAll(pageable);
		List<Post> allPost = page.getContent();
		
		List<PostDto> listOfPost = allPost.stream().map(post->
        modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostPayload payload = new PostPayload();
		payload.setPosts(listOfPost);
		payload.setPageNumber(page.getNumber());
		payload.setPageSize(page.getSize());
		payload.setTotalElement(page.getTotalElements());
		payload.setTotalPages(page.getTotalPages());
		payload.setLastPage(page.isLast());
		
		
		if(listOfPost.isEmpty()) {
			throw new NoSuchRecordFoundException("No Record Found");
		}else {
			return payload;
		}
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = postsRepo.findById(postId).orElseThrow(()-> new PostNotFoundException("Post ", "Id : ", postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategories(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new 
		         CategoryNotFoundException("Category ", "Id : ", categoryId));
		List<PostDto> getPostByCategory =  postsRepo.findAllByCategory(category).stream()
				.map(cat ->modelMapper.map(cat, PostDto.class)).collect(Collectors.toList());
		if(getPostByCategory.isEmpty()) throw new NoSuchRecordFoundException("No Record Found");
		else return getPostByCategory;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new 
		         UserNotFoundException("User ", "Id : ", userId));
		List<PostDto> getPostByUser =  postsRepo.findAllByUser(user).stream()
				.map(cat ->modelMapper.map(cat, PostDto.class)).collect(Collectors.toList());
		
		if(getPostByUser.isEmpty()) throw new NoSuchRecordFoundException("No Record Found");
		else return getPostByUser;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
	   List<Post> searchedPost =	postsRepo.findByTitle("%"+keyword+"%");
	   List<PostDto>  searchedPostDto =  searchedPost.stream().map(post-> 
	                          modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	   
	   if(searchedPostDto.isEmpty()) throw new NoSuchRecordFoundException("No Record Found");
	   else return searchedPostDto;
	 
	}


}
