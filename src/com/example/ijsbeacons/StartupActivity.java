package com.example.ijsbeacons;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import com.example.ijsbeacons.SOAP.*;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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
		

		try {
			SoapResult_getUserByAndroidId result = (SoapResult_getUserByAndroidId) new SendSoapRequest().execute(new SoapRequest_getUserByAndroidId(android_id)).get();
		
			Toast.makeText(this, "Result: " + result.returnCode, Toast.LENGTH_LONG).show();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
