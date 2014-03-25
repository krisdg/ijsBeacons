package com.example.ijsbeacons;

public class DistanceRule {
	public BeaconIdentifier beacon1;
	public BeaconIdentifier beacon2;
	public int distance;
	
	public DistanceRule(BeaconIdentifier beacon1, BeaconIdentifier beacon2, int distance) {
		this.beacon1 = beacon1;
		this.beacon2 = beacon2;
		this.distance = distance;
	}
}
