package com.energybox.backendcodingchallenge.exception;

public class CustomMissingMandatoryFieldsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomMissingMandatoryFieldsException(String msg) {
		super(msg);
	}
	

}
