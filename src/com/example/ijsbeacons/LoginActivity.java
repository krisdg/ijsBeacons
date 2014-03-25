package com.example.ijsbeacons;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import com.example.ijsbeacons.SOAP.*;

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
import android.widget.Toast;
import android.os.Build;

public class LoginActivity extends Activity {

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

		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				username.setText(account.name);
			}
		}

		register.setOnClickListener(but_registerPressed);
		close.setOnClickListener(but_closePressed);

	}

	View.OnClickListener but_registerPressed = new View.OnClickListener() {
		public void onClick(View v) {
			// Register user into database
			try {
				SoapRequest_registerUser registerUserObject = new SoapRequest_registerUser();
				registerUserObject.userAndroidId = ((IJsBeaconsApplication) LoginActivity.this.getApplication()).getUserAndroidId();
				registerUserObject.userName = username.getText().toString();

				SoapResult result = (SoapResult) new SendSoapRequest().execute(
						registerUserObject).get();
				
				Toast.makeText(LoginActivity.this, result.resultMessage, Toast.LENGTH_SHORT).show();

				if (result.resultCode == 1) {
					Intent intent = new Intent(LoginActivity.this, StartupActivity.class);
					startActivity(intent);
					finish();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	View.OnClickListener but_closePressed = new View.OnClickListener() {
		public void onClick(View v) {
			// Close app
			finish();
			System.exit(0);
		}
	};

}
