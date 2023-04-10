package com.restapi.blog.exceptions;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restapi.blog.payload.ResponsePayload;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponsePayload> userNotFoundExceptionHandler(UserNotFoundException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Retrive.failed", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<ResponsePayload> InvalidCredentialException(InvalidCredentialException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Retrive.failed", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(UserDisableException.class)
	public ResponseEntity<ResponsePayload> UserDisableException(UserDisableException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Retrive.failed", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<ResponsePayload> commentNotFoundExceptionHandler(CommentNotFoundException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Retrive.failed", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ResponsePayload> categoryNotFoundExceptionHandler(CategoryNotFoundException ex){
		String message = ex.getMessage();
		ResponsePayload categoryPayload = new ResponsePayload(message, "Retrive.failed", false,null);
		return new ResponseEntity<ResponsePayload>(categoryPayload,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ResponsePayload> postNotFoundExceptionHandler(PostNotFoundException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Retrive.failed", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(NoSuchRecordFoundException.class)
	public ResponseEntity<ResponsePayload> noSuchRecordFoundExceptionHandler(NoSuchRecordFoundException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Not.Found", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(UserAlreadyExistEception.class)
	public ResponseEntity<ResponsePayload> userAlreadyExistEceptionHandler(UserAlreadyExistEception ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Already.Exsit", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ResponsePayload> unauthorizedExceptionHandler(UnauthorizedException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message,"Access.Denined ", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<ResponsePayload> ImageNotFoundExceptionHandler(ImageNotFoundException ex){
		String message = ex.getMessage();
		ResponsePayload payload = new ResponsePayload(message, "Not.Found", false,null);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.NOT_FOUND);	
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponsePayload> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String,String> reps = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(err -> {
			String fieldName = ((FieldError)err).getField();
			String message = err.getDefaultMessage();
			reps.put(fieldName, message);
		});

		ResponsePayload payload = new ResponsePayload((String) reps.values().toArray()[0], "Retrive.failed", false,reps);
		return new ResponseEntity<ResponsePayload>(payload,HttpStatus.BAD_REQUEST);
	}

}
