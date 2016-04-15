package com.example.cookbooks;


import java.util.ArrayList;
import java.util.List;

import com.example.cookbooks.Activity.NavigationCooks;
import com.example.cookbooks.helper.MyApplication;
import com.example.cookbooks.layout.content.pager1.frag_content_pager1;
import com.example.cookbooks.layout.content.pager1.Ads.frag_Ads;
import com.example.cookbooks.layout.content.pager2.frag_content_pager2;
import com.example.cookbooks.layout.menu.frag_menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

public class MainActivity extends FragmentActivity{

	private Receiver_For_cookbook_content rfcc;
	
	private ListView listView_navigation;
	private Button button_menu;
	private List<String> list_datas;
	
	private Fragment frag_ads;
	
	private String string[] = new String[]{"搜索","精品推荐","我的收藏"};
	
	private MenuDrawer mMenuDrawer;
	private boolean MenuIsOpen = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mMenuDrawer = MenuDrawer.attach(MainActivity.this, MenuDrawer.MENU_DRAG_CONTENT, Position.LEFT); 
	    mMenuDrawer.setContentView(R.layout.activity_main_2);  
	    mMenuDrawer.setMenuView(R.layout.layout_menu);  
		
	    initMenuVie();	
	    initFragAds();
	    initView();
	    initListViewDatas();
	    initListView();
		initReceiver();
		handler.sendEmptyMessageDelayed(0, 2000);	
	}
	
	/**
	 * 初始化广告碎片
	 */
	private void initFragAds() {
		// TODO Auto-generated method stub
		frag_ads = new frag_Ads(MainActivity.this);
		getSupportFragmentManager().beginTransaction().add(R.id.frame_main_ads, frag_ads).commit();

	}

	/**
	 * 初始化导航ListView
	 */
	private void initListView() {
		// TODO Auto-generated method stub
		listView_navigation = (ListView) findViewById(R.id.listview_main_navigation);
		listView_navigation.setAdapter(new ArrayAdapter<String>
		(this, android.R.layout.simple_list_item_1, list_datas));
		
		listView_navigation.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				if(position!=2){
					Intent intent = new Intent(MainActivity.this,NavigationCooks.class);
					intent.putExtra("pos", position);
					intent.putExtra("title", string[position]);
					startActivity(intent);
				}
				
				
				
			}
		});
		
	}

	/**
	 * 初始化导航ListView的数据
	 */
	private void initListViewDatas() {
		// TODO Auto-generated method stub
		list_datas = new ArrayList<String>();
		for(int i=0;i<string.length;i++){
			list_datas.add(string[i]);
		}
	}

	/**
	 * 初始化主布局的其他控件
	 * 
	 * 1.菜单栏的开关按钮
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		button_menu = (Button) findViewById(R.id.button_main_menu);
		button_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tooggleMenu();
			}
		});
	}

	
	
	private Handler handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what==0){
					Log.d("gopager", "Activity is gopager");
					//自动播放广告栏
					((frag_Ads) frag_ads).goPager();
				}
			};
	};
	
	/**
	 * 初始化菜单碎片
	 * 
	 * 把菜单栏的碎片添加到菜单布局中
	 * 
	 */
	private void initMenuVie() {
		// TODO Auto-generated method stub
		Fragment fragment = new frag_menu(MainActivity.this);
		getSupportFragmentManager().beginTransaction().add(R.id.menu_frag_framelayout,
				fragment).commit();
	}

	


	
	/**
	 * 菜单的开关
	 */
	public void tooggleMenu(){
		mMenuDrawer.toggleMenu();
		if(MenuIsOpen==true){
			MenuIsOpen=false;
		}else {
			MenuIsOpen=true;
		}
	}
	
//	/**
//	 * 跳转页面
//	 */
//	public void goPager(int pos){
//		viewPager.setCurrentItem(pos);
//	}
	

	/**
	 * 初始化广播接收器
	 */
	private void initReceiver() {
		// TODO Auto-generated method stub
		IntentFilter intentFilter = new IntentFilter("Receiver_For_cookbook_content");
		rfcc = new Receiver_For_cookbook_content();
		registerReceiver(rfcc, intentFilter);
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(rfcc);
		MyApplication.getHttpQueues().cancelAll("Get_Cook");
	}
	
	/**
	 * 广播接收器
	 * @author Administrator
	 *
	 */
	private class Receiver_For_cookbook_content extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub		

		}
	}

	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(MenuIsOpen==true){
				//如果菜单栏打开了，则先关闭菜单栏
				tooggleMenu();
				return true;
			}
			ExitDialog(this).show();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	private Dialog ExitDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("MyCooks").setMessage("确定退出此程序吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		return builder.create();
	}
	
	
}


