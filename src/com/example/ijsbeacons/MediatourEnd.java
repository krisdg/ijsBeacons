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

public class MediatourEnd extends Activity
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

		welcomeTitle.setText("BEDANKT!");
		welcomeText.setText("\n\n\n\n\n\n\nBedankt voor uw inzending " + personName + "! Dit is het einde van de demonstratie.");
		welcomeName.setVisibility(View.GONE);
		contestValue.setVisibility(View.GONE);
		welcomeButton.setVisibility(View.GONE);
	}
}
