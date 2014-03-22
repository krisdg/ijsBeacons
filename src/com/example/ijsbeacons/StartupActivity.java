package com.example.ijsbeacons;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.provider.Settings.Secure;

public class StartupActivity extends Activity
{
	
	String android_id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        getActionBar().setTitle("ijsBeacons");
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		
		android_id = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		
		if (BackgroundService.isRunning == false) {
			System.out.println("START SERVICE MANUALLY");
            Intent service = new Intent(this, BackgroundService.class);
            startService(service);
		}
		
		//DEBUG!!
		((IJsBeaconsApplication) this.getApplication()).setLastUpdate(123);
		int i = ((IJsBeaconsApplication) this.getApplication()).getLastUpdate();
		
		//CHECK IF ANDROID ID EXISTS IN DATABASE
		boolean userExists = true; //DEBUG
		
		if (userExists) {
			//IF SO: GO TO MAINACTIVITY
	    	Intent intent = new Intent(this, MainActivity.class);
	        startActivity(intent);
		} else {
			//ELSE:  GO TO LOGIN ACTIVITY
	    	Intent intent = new Intent(this, LoginActivity.class);
	        startActivity(intent);
		}
		
		finish();
	}
}
