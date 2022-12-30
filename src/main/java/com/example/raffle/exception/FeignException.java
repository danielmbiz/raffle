package com.example.raffle.exception;

public class FeignException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FeignException(String message) {
		super(message);
	}
	
}

