package com.example.ijsbeacons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MediatourContestActivity extends Activity
{
	private TextView welcomeTitle;
	private TextView welcomeText;
	private EditText welcomeName;
	private EditText contestValue;
	private Button btnContest;
	
	private boolean isRunning;
	private SharedPreferences settings;
	private String personName;
	
	Context self;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		self = this;
		settings = getSharedPreferences("ijsBeacons_Mediatour", 0);
		personName = settings.getString("welcomeName", "persoon");
		
		if( settings.getBoolean("hasParticipated", false))
		{
			finish();
		}

		super.onCreate(savedInstanceState);
		
		// hide title and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.mediatour);
		
		welcomeTitle = (TextView) findViewById(R.id.welcomeTitle);
		welcomeText = (TextView) findViewById(R.id.welcomeText);
		welcomeName = (EditText) findViewById(R.id.welcomeName);
		contestValue = (EditText) findViewById(R.id.contestValue);
		btnContest = (Button) findViewById(R.id.btnContest);

		welcomeTitle.setText("Uit hoeveel legostenen bestaat de T-Rex??");
		welcomeText.setText(personName + ", leuk dat je interesse toont!\nOm mee te doen met deze wedstrijd, hoef je naast je gok van het aantal legostenen, alleen je e-mail in te vullen. JeU krijgt alleen een mail ter bevestiging van je inzending, en een mail met de uitslag.");
		
		contestValue.setHint("Legostenen");
		welcomeName.setHint("email adres");
		
		btnContest.setText("Doe mee!");
		btnContest.setOnClickListener(
			new View.OnClickListener() {
				
				@Override
				public void onClick(View v)
				{
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("hasParticipated", true);
					editor.commit();
					
					Toast.makeText(self, "Bedankt " + personName + ", voor je inzending!", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		);
	}

}
