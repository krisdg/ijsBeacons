package com.example.ijsbeacons;

import java.util.ArrayList;
import java.util.List;

public class BeaconIdentifier {
	String MAC;
	String name;
	List<Double> distanceList = new ArrayList<Double>();
	long lastSeen;
	int location[] = {0, 0, 0};
	
	public BeaconIdentifier() {
		
	}
	
	public BeaconIdentifier(String MAC, String name, int location[]) {
		this.MAC = MAC;
		this.name = name;
		this.location = location;
	}
	
	public double getAvarageDistance() {
		double avarageDistance = 0;
		
		int counter = 0;
		for(counter = 0; counter < distanceList.size(); counter++) {
			avarageDistance += distanceList.get(counter);
		}
		avarageDistance = avarageDistance / counter;
		
		if (counter < 4) {
			avarageDistance = 10;
		}
		
		return avarageDistance;
	}
	
	public void putDistance(double dis) {
		if (distanceList.size() == 5) {
			distanceList.remove(0);
		}
		distanceList.add(dis);
	}
}
