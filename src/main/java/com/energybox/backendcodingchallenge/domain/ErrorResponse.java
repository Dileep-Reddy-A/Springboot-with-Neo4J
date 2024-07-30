package com.energybox.backendcodingchallenge.domain;

import java.util.Date;



public class ErrorResponse {
	
	private int errorCode;
	private String errorDesc;
	private Date date;
	public ErrorResponse(int errorCode, String errorDesc, Date date) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.date = date;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
