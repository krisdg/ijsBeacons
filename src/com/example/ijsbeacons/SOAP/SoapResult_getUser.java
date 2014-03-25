package com.example.ijsbeacons.SOAP;

public class SoapResult_getUser extends SoapResult {
	public int userId = 0;
	public String userAndroidId = "";
	public String userName = "";
	public int userFirstActivity = 0;
	public int userLastActivity = 0;

	public SoapResult_getUser() {
	}
	public SoapResult_getUser(int resultCode) {
		super.resultCode = resultCode;
	}
	public SoapResult_getUser(int resultCode, String resultMessage) {
		super(resultCode, resultMessage);
	}
}