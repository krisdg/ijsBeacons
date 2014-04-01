package com.example.ijsbeacons.SOAP;

public class SoapRequest_getPersonalStatistics extends SoapRequest {

	public String userAndroidId;
	
	public SoapRequest_getPersonalStatistics() {
		super.METHOD_NAME = "getPersonalStatistics";
	}
	public SoapRequest_getPersonalStatistics(String userAndroidId) {
		this();
		
		this.userAndroidId = userAndroidId;
	}
}
