package com.example.ijsbeacons;

import android.app.Application;

public class IJsBeaconsApplication extends Application {

    private int lastUpdate;

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}