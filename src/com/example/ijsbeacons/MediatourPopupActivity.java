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

public class MediatourPopupActivity extends Activity
{
	private TextView popupTitle;
	private TextView popupText;
	private String content;
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

		super.onCreate(savedInstanceState);
		
		// hide title and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.mediatour_popup);
		
		popupTitle = (TextView) findViewById(R.id.popupTitle);
		popupText = (TextView) findViewById(R.id.popupText);
		btnContest = (Button) findViewById(R.id.btnContest);
		
		content = "Kijk, " + personName + ", een dinosaurus!\nDeze dinosaurus wordt een Tiranosaurus Rex genoemd, of T-Rex in het kort. Deze T-Rex bestaat volledig uit legostenen!";
		Boolean hasParticipated = settings.getBoolean("hasParticipated", false);
		if( hasParticipated )
		{
			btnContest.setVisibility(View.GONE);
			
		} else {
			
			content += "\nKan jij raden uit hoeveel deze T-Rex bestaat? Klik op onderstaande knop om mee te doen met onze wedstrijd, en win een bezoek aan Legoland!";
		}

		popupTitle.setText("T-Rex van Legostenen!");
		popupText.setText(content);
		btnContest.setText("Doe mee!");
		btnContest.setOnClickListener(
			new View.OnClickListener() {
				
				@Override
				public void onClick(View v)
				{
					Intent popup = new Intent(self, MediatourContestActivity.class);
					startActivityForResult(popup, 1);
//					startActivity(popup);
				}
			}
		);
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Boolean hasParticipated = settings.getBoolean("hasParticipated", false);
		if( hasParticipated )
		{
			content = "Kijk, " + personName + ", een dinosaurus!\nDeze dinosaurus wordt een Tiranosaurus Rex genoemd, of T-Rex in het kort. Deze T-Rex bestaat volledig uit legostenen!";
			popupText.setText(content);
			
			btnContest.setVisibility(View.GONE);
		}
	}

}
