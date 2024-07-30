package com.energybox.backendcodingchallenge.exception;

public class DataAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataAlreadyExistsException(String msg) {
		super(msg);
	}
	

}
