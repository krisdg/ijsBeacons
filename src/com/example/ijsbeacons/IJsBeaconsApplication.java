package com.example.ijsbeacons;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class IJsBeaconsApplication extends Application {

    private String userAndroidId;

    public String getUserAndroidId() {
        return userAndroidId;
    }

    public void setUserAndroidId(String userAndroidId) {
        this.userAndroidId = userAndroidId;
    }
}