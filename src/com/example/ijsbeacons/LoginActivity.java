package com.example.ijsbeacons;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class LoginActivity extends Activity implements OnClickListener {
	
	EditText username;
	Button register;
	Button close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setTitle("ijsBeacons");
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		username = (EditText) findViewById(R.id.username);
		register = (Button) findViewById(R.id.but_register);
		close = (Button) findViewById(R.id.but_close);
		
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        username.setText(account.name);
		    }
		}
		
		register.setOnClickListener(this);
		close.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
	    case R.id.but_register:
	    	//Register user into database
	    	Intent intent = new Intent(this, StartupActivity.class);
	        startActivity(intent);
	    	finish();
	    case R.id.but_close:
	    	//Close app
	    	finish();
	    	System.exit(0);
		}
	}

}
