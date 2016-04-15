package com.example.cookbooks.Activity;

import com.example.cookbooks.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_activity extends Activity{

	
	private EditText et_user;
	private EditText et_pw;
	private Button login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activitylayout_login);
		
//		et_pw = (EditText) findViewById(R.id.login_ET_Passwork);
//		et_user=(EditText) findViewById(R.id.login_ET_User);
//		login=(Button) findViewById(R.id.SignIn_button);
//		
//		login.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				String aString = et_pw.getText().toString();
//				String bString = et_user.getText().toString();
//				
//				if(aString=="aaa" && bString=="aaa"){
//					Toast.makeText(login_activity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//				
//					finish();
//				}
//				
//			}
//		});
		
	}	
}
