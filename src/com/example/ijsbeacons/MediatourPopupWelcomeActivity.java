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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MediatourPopupWelcomeActivity extends Activity
{
	private TextView popupTitle;
	private TextView popupText;
	private Button btnContest;
	private ImageView img;
	
	private SharedPreferences settings;
	private String personName;
	
	Context self;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		self = this;
		settings = getSharedPreferences("ijsBeacons_Mediatour", 0);
		personName = settings.getString("welcomeName", "persoon");

		super.onCreate(savedInstanceState);
		
		// hide title and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.mediatour_popup);
		
		popupTitle = (TextView) findViewById(R.id.popupTitle);
		popupText = (TextView) findViewById(R.id.popupText);
		btnContest = (Button) findViewById(R.id.btnContest);
		img = (ImageView) findViewById(R.id.imageView1);
		
		btnContest.setVisibility(View.GONE);
		img.setVisibility(View.GONE);

		popupTitle.setText("Welkom!");
		popupText.setText("Welkom " + personName + ", de tour kan nu echt beginnen!\nAan je rechterhand worden audiobooks uitgedeeld.\nOh, en let maar niet op de grote meneer in nette kleding aan je linkerhand!");
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
		    		
		    		if (closestBeacon.equals("TREX")) {
		    			Intent popup = new Intent(self, MediatourPopupActivity.class);
		    			startActivity(popup);
						overridePendingTransition(R.anim.slideup, R.anim.slidedown);
		    			break;
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
