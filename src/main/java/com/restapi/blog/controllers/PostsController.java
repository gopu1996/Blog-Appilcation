package com.restapi.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.blog.dto.PostDto;
import com.restapi.blog.exceptions.ImageNotFoundException;
import com.restapi.blog.payload.PostPayload;
import com.restapi.blog.payload.ResponsePayload;
import com.restapi.blog.services.FileService;
import com.restapi.blog.services.PostsService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/post")
public class PostsController {
	
	@Autowired
	private PostsService postsService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${project.image}")
	private String path;
	


	@PostMapping("/createPost")
	public ResponseEntity<ResponsePayload> addPosts(@Valid
			@RequestParam(value = "userId") Integer userId,
			@RequestParam(value = "categoryId") Integer categoryId,
			@RequestParam("image") MultipartFile file,
			@RequestParam("postData") String postData
			) throws IOException, ImageNotFoundException{
		PostDto postDto = objectMapper.readValue(postData, PostDto.class);
		PostDto post = null;
		if(file.isEmpty()) throw new ImageNotFoundException("Image Not Found Exception");
		 else {
			post =  postsService.createPosts(postDto, userId, categoryId,file);	
		}
		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Post added sucessfully","Added.Sucess",true,post),HttpStatus.CREATED);
	
	}
	
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<ResponsePayload> updatePosts(@Valid 
			@PathVariable Integer postId,
			@RequestParam("image") MultipartFile file,
			@RequestParam("postData") String postData
			) throws IOException{
		PostDto postDto = objectMapper.readValue(postData, PostDto.class);
		PostDto post =  postsService.updatePosts(postDto, postId,file);

		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Post Updated sucessfully","Updated.Sucess",true,post),HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllPostsByUser/{userId}")
	public ResponseEntity<ResponsePayload> getAllPostsByUser(@PathVariable Integer userId){
		List<PostDto> post =  postsService.getPostByUser(userId);
		return new ResponseEntity<ResponsePayload>(new 
					ResponsePayload("Post Retrive Sucessfully","Fetch.Sucess",true,post),HttpStatus.OK);		
	}
	
	@GetMapping("/getAllPostsByCategory/{categoryId}")
	public ResponseEntity<ResponsePayload> getAllPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> post =  postsService.getPostByCategories(categoryId);
		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Post Retrive Sucessfully","Fetch.Sucess",true,post),HttpStatus.OK);
	}
	
	@GetMapping("/getAllPosts")
	public ResponseEntity<ResponsePayload> getAllPosts(
			@RequestParam(value = "page",defaultValue = "0",required =  false) Integer page, 
			@RequestParam(value = "pageSize",defaultValue = "5",required =  false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required =  false) String  sortBy){
		
	        PostPayload post =  postsService.getAllPost(page,pageSize,sortBy);
		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Posts Retrive Sucessfully","Fetch.Sucess",true,post),HttpStatus.OK);
	}
	
	
	@GetMapping("/{postId}")
	public ResponseEntity<ResponsePayload> getAllPostById(@PathVariable Integer postId){
		PostDto post =  postsService.getPostById(postId);
		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Post Retrive Sucessfully","Fetch.Sucess",true,post),HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ResponsePayload> deletePostById(@PathVariable Integer postId){
		postsService.deletePost(postId);
		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Post Deleted Sucessfully","Deleted.Sucess",true,null),HttpStatus.OK);
	}
	
	@GetMapping("postSearch/{search}")
	public ResponseEntity<ResponsePayload> searchTitle(@PathVariable String search){
		List<PostDto> post =  postsService.searchPost(search);
		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Post Retrive Sucessfully","Fetch.Sucess",true,post),HttpStatus.OK);
	}
	
	
//	@PostMapping("/upload/image/{postId}")
//	public ResponseEntity<ResponsePayload> uploadImage(
//			@RequestParam("image") MultipartFile file,
//			@PathVariable Integer postId
//			) throws IOException{
//		    PostDto postDto =  postsService.getPostById(postId);
//	        String fileName  =	fileService.uploadImage(path, file);
//	     
//	        PostDto pDto =  postsService.updatePosts(postDto, postId);
//	      
//	        return new ResponseEntity<ResponsePayload>(new 
//					ResponsePayload("Image Upload sucessfully","Upload.Sucess",true,pDto),HttpStatus.OK);
//	}
//	
	@GetMapping(value = "image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
}
