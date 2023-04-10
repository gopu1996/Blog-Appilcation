package com.restapi.blog.services;

import com.restapi.blog.dto.CommentDto;

public interface CommentService {
	
	CommentDto postComment(CommentDto commentDto,Integer postId,Integer userId);
	
	void deleteComment(Integer id);

}
