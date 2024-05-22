package com.project.SMS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)

public class ResourceNotFoundException extends RuntimeException {

	private String errorMessage;

	public ResourceNotFoundException(String message) {

		super(message);
		this.errorMessage = message;
	}
}
