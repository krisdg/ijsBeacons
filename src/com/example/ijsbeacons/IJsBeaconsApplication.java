package com.example.ijsbeacons;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class IJsBeaconsApplication extends Application {

    private String userAndroidId;
    
    public List<BeaconIdentifier> beacons = new ArrayList<BeaconIdentifier>();
    public List<DistanceRule> distanceRules = new ArrayList<DistanceRule>();
	
	public void defineList() {
		beacons.add(new BeaconIdentifier("EA:4B:01:B6:4C:F5", "GROEN", new int[] {1, 0, 0}));
		beacons.add(new BeaconIdentifier("D4:23:26:59:34:AD", "PAARS", new int[] {5, 10, 10}));
		beacons.add(new BeaconIdentifier("D8:38:9B:3F:55:F8", "BLAUW", new int[] {10, 0, 0}));
		
		distanceRules.add(new DistanceRule(getBeaconByName("GROEN"), getBeaconByName("PAARS"), 100));
	}
	
	public BeaconIdentifier getBeaconByName(String name) {
		if (beacons.size() == 0) {
			defineList();
		}
		
		for (BeaconIdentifier beacon : beacons) {
			if (beacon.name.equals(name)) {
				return beacon;
			}
		}
		return null;
	}

	public BeaconIdentifier getBeacon(String MAC) {
		if (beacons.size() == 0) {
			defineList();
		}
		
		for (BeaconIdentifier beacon : beacons) {
			if (beacon.MAC.equals(MAC)) {
				return beacon;
			}
		}
		return null;
		
	}

    public String getUserAndroidId() {
        return userAndroidId;
    }

    public void setUserAndroidId(String userAndroidId) {
        this.userAndroidId = userAndroidId;
    }
}