package com.restapi.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.blog.dto.CommentDto;
import com.restapi.blog.entity.Comment;
import com.restapi.blog.entity.Post;
import com.restapi.blog.entity.User;
import com.restapi.blog.exceptions.CommentNotFoundException;
import com.restapi.blog.exceptions.PostNotFoundException;
import com.restapi.blog.exceptions.UserNotFoundException;
import com.restapi.blog.repositories.CommentRepo;
import com.restapi.blog.repositories.PostsRepo;
import com.restapi.blog.repositories.UserRepo;
import com.restapi.blog.services.CommentService;


@Service
public class CommentsImpl implements CommentService {

	@Autowired
	private PostsRepo postsRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDto postComment(CommentDto commentDto,Integer postId,Integer userId) {
		  
	 Post post = postsRepo.findById(postId).orElseThrow(()-> new PostNotFoundException("Post ", "Id : ", postId));
	 User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User ", "Id : ", userId));
     Comment comment =  mapper.map(commentDto, Comment.class);
     comment.setPost(post);
     comment.setUsers(user);
     	
	return mapper.map(commentRepo.save(comment), CommentDto.class);
	}

	@Override
	public void deleteComment(Integer id) {
		Comment comment = commentRepo.findById(id).orElseThrow(()-> new CommentNotFoundException("Comment ", "Id : ", id));
		commentRepo.delete(comment);
	}

}
