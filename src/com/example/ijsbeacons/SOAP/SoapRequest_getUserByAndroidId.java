package com.example.ijsbeacons.SOAP;

public class SoapRequest_getUserByAndroidId extends SoapRequest {
	
	public String userAndroidId;
	
	public SoapRequest_getUserByAndroidId() {
		super.METHOD_NAME = "getUserByAndroidId";
	}
	public SoapRequest_getUserByAndroidId(String userAndroidId) {
		this();
		
		this.userAndroidId = userAndroidId;
	}
}
