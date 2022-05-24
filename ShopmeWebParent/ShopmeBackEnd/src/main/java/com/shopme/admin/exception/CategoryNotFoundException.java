package com.shopme.admin.exception;

public class CategoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4457734639350591865L;

	public CategoryNotFoundException(String message) {
		super(message);
	}
}
