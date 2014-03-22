package com.example.ijsbeacons;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.utils.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackgroundService extends Service {
	
	BeaconManager beaconManager;
	
	static boolean isRunning = false;

	public BackgroundService() {

	}
	
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
		beaconManager = new BeaconManager(this);
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
	      @Override
	      public void onBeaconsDiscovered(Region region, final List<Beacon> rangedBeacons) {
	        // Note that results are not delivered on UI thread.
	            for (Beacon rangedBeacon : rangedBeacons) {
	              System.out.println(String.format("BEACON MAC: %s (%.2fm)", rangedBeacon.getMacAddress(), Utils.computeAccuracy(rangedBeacon)));
	            }
	            
	          }
	    });
    }

    @Override
    public void onStart(Intent intent, int startId) {
    	isRunning = true;
    	
    	System.out.println("SERVICE STARTED");
    	Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
    	
    	beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	        @Override
	        public void onServiceReady() {
	          try {
	            beaconManager.startRanging(new Region("rid", null, null, null));
	          } catch (RemoteException e) {

	          }
	        }
	      });
    }

    @Override
    public void onDestroy() {
    	isRunning = false;
    }

}
