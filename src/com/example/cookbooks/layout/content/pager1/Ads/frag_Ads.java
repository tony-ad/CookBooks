package com.example.cookbooks.layout.content.pager1.Ads;

import java.util.ArrayList;
import java.util.List;

import com.example.cookbooks.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class frag_Ads extends Fragment implements OnClickListener{
	
	private Context mContext;
	private View view;
	
	/**
	 * 显示页面
	 */
	private ViewPager Ads_viewPager;
	
	/**
	 * 底部小按钮
	 */
	private LinearLayout Ads_ll;
	
	/**
	 * 数量是 image_ids的数量+1
	 */
	private List<ImageView> list_imageView;
	
	private int[] image_ids = new int[]{
			R.drawable.ads_image_1,
			R.drawable.ads_image_2,
			R.drawable.ads_image_3,
			R.drawable.ads_image_4,
			R.drawable.ads_image_5};
	
	private ImageView[] imageViews = new ImageView[image_ids.length];

	
	private int Light_Button = R.drawable.ads_button_light;
	private int Drak_Button = R.drawable.ads_button_drak;
	
	private boolean isPager;
	
	public frag_Ads(Context context){
		mContext=context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_ads, container,false);
		
		initView();
		init();
		initViewPager();
		ButtonColorChange(0);
		return view;
	}

	/**
	 * 开启子线程
	 * 自动播放广告栏
	 * 
	 */
	public void goPager(){
		Log.d("gopager", "thread is run");
		thread.run();
	}
	
	private Thread thread = new Thread(new Runnable() {	
		@Override
		public void run() {
			// TODO Auto-generated method stub
				Log.d("gopager", "sendMessage");
				handler.sendEmptyMessageDelayed(1, 3500);
		}
	});

	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				Log.d("gopager", "setCurrentItem");
				Ads_viewPager.setCurrentItem(Ads_viewPager.getCurrentItem()+1);
				thread.run();
			}
		};
	};
	
	
	private void initViewPager() {
		// TODO Auto-generated method stub
		Ads_viewPager.setAdapter(new MyPagerAdapter());
		Ads_viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			//参数：将要滚动到 哪个页面
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				//页面相应按钮的显示
				Log.d("PagerScroll", "onPageSelected");
				ButtonColorChange(arg0);	
	
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.d("PagerScroll", "onPageScrolled  " + arg0);
				Log.d("PagerScroll", "list_imageView.size()  " + list_imageView.size());
				if(arg0==list_imageView.size()-1){
					Ads_viewPager.setCurrentItem(0,false);
				}
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				Log.d("PagerScroll", "onPageScrollStateChanged  " + arg0);
			}
		});
	}

	private class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_imageView.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(list_imageView.get(position));
			return list_imageView.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(list_imageView.get(position));
		}
		
		
		
	}
	
	/**
	 * 按钮颜色的改变
	 * @param a
	 */
	private void ButtonColorChange(int a){
		for(int i=0;i<imageViews.length;i++){
			if(i==a){
				imageViews[i].setImageResource(Light_Button);
			}else {
				imageViews[i].setImageResource(Drak_Button);
			}
		}
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		list_imageView=new ArrayList<ImageView>();
		for(int i=0;i<image_ids.length+1;i++){
			//用来作为ViewPgaer无限循环的最后一个页面，它显示的是第一个页面的内容
			if(i==image_ids.length){
				ImageView imageView = new ImageView(mContext);
				imageView.setImageResource(image_ids[0]);
//				imageView.setLayoutParams(new LayoutParams
//						(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				list_imageView.add(imageView);
			}else {
				
				ImageView imageView = new ImageView(mContext);
				imageView.setImageResource(image_ids[i]);
				list_imageView.add(imageView);

				imageViews[i] = new ImageView(mContext);
				imageViews[i].setId(i);
				imageViews[i].setPadding(20, 0, 0, 0);
				imageViews[i].setOnClickListener(this);
			
				Ads_ll.addView(imageViews[i]);
			}
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		Ads_viewPager=(ViewPager) view.findViewById(R.id.Ads_viewPager);
		Ads_ll=(LinearLayout) view.findViewById(R.id.Ads_ll);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Ads_viewPager.setCurrentItem(v.getId());
	}
	
}
