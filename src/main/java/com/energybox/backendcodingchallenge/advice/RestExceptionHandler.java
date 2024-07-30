package com.energybox.backendcodingchallenge.advice;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.energybox.backendcodingchallenge.domain.ErrorResponse;
import com.energybox.backendcodingchallenge.exception.CustomDataNotFoundException;
import com.energybox.backendcodingchallenge.exception.CustomMissingMandatoryFieldsException;
import com.energybox.backendcodingchallenge.exception.DataAlreadyExistsException;
import com.energybox.backendcodingchallenge.utils.ApplicationConstants;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	  
	  @ExceptionHandler(DataAlreadyExistsException.class )
	  protected ResponseEntity<ErrorResponse> handleDuplicateData(DataAlreadyExistsException ex) {
		  
		  ErrorResponse err= new ErrorResponse(ApplicationConstants.HTTP_STATUS_400, ex.getMessage(), new Date());
	      return new ResponseEntity<ErrorResponse>(
	    		  err, HttpStatus.BAD_REQUEST);
	  }
	  
	  
	  @ExceptionHandler(CustomDataNotFoundException.class )
	  protected ResponseEntity<ErrorResponse> handleDataNotFound(CustomDataNotFoundException ex) {
		  
		  ErrorResponse err= new ErrorResponse(ApplicationConstants.HTTP_STATUS_400, ex.getMessage(), new Date());
	      return new ResponseEntity<ErrorResponse>(
	    		  err, HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(CustomMissingMandatoryFieldsException.class )
	  protected ResponseEntity<ErrorResponse> handleMandatoryFields(CustomMissingMandatoryFieldsException ex) {
		  
		  ErrorResponse err= new ErrorResponse(ApplicationConstants.HTTP_STATUS_400, ex.getMessage(), new Date());
	      return new ResponseEntity<ErrorResponse>(
	    		  err, HttpStatus.BAD_REQUEST);
	  }
	  
}