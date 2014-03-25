package com.example.ijsbeacons.SOAP;

public class SoapRequest_registerUser extends SoapRequest {
	public String userAndroidId;
	public String userName;

	public SoapRequest_registerUser() {
		super.METHOD_NAME = "registerUser";
	}
	public SoapRequest_registerUser(String userAndroidId, String userName) {
		this();
		
		this.userAndroidId = userAndroidId;
		this.userName = userName;
	}
}
