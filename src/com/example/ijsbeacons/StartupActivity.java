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
	boolean Expo = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        getActionBar().setTitle("ijsBeacons");
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		
		String userAndroidId = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		((IJsBeaconsApplication) this.getApplication()).setUserAndroidId(userAndroidId);
		
		if (BackgroundService.isRunning == false) {
			System.out.println("START SERVICE MANUALLY");
            Intent service = new Intent(this, BackgroundService.class);
            startService(service);
		}

		//CHECK IF ANDROID ID EXISTS IN DATABASE
		boolean userExists = false;
//		try {
//			SoapResult_getUser result = (SoapResult_getUser) new SendSoapRequest().execute(new SoapRequest_getUserByAndroidId(userAndroidId)).get();
//		
//			if (result != null && result.resultCode == 1) {
//				userExists = true;
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		if (Expo == true) {
			//GO EMEDIATELY TO EXPO
	    	Intent intent = new Intent(this, MediatourActivity.class);
	        startActivity(intent);
		} else if (userExists) {
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
