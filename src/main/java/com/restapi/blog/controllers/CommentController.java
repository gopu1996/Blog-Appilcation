package com.restapi.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.blog.dto.CommentDto;
import com.restapi.blog.payload.ResponsePayload;
import com.restapi.blog.services.CommentService;

@RestController
@RequestMapping("api/comment")
public class CommentController {
	
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/postComment")
	public ResponseEntity<ResponsePayload> addComment(@RequestBody CommentDto commentDto,
			@RequestParam(value = "postId") Integer postId,
			@RequestParam(value = "userId") Integer userId
			){
		CommentDto comment = commentService.postComment(commentDto, postId,userId) ;
		return new ResponseEntity<ResponsePayload>(new 
				  ResponsePayload("Comment added sucessfully","Added.Sucess",true,comment),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ResponsePayload> deleteCommentById(@PathVariable Integer commentId){
		commentService.deleteComment(commentId);
		return new ResponseEntity<ResponsePayload>(new 
				ResponsePayload("Post Deleted Sucessfully","Deleted.Sucess",true,null),HttpStatus.OK);
	}

}
