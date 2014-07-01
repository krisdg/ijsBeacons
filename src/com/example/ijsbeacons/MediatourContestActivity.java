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

		welcomeTitle.setText("BEDANKT!");
		welcomeText.setText(personName + ", bedankt dat je zoveel interesse hebt getoond! Om je te bedanken mag je deelnemen aan de westrijd voor 2 gratis tickets voor deze expositie om weg te geven! De vraag is, hoeveel legostenen bevat deze ruimte? Wij sturen je een mail als je in de prijzen bent gevallen!");
		
		contestValue.setHint("Legostenen");
		welcomeName.setHint("E-mailadres");
		
		btnContest.setText("Doe mee!");
		btnContest.setOnClickListener(
			new View.OnClickListener() {
				
				@Override
				public void onClick(View v)
				{
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("hasParticipated", true);
					editor.commit();
					
	    			Intent popup = new Intent(self, MediatourEnd.class);
	    			startActivity(popup);
					overridePendingTransition(R.anim.slideup, R.anim.slidedown);
					
					finish();
				}
			}
		);
	}

}
