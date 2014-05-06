package com.example.ijsbeacons.SOAP;

public class SoapRequest_updateStatistics extends SoapRequest {
	
	public String userAndroidId;
	public int coffeeMachineCount;
	public int walkedDistance;
	public int seenSurface;
	public int walkingSpeed;
	public int lunchRoomCount;
	
	public SoapRequest_updateStatistics() {
		super.METHOD_NAME = "updateStatistics";
	}
	public SoapRequest_updateStatistics(String userAndroidId, int coffeeMachineCount, int walkedDistance, int seenSurface, int walkingSpeed, int lunchRoomCount) {
		this();
		
		this.userAndroidId = userAndroidId;
		this.coffeeMachineCount = coffeeMachineCount;
		this.walkedDistance = walkedDistance;
		this.seenSurface = seenSurface;
		this.walkingSpeed = walkingSpeed;
		this.lunchRoomCount = lunchRoomCount;
	}
}
