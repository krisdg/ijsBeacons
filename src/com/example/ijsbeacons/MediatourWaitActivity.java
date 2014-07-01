package com.example.ijsbeacons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
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
		
		setContentView(R.layout.mediatour);
		
		welcomeTitle = (TextView) findViewById(R.id.welcomeTitle);
		welcomeText = (TextView) findViewById(R.id.welcomeText);
		welcomeName = (EditText) findViewById(R.id.welcomeName);
		contestValue = (EditText) findViewById(R.id.contestValue);
		welcomeButton = (Button) findViewById(R.id.btnContest);

		welcomeTitle.setText("ART OF THE BRICK");
		welcomeText.setText("\n\n\n\n\n\n\nHallo " + personName + "! Loop gerust rond door de expositie. Mocht er iets interessants in de buurt zijn laat ik het je weten!");
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
						
			    		if (closestBeacon.equals("LEGOOBJECT")) {
			    			Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(1000);

			    			Intent popup = new Intent(self, MediatourPopupActivity.class);
			    			startActivity(popup);
							overridePendingTransition(R.anim.slideup, R.anim.slidedown);
			    		}
			    		if (closestBeacon.equals("MEDIATOUREND")) {
			    			Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(1000);

			    			Intent popup = new Intent(self, MediatourContestActivity.class);
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
