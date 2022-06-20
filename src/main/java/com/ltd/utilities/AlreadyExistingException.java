package com.ltd.utilities;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AlreadyExistingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadyExistingException() {
	}

	public AlreadyExistingException(String message) {
		super(message);
	}

	public AlreadyExistingException(Throwable cause) {
		super(cause);
	}

	public AlreadyExistingException(String message, Throwable cause) {
		super(message, cause);
	}
}
