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
	private TextView popupText2;
	private String content;
	private String content2;
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
		popupText2 = (TextView) findViewById(R.id.popupText2);
		
		content = "Kijk " + personName + ", een T-Rex! Deze T-Rex is gemaakt door Mart Zonneveld in juni 2014. Het bouwen ervan heeft wel 2,5 uur geduurd! De rode kleur is voortgekomen uit de bloederige roofpartijen die dit roofdier heeft begaan.";
		content2 = "Wist jij dat een Tyrannosaurus Rex tijdens het roven wel 70 kilometer per uur kon halen? Daarnaast was hij het grootste landroofdier in het Jura-tijdperk.";

		popupTitle.setText("T-REX");
		popupText.setText(content);
		popupText2.setText(content2);
	}
}
