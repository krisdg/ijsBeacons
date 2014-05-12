package com.example.ijsbeacons;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.example.ijsbeacons.SOAP.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BackgroundService extends Service {

	BeaconManager beaconManager;

	List<BeaconIdentifier> foundBeacons = new ArrayList<BeaconIdentifier>();

	static boolean isRunning = false;
	
	String userAndroidId;
	
	//Beacon variables
	BeaconIdentifier closestBeacon;
	
	//Statistic variables
	boolean statisticsReset = false;
	double walkedDistance = 0;
	int coffeeMachineCount = 0;
	int lunchRoomCount = 0;
	int lunchRoomDistance;
	List<BeaconIdentifier> seenSurfaceBeacons = new ArrayList<BeaconIdentifier>();
	double walkingSpeed = 0;
	//Temporary statistic variables
	long timestampGroundFloor = 0;
	long timestampAttic = 0;
	
	//Define beacon identifiers
    public List<BeaconIdentifier> beacons = new ArrayList<BeaconIdentifier>();
    public List<DistanceRule> distanceRules = new ArrayList<DistanceRule>();
	
	public void defineBeaconList() {
		beacons.add(new BeaconIdentifier("EA:4B:01:B6:4C:F5", "COFFEEMACHINE", new int[] {1, 0, 0})); //GROEN
		beacons.add(new BeaconIdentifier("D4:23:26:59:34:AD", "HALL", new int[] {5, 10, 10})); //PAARS
		beacons.add(new BeaconIdentifier("D8:38:9B:3F:55:F8", "ATTIC", new int[] {10, 0, 0})); //BLAUW
		beacons.add(new BeaconIdentifier("00:00:00:00:00:00", "LUNCHROOM", new int[] {10, 0, 0})); //--
		
		lunchRoomDistance = 1000;
		
		distanceRules.add(new DistanceRule(getBeaconByName("COFFEEMACHINE"), getBeaconByName("ATTIC"), 50));
		distanceRules.add(new DistanceRule(getBeaconByName("HALL"), getBeaconByName("ATTIC"), 34));
	}
	
	public BeaconIdentifier getBeaconByName(String name) {
		for (BeaconIdentifier beacon : beacons) {
			if (beacon.name.equals(name)) {
				return beacon;
			}
		}
		return null;
	}

	public BeaconIdentifier getBeacon(String MAC) {
		for (BeaconIdentifier beacon : beacons) {
			if (beacon.MAC.equals(MAC)) {
				return beacon;
			}
		}
		return null;
		
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
			BeaconIdentifier newBeacon = getBeacon(MAC);
			
			if (newBeacon != null) {
				newBeacon.MAC = MAC;
				newBeacon.lastSeen = timestamp;
				newBeacon.putDistance(distance);
	
				foundBeacons.add(newBeacon);

				System.out.println("[BEACON IDENTIFIERS]ADDED BEACON " + MAC);
			}
		}
	}
	
	public BeaconIdentifier getClosestBeacon() {
		double closestDistance = 10;
		BeaconIdentifier localClosestBeacon = null;
		
		for (BeaconIdentifier beaconId : foundBeacons) {
			if (localClosestBeacon == null) {
				localClosestBeacon = beaconId;
				closestDistance = beaconId.getAvarageDistance();
			} else {
				if (closestDistance > beaconId.getAvarageDistance()) {
					localClosestBeacon = beaconId;
					closestDistance = beaconId.getAvarageDistance();
				}
			}
		}
		
		return localClosestBeacon;
	}
	
	public double calculateDistance(BeaconIdentifier bcn1, BeaconIdentifier bcn2) {
		double distance = -1;
		
		for (DistanceRule distanceRule : distanceRules) {
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
		
		if (bcn1.name.equals("LUNCHROOM") || bcn2.name.equals("LUNCHROOM")) {
			distance = lunchRoomDistance;
		}
		
		return distance;
	}

	public class CalculateLocation implements Runnable {
		public void run() {
			try {				
				while(true) {
					Thread.sleep(3000);
					
					System.out.println("[BEACON IDENTIFIERS]START");
					
					List<BeaconIdentifier> toRemove = new ArrayList<BeaconIdentifier>();
					for (BeaconIdentifier bcn : foundBeacons) {
						if (bcn.lastSeen + 10000 < System.currentTimeMillis()) {
							toRemove.add(bcn);
							System.out.println("[BEACON IDENTIFIERS]REMOVED BEACON " + bcn.MAC);
						}
						System.out.println("[BEACON IDENTIFIERS]BCN:" + bcn.name + " : " + String.format("%1$,.2f", bcn.getAvarageDistance()) + " : " + bcn.lastSeen);
					}
					foundBeacons.removeAll(toRemove);
					
					if (foundBeacons.size() == 0) {
						System.out.println("[BEACON IDENTIFIERS]NO BEACONS FOUND");
					} else {
						if (closestBeacon == null) {
							//Initialize closestBeacon
							closestBeacon = getClosestBeacon();
						}
						
						//Save timestamp for walking speed calculation
						if (closestBeacon.name.equals("HALL")) {
							timestampGroundFloor = System.currentTimeMillis();
						}
						
						if (closestBeacon.MAC.equals(getClosestBeacon().MAC) == false) {
							//New closest beacon found!
							//Call onFoundNewClosestBeacon()
							onFoundNewClosestBeacon(closestBeacon, getClosestBeacon());
							closestBeacon = getClosestBeacon();
						}
						
						//Seen surface
						boolean foundBeacon = false;
						for (BeaconIdentifier bcn : seenSurfaceBeacons) {
							if (bcn.MAC.equals(closestBeacon.MAC)) {
								foundBeacon = true;
							}
						}
						if (foundBeacon == false) {
							seenSurfaceBeacons.add(closestBeacon);
						}
					}

					System.out.println("[BEACON IDENTIFIERS]END");
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	public class UpdateUserStatistics implements Runnable {
		public void run() {
			try {				
				while(true) {
					Thread.sleep(30000);
					
					System.out.println("UPDATE USER STATS");
					
					SoapRequest_updateStatistics request = new SoapRequest_updateStatistics();
					
					request.userAndroidId = userAndroidId;
					request.walkedDistance = (int) walkedDistance;
					request.coffeeMachineCount = coffeeMachineCount;
					request.lunchRoomCount = lunchRoomCount;
					request.seenSurface = seenSurfaceBeacons.size() * 100 / beacons.size();
					request.walkingSpeed = (int) (walkingSpeed * 1000);

					try {
						SoapResult result = (SoapResult) new SendSoapRequest().execute(request).get();
						
						if (result == null) {
							System.out.println("UPDATE USER STATS ERROR");
						} else if (result.resultCode == 0) {
							System.out.println("UPDATE USER STATS FAILED: " + result.resultMessage);
						} else if (result.resultCode == 1) {
							System.out.println("UPDATE USER STATS PASSED");
						}
					} catch (ExecutionException e) {
						System.out.println("UPDATE USER STATS ERROR: " + e.getMessage());
						e.printStackTrace();
					}
					
					//Reset statistics at 24PM
					SimpleDateFormat dateFormat = new SimpleDateFormat("H");
					Date date = new Date();
					if(dateFormat.format(date).equals("1")) {
						//Reset statistics
						if (statisticsReset == false) {
							statisticsReset = true;
							
							walkedDistance = 0;
							walkingSpeed = 0;
							coffeeMachineCount = 0;
							lunchRoomCount = 0;
							seenSurfaceBeacons.clear();
						}
					} else {
						statisticsReset = false;
					}
					
					//Save statistics
					dateFormat = new SimpleDateFormat("yyyy-MM-dd");

					SharedPreferences settings = getSharedPreferences("ijsBeaconsStats", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("lastSave", dateFormat.format(date));
					editor.putInt("walkedDistance", (int) walkedDistance);
					editor.putInt("coffeeMachineCount", coffeeMachineCount);
					editor.putInt("lunchRoomCount", lunchRoomCount);
					editor.putInt("walkingSpeed", (int) (walkingSpeed * 1000));
					
					String seenSurfaceString = "";
					for (BeaconIdentifier bcn : seenSurfaceBeacons) {
						seenSurfaceString += bcn.MAC + "-";
					}
					
					editor.putString("seenSurfaceString", seenSurfaceString);
					editor.commit();
				}
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void onFoundNewClosestBeacon(BeaconIdentifier oldBeacon, BeaconIdentifier newBeacon) {
		double localWalkedDistance = calculateDistance(oldBeacon, newBeacon);
		
		System.out.println("NEW CLOSEST BEACON FOUND: " + newBeacon.name + ", Distance walked: " + localWalkedDistance);
		
		//Update statistics
		if (newBeacon.name.equals("COFFEEMACHINE")) {
			coffeeMachineCount++;
		}
		if (newBeacon.name.equals("LUNCHROOM")) {
			lunchRoomCount++;
		}
		
		SharedPreferences settings = getSharedPreferences("ijsBeacons_Mediatour", 0);
		Boolean isRunning = settings.getBoolean("isRunning", false);
		if ( newBeacon.name.equals("TREX") && isRunning )
		{
			Intent popup = new Intent(this, MediatourPopupActivity.class);
			startActivity(popup);
		}
		
		//Calculate walking speed
		if (newBeacon.name.equals("ATTIC")) {
			timestampAttic = System.currentTimeMillis();
			
			if (timestampGroundFloor != 0) {
				long timeDifference = timestampAttic - timestampGroundFloor;
				timeDifference = timeDifference / 1000;
				double localWalkingSpeed = calculateDistance(getBeaconByName("HALL"), getBeaconByName("ATTIC")) / timeDifference * 3.6;
				System.out.println("WALKINGSPEED: " + localWalkingSpeed + " TIMEDIFFERENCE: " + timeDifference);
				if (localWalkingSpeed > walkingSpeed && timeDifference > 10 && timeDifference < 60) {
					walkingSpeed = localWalkingSpeed;
					
					System.out.println("NEW USER HIGHSCORE WALKINGSPEED: " + walkingSpeed);
				}
			}
		}
		
		walkedDistance += localWalkedDistance;
	}

	@Override
	public void onCreate() {
		defineBeaconList();
		
		userAndroidId = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		
		beaconManager = new BeaconManager(this);
		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region,
				final List<Beacon> rangedBeacons) {
				//Find nearby beacons every second and process them
				for (Beacon rangedBeacon : rangedBeacons) {
					processBeacon(rangedBeacon);
				}
			}
		});
	}

	@Override
	public void onStart(Intent intent, int startId) {
		isRunning = true;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		//Restore statistics
	    SharedPreferences settings = getSharedPreferences("ijsBeaconsStats", 0);
	    String lastSave = settings.getString("lastSave", "");
		    
		if (dateFormat.format(date).equals(lastSave)) {
		    walkedDistance = Double.parseDouble(settings.getInt("walkedDistance", 0) + "");
		    coffeeMachineCount = settings.getInt("coffeeMachineCount", 0);
		    lunchRoomCount = settings.getInt("lunchRoomCount", 0);
		    String seenSurfaceString = settings.getString("seenSurfaceString", "");
		    walkingSpeed = Double.parseDouble(settings.getInt("walkingSpeed", 0) + "") / 1000;
		    
		    //Parse string to surfaceBeaconArray
			String[] seenSurfaceArray = seenSurfaceString.split("-");
			for (String MAC : seenSurfaceArray) {
				if (MAC.length() == 17 && getBeacon(MAC) != null) {
					boolean foundBeacon = false;
					for (BeaconIdentifier bcn : seenSurfaceBeacons) {
						if (bcn.MAC.equals(MAC)) {
							foundBeacon = true;
						}
					}
					if (foundBeacon == false) {
						seenSurfaceBeacons.add(getBeacon(MAC));
					}
				}
			}
		}

		System.out.println("SERVICE STARTED");
		Toast.makeText(this, "ijsBeacons service gestart", Toast.LENGTH_SHORT).show();

		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(new Region("rid", null, null, null));
				} catch (RemoteException e) {

				}
			}
		});
		
		Thread calculateLocation = new Thread(new CalculateLocation());
		calculateLocation.start();
		Thread updateUserStatistics = new Thread(new UpdateUserStatistics());
		updateUserStatistics.start();
	}

	@Override
	public void onDestroy() {
		isRunning = false;

		Toast.makeText(this, "ijsBeacons service gestopt", Toast.LENGTH_SHORT).show();
	}

}
