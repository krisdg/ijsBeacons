package com.example.ijsbeacons.SOAP;

public class SoapResult {
	public int resultCode = 0;
	public String resultMessage = "";

	public SoapResult() {
	}

	public SoapResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public SoapResult(int resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}
}