package com.shopme.admin.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5591243092370173996L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
