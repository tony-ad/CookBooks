package com.example.cookbooks.layout.menu;

import com.example.cookbooks.R;
import com.example.cookbooks.Activity.login_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class frag_menu extends Fragment{

	private Context mContext;
	private View view;
	
	private Button loginButton;
	
	public frag_menu(){
		
	}
	
	public frag_menu(Context context){
		mContext=context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_frag_menu, container,false);
		initView();
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		loginButton = (Button) view.findViewById(R.id.login);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,login_activity.class);
				mContext.startActivity(intent);
			}
		});
	}
	
	
}
