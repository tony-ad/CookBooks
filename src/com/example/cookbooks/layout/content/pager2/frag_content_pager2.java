package com.example.cookbooks.layout.content.pager2;

import com.example.cookbooks.R;
import com.example.cookbooks.helper.cook_List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class frag_content_pager2 extends Fragment{

	
	private Context mContext;
	private View view;
	
	private pager2_change_Receiver receiver;
	
	private TextView textView_title;
	private TextView textView_ingredients;
	private ImageView imageView_picture;
	private TextView textView_content;
	private TextView textView_burden;
	
	public frag_content_pager2(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_content_2, container,false);
		
		initView();
		initRecevier();
		
		return view;
	}

	private void initRecevier() {
		// TODO Auto-generated method stub
		receiver = new pager2_change_Receiver();
		IntentFilter iFilter = new IntentFilter("pager2_change_Receiver");
		mContext.registerReceiver(receiver, iFilter);
	}

	private void initView() {
		// TODO Auto-generated method stub
		textView_title=(TextView) view.findViewById(R.id.pager2_title);
		textView_ingredients = (TextView) view.findViewById(R.id.pager2_ingredients);
		textView_content=(TextView) view.findViewById(R.id.pager2_content);
		imageView_picture=(ImageView) view.findViewById(R.id.pager2_picture);
		textView_burden=(TextView) view.findViewById(R.id.pager2_burden);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mContext.unregisterReceiver(receiver);
	}
	
	private void setContent(int pos){
		textView_title.setText(cook_List.getCook_List().get(pos).getCb_name());
		textView_ingredients.setText("主要食材："+cook_List.getCook_List().get(pos).getCb_ingredients());
		textView_content.setText("步骤如下：" + cook_List.getCook_List().get(pos).getCb_steps());	
		imageView_picture.setImageBitmap(cook_List.getCook_List().get(pos).getCb_picture());
		textView_burden.setText("配料："+cook_List.getCook_List().get(pos).getCb_burden());
	}
	
	private class pager2_change_Receiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int pos = intent.getIntExtra("pos", 0);
			setContent(pos);
		}	
	}
	
	
}
