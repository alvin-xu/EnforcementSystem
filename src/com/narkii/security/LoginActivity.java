package com.narkii.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	private EditText name,password;
	private Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		name=(EditText) findViewById(R.id.login_username);
		password=(EditText) findViewById(R.id.login_password);
		login=(Button) findViewById(R.id.login_button);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(!name.getText().toString().equals("") && !password.getText().toString().equals("")){
					Intent i=new Intent(LoginActivity.this, MainActivity.class);
					startActivity(i);
					finish();
//				}
			}
		});
	}
	
}
