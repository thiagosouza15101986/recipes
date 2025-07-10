package com.recipes.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2483049931739844660L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}