package com.example.ijsbeacons.SOAP;

public class SoapRequest_getToplist extends SoapRequest {

	public String userAndroidId;
	public int startDate;
	public int endDate;
	
	public SoapRequest_getToplist(String type) {
		
		this.METHOD_NAME = "getTopListBy" + type;
		
		this.endDate = (int)(System.currentTimeMillis() / 1000);
		this.startDate = this.endDate - 60*60*24*31;
	}
}
