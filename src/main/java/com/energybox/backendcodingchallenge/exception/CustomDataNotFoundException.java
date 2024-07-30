package com.energybox.backendcodingchallenge.exception;

public class CustomDataNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomDataNotFoundException(Integer id) {
		super("Data with id:"+id+" not found in DB");
	}
	

}
