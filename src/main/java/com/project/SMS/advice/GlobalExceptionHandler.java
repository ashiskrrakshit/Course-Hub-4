package com.project.SMS.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.SMS.exception.InvalidDataException;
import com.project.SMS.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> resourseNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getErrorMessage();
		return new ResponseEntity<String>(message,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<String> invalidDataExceptionHandler(InvalidDataException ex){
		String message = ex.getErrorMessage();
		return new ResponseEntity<String>(message,HttpStatus.NOT_FOUND);
	}
}
