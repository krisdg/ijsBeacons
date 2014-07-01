package com.example.ijsbeacons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MediatourActivity extends Activity
{
	private TextView welcomeTitle;
	private TextView welcomeText;
	private EditText welcomeName;
	private EditText contestValue;
	private Button welcomeButton;
	
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	
	Context self;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// hide title and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.mediatour);

		// simple reset
		settings = getSharedPreferences("ijsBeacons_Mediatour", 0);
		editor = settings.edit();
		editor.putString("welcomeName", "");
		editor.putBoolean("isRunning", false);
		editor.putBoolean("hasParticipated", false);
		editor.commit();
		
		welcomeTitle = (TextView) findViewById(R.id.welcomeTitle);
		welcomeText = (TextView) findViewById(R.id.welcomeText);
		welcomeName = (EditText) findViewById(R.id.welcomeName);
		contestValue = (EditText) findViewById(R.id.contestValue);
		welcomeButton = (Button) findViewById(R.id.btnContest);
		
		welcomeTitle.setText("WELKOM!");
		welcomeText.setText("\n\n\nBedankt dat u ons wilt helpen bij het testen van onze app. Om de tour te starten voert u hieronder uw naam in en klikt u op start. Veel plezier!");
		welcomeButton.setText("Start");
		contestValue.setVisibility(View.GONE);
		
		// secure the "this"
		self = this;
		
		welcomeButton.setOnClickListener(
			new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					String name = welcomeName.getText().toString();
					
					if(name != "")
					{
						editor.putString("welcomeName", name);
						editor.putBoolean("isRunning", true);
						editor.commit();
						
						Intent MediatourWaitActivity = new Intent(self, MediatourWaitActivity.class);
						startActivity(MediatourWaitActivity);
					}
				}
			}
		);
	}
}
