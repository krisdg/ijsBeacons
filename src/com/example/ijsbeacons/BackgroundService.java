package com.example.ijsbeacons;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.ArrayList;
import java.util.List;

public class BackgroundService extends Service {

	BeaconManager beaconManager;

	List<BeaconIdentifier> foundBeacons = new ArrayList<>();

	static boolean isRunning = false;
	
	//Statistic variables
	int walkedDistance = 0;
	int coffeeMachineCount = 0;
	int seenSurface = 0;
	int walkingSpeed = 0;

	public BackgroundService() {
	}

	private void processBeacon(Beacon rangedBeacon) {
		String MAC = rangedBeacon.getMacAddress();
		double distance = Utils.computeAccuracy(rangedBeacon);
		long timestamp = System.currentTimeMillis();
		
		boolean beaconFound = false;

		for (BeaconIdentifier beaconId : foundBeacons) {
			if (beaconId.MAC.equals(MAC)) {
				beaconId.lastSeen = timestamp;
				beaconId.putDistance(distance);
				
				beaconFound = true;
			}
		}
		
		if (beaconFound == false) {
			System.out.println("[BEACON IDENTIFIERS]ADDED BEACON " + MAC);
			
			BeaconIdentifier newBeacon = ((IJsBeaconsApplication) this.getApplication()).getBeacon(MAC);
			
			if (newBeacon != null) {
				newBeacon.MAC = MAC;
				newBeacon.lastSeen = timestamp;
				newBeacon.putDistance(distance);
	
				foundBeacons.add(newBeacon);
			}
		}
	}
	
	public BeaconIdentifier getClosestBeacon() {
		double closestDistance = 10;
		BeaconIdentifier closestBeacon = null;
		
		for (BeaconIdentifier beaconId : foundBeacons) {
			if (closestBeacon == null) {
				closestBeacon = beaconId;
				closestDistance = beaconId.getAvarageDistance();
			} else {
				if (closestDistance > beaconId.getAvarageDistance()) {
					closestBeacon = beaconId;
					closestDistance = beaconId.getAvarageDistance();
				}
			}
		}
		
		return closestBeacon;
	}
	
	public double calculateDistance(BeaconIdentifier bcn1, BeaconIdentifier bcn2) {
		double distance = -1;
		
		for (DistanceRule distanceRule : ((IJsBeaconsApplication) this.getApplication()).distanceRules) {
			if (distanceRule.beacon1.MAC.equals(bcn1.MAC) && distanceRule.beacon2.MAC.equals(bcn2.MAC)) {
				distance = distanceRule.distance;
			}
			if (distanceRule.beacon1.MAC.equals(bcn2.MAC) && distanceRule.beacon2.MAC.equals(bcn1.MAC)) {
				distance = distanceRule.distance;
			}
		}
		
		if (distance == -1) {
			double deltaX = bcn1.location[0] - bcn2.location[0];
			double deltaY = bcn1.location[1] - bcn2.location[1];
			double deltaZ = bcn1.location[2] - bcn2.location[2];

			distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		}
		
		return distance;
	}

	public class calculateLocation implements Runnable {
		public void run() {
			try {
				while(true) {
					Thread.sleep(3000);
					
					System.out.println("[BEACON IDENTIFIERS]START");
					
					int index = 0;
					
					for (BeaconIdentifier bcn : foundBeacons) {
						if (bcn.lastSeen + 10000 < System.currentTimeMillis()) {
							foundBeacons.remove(index);
						}
						index++;
						System.out.println("[BEACON IDENTIFIERS]BCN:" + bcn.name + " : " + String.format("%1$,.2f", bcn.getAvarageDistance()) + " : " + bcn.lastSeen);
					}
					System.out.println("[BEACON IDENTIFIERS]END");
				}
			} catch (InterruptedException e) {
			}
		}
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
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> rangedBeacons) {
				// Note that results are not delivered on UI thread.
				for (Beacon rangedBeacon : rangedBeacons) {
					System.out.println(String.format("BEACON MAC: %s (%.2fm)",
							rangedBeacon.getMacAddress(),
							Utils.computeAccuracy(rangedBeacon)));
					processBeacon(rangedBeacon);
				}

			}
		});
	}

	@Override
	public void onStart(Intent intent, int startId) {
		isRunning = true;

		System.out.println("SERVICE STARTED");
		Toast.makeText(this, " Service Started", Toast.LENGTH_SHORT).show();

		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(new Region("rid", null, null,
							null));
				} catch (RemoteException e) {

				}
			}
		});
		
		Thread t = new Thread(new calculateLocation());
		t.start();
	}

	@Override
	public void onDestroy() {
		isRunning = false;

		Toast.makeText(this, " Service Killed", Toast.LENGTH_SHORT).show();
	}

}
