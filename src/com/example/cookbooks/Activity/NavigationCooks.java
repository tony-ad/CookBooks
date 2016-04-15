package com.example.cookbooks.Activity;

import java.util.ArrayList;
import java.util.List;

import com.example.cookbooks.MainActivity;
import com.example.cookbooks.R;
import com.example.cookbooks.layout.content.pager1.frag_content_pager1;
import com.example.cookbooks.layout.content.pager2.frag_content_pager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Window;

/**
 * 此活动用到content包中的内容
 * @author Administrator
 *
 */
public class NavigationCooks extends FragmentActivity {

	
	private Fragment fragment_pager1;
	private Fragment fragment_pager2;
	private List<Fragment> pagerList;
	private FragmentPagerAdapter pagerAdapter;
	private ViewPager viewPager;

	/**
	 * 点击的导航位置
	 */
	private int pos;
	
	private String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_navigation);
		
		Intent intent = getIntent();
		pos = intent.getIntExtra("pos", 0);
		title = intent.getStringExtra("title");
		
		initFragLists();
		initView();
	}
	
	
	
	private void initFragLists() {
		// TODO Auto-generated method stub
		pagerList=new ArrayList<Fragment>();
		
		fragment_pager1 = new frag_content_pager1(NavigationCooks.this,pos,title);
		pagerList.add(fragment_pager1);
		
		fragment_pager2 = new frag_content_pager2(NavigationCooks.this);
		pagerList.add(fragment_pager2);
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		viewPager=(ViewPager) findViewById(R.id.mViewPager);
		pagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return pagerList.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return pagerList.get(arg0);
			}
		};
		viewPager.setAdapter(pagerAdapter);
		
	}
	
	public void goPager(int i){
		viewPager.setCurrentItem(i);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(viewPager.getCurrentItem()==1){
				viewPager.setCurrentItem(0);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
}
