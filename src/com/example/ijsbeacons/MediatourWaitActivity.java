package com.example.ijsbeacons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MediatourWaitActivity extends Activity
{
	private TextView welcomeTitle;
	private TextView welcomeText;
	private EditText welcomeName;
	private EditText contestValue;
	private Button welcomeButton;
	
	private boolean isRunning;
	private SharedPreferences settings;
	
	Context self;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		self = this;
		settings = getSharedPreferences("ijsBeacons_Mediatour", 0);

		super.onCreate(savedInstanceState);
		
		// hide title and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.mediatour);
		
		welcomeTitle = (TextView) findViewById(R.id.welcomeTitle);
		welcomeText = (TextView) findViewById(R.id.welcomeText);
		welcomeName = (EditText) findViewById(R.id.welcomeName);
		contestValue = (EditText) findViewById(R.id.contestValue);
		welcomeButton = (Button) findViewById(R.id.btnContest);

		welcomeTitle.setText("Mediatour demo");
		welcomeText.setText("Loop gerust rond. Mocht er iets interessants in de buurt zijn vertellen wij het je!");
		welcomeName.setVisibility(View.GONE);
		contestValue.setVisibility(View.GONE);
		welcomeButton.setVisibility(View.GONE);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		final IJsBeaconsApplication app = (IJsBeaconsApplication) this.getApplication();
		
		new Thread(new Runnable() {
		    public void run() {
		    	while(true) {
		    		String closestBeacon;
		    		closestBeacon = app.getMediaTourBeacon();
		    		
		    		if (closestBeacon.equals(app.getMediaTourLastBeacon()) == false) {
						app.setMediaTourLastBeacon(closestBeacon);
						
			    		if (closestBeacon.equals("TREX")) {
			    			Intent popup = new Intent(self, MediatourPopupActivity.class);
			    			startActivity(popup);
							overridePendingTransition(R.anim.slideup, R.anim.slidedown);
			    			break;
			    		}
			    		if (closestBeacon.equals("MEDIATOURSTART")) {
			    			Intent popup = new Intent(self, MediatourPopupWelcomeActivity.class);
			    			startActivity(popup);
							overridePendingTransition(R.anim.slideup, R.anim.slidedown);
			    			break;
			    		}
		    		}

		    		try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    }
		}).start();
	}
}
