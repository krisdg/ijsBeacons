package com.example.ijsbeacons;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class IJsBeaconsApplication extends Application {

    private String userAndroidId;
    private String MediaTourClosestBeacon = "";
    private String lastBeaconMediaTour = "";

    public String getUserAndroidId() {
        return userAndroidId;
    }

    public void setUserAndroidId(String userAndroidId) {
        this.userAndroidId = userAndroidId;
    }

	public void setMediaTourBeacon(String bcnName) {
		MediaTourClosestBeacon = bcnName;
	}

	public String getMediaTourBeacon() {
		return MediaTourClosestBeacon;
	}

	public String getMediaTourLastBeacon() {
		return lastBeaconMediaTour;
	}

	public void setMediaTourLastBeacon(String lastBeacon) {
		lastBeaconMediaTour = lastBeacon;
	}
}