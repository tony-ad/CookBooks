package com.example.cookbooks.layout.content.pager1;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.example.cookbooks.MainActivity;
import com.example.cookbooks.R;
import com.example.cookbooks.Activity.NavigationCooks;
import com.example.cookbooks.helper.CeshiOther;
import com.example.cookbooks.helper.CookBook;
import com.example.cookbooks.helper.MyApplication;
import com.example.cookbooks.helper.NetworkAccess;
import com.example.cookbooks.helper.PictureHelper;
import com.example.cookbooks.helper.cook_List;
import com.example.cookbooks.helper.cookbook_ids;
import com.example.cookbooks.helper.save_cook_list;
import com.example.cookbooks.layout.content.pager1.Ads.frag_Ads;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class frag_content_pager1 extends Fragment {

	private Context context;
	private View view;
	private ListView listView;
	private MyAdapter myAdapter;
	private List<CookBook> list;
	private Button button_back;
	private Button button_changeDatas;
	private EditText editText_query;

	private Button button_query;
	private LinearLayout linearLayout;

	/**
	 * 第一个可见Item的pos
	 */
	private int First_Item;
	/**
	 * 全部item的数量
	 */
	private int All_Item;
	/**
	 * 最后一个可见的item的pos
	 */
	private int End_Item;
	/**
	 * 可见item的数量
	 * 
	 * @param context
	 */
	private int Visit_Item;

	private NavigationCooks activity;

	/**
	 * 根据模式设置数据
	 * 
	 * 0-搜索 1-推荐 2-收藏
	 * 
	 */
	private int show_item_model = -1;

	/**
	 * 点击更换菜谱按钮
	 */
	private boolean isClickChange = false;
	/**
	 * 第一次点击
	 */
	private boolean isFirstCome = true;

	private String title;

	public frag_content_pager1(Context context, int model, String title) {
		this.context = context;
		show_item_model = model;
		this.title = title;
	}

	private String queryString;
	
	private update_Receiver update_receiver;

	private TextView titleText;

	private boolean firstLoad = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_content_1, container, false);
		activity = (NavigationCooks) getActivity();
		initOtherView();
		initNoShow(show_item_model);
		initReceiver();
		initDatas();
		initListView(list);

		if (cook_List.getCook_List().get(0).getCb_name() == null
				|| cook_List.getCook_List().get(0).getCb_name().length() == 0) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.sendEmptyMessageDelayed(1, 2000);
				}
			}).run();
		}
		return view;
	}

	/**
	 * 根据模式显示/隐藏控件
	 * 
	 * @param show_item_model
	 */
	private void initNoShow(int show_item_model) {
		// TODO Auto-generated method stub
		switch (show_item_model) {
		case 0:
			linearLayout.setVisibility(View.VISIBLE);
			titleText.setVisibility(View.GONE);
			editText_query.setVisibility(View.VISIBLE);
			editText_query.setTextColor(0xffffffff);
			button_changeDatas.setVisibility(View.VISIBLE);
			button_query.setVisibility(View.VISIBLE);
			break;
		case 1:
			titleText.setVisibility(View.VISIBLE);
			editText_query.setVisibility(View.GONE);
			linearLayout.setVisibility(View.GONE);
			button_query.setVisibility(View.GONE);
			button_changeDatas.setVisibility(View.VISIBLE);
			break;
		case 2:
			titleText.setVisibility(View.VISIBLE);
			editText_query.setVisibility(View.GONE);
			linearLayout.setVisibility(View.GONE);
			button_query.setVisibility(View.GONE);
			button_changeDatas.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				loadDatas(show_item_model);
			}
		};
	};

	/**
	 * 初始化顶端布局的控件
	 * 
	 * 
	 */
	private void initOtherView() {
		// TODO Auto-generated method stub
		linearLayout = (LinearLayout) view.findViewById(R.id.ll_navigation);

		linearLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				linearLayout.setFocusable(true);
				linearLayout.setFocusableInTouchMode(true);
				linearLayout.requestFocus();
				return false;
			}
		});

		button_query = (Button) view.findViewById(R.id.button_navigation_query);

		button_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				queryString = editText_query.getText().toString();

				if (queryString.length() == 0 || queryString == null || queryString.length() > 2) {
					Toast.makeText(context, "请输入最多为2个字的中文", Toast.LENGTH_SHORT).show();
				} else {
					loadDatas_0(queryString);
				}

				linearLayout.setFocusable(true);
				linearLayout.setFocusableInTouchMode(true);
				linearLayout.requestFocus();
			}
		});

		editText_query = (EditText) view.findViewById(R.id.edittext_navigation_title);

		titleText = (TextView) view.findViewById(R.id.button_navigation_title);
		titleText.setText(title);
		button_back = (Button) view.findViewById(R.id.button_navigation_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity.finish();
			}
		});

		button_changeDatas = (Button) view.findViewById(R.id.button_navigation_changeDatas);
		button_changeDatas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadDatas(show_item_model);
			}
		});

	}

	private void initReceiver() {
		// TODO Auto-generated method stub
		update_receiver = new update_Receiver();
		IntentFilter intent = new IntentFilter("update_receiver");
		context.registerReceiver(update_receiver, intent);
	}

	private void initListView(List<CookBook> alist) {
		// TODO Auto-generated method stub

		if (listView == null || myAdapter == null) {
			Log.d("update", "initListView - 1");

			listView = (ListView) view.findViewById(R.id.listview_navigation_showcook);
			myAdapter = new MyAdapter(context, alist, R.layout.layout_content_1_listview_item);
			listView.setAdapter(myAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					if (cook_List.getCook_List().get(position).getCb_name() == null
							|| cook_List.getCook_List().get(position).getCb_name().length() == 0) {

					} else {
						Intent intent = new Intent("pager2_change_Receiver");
						intent.putExtra("pos", position);
						context.sendBroadcast(intent);
						activity.goPager(1);
					}
				}
			});

			listView.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					switch (scrollState) {
					case SCROLL_STATE_IDLE:
						// 滚动停止时
						// 加载相应位置的item
						Log.d("Listview", "SCROLL_STATE_IDLE");

						break;
					case SCROLL_STATE_FLING:
						Log.d("Listview", "SCROLL_STATE_FLING");
						break;
					case SCROLL_STATE_TOUCH_SCROLL:
						// 在滚动时
						Log.d("Listview", "SCROLL_STATE_TOUCH_SCROLL");

						break;
					default:
						break;
					}

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					First_Item = firstVisibleItem;
					All_Item = totalItemCount;
					Visit_Item = visibleItemCount;
					End_Item = firstVisibleItem + visibleItemCount - 1;
					Log.d("Listview", firstVisibleItem + "   " + visibleItemCount + "   " + totalItemCount);
				}
			});

			if (firstLoad) {
				if(show_item_model!=2){
					listView.setVisibility(View.GONE);
					firstLoad = false;
				}
				
			}

		} else {

			myAdapter.notifyDataSetChanged();
			Log.d("update", "initListView - 2");

		}

		Log.d("listview", "parent is " + listView.getParent());

	}

	/**
	 * 加载数据
	 * 
	 * 如果没有菜谱信息，则访问服务器获取
	 * 
	 * 如果没有图片，则从缓存中找 缓存中没有，访问服务器获取
	 * 
	 * 只有在进入活动时会自动执行一次 然后点击刷新按钮也会执行
	 * 
	 */
	private void loadDatas(int pos) {
		switch (pos) {
		case 0:
			if(queryString==null || queryString.length()==0){
				Toast.makeText(context, "请输入搜索关键字词！", Toast.LENGTH_SHORT).show();
			}else {
				loadDatas_0(queryString);
			}
			break;
		case 1:
			Toast.makeText(context, "正在获取数据，请稍等...", Toast.LENGTH_SHORT).show();
			loadDatas_1();
			break;
		case 2:

			break;

		default:
			break;
		}
	}

	private void loadDatas_0(String keyCookName) {
		NetworkAccess.getNetworkAccess(context).volleyGet_3(keyCookName);
	}

	private void loadDatas_1() {
		NetworkAccess.getNetworkAccess(context).volleyGet2();
	}

	 private void loadDatas_OK() {
	 for (int i = First_Item; i < End_Item + 1; i++) {
	 if (cook_List.getCook_List().get(i).getCb_name() == null
	 || cook_List.getCook_List().get(i).getCb_name().length() == 0) {
	 Log.d("Datas", i + " is 访问服务器获取数据");
	 // 没有数据时，访问服务器获取数据
	 Toast.makeText(context, "正在获取数据，请稍等...", Toast.LENGTH_SHORT).show();
	
	 } else {
	
	 Log.d("Datas", i + " is 从缓存拿图");
	 Bitmap mBitmap = PictureHelper
	 .getBitmapFromMemCache(cook_List.getCook_List().get(i).getCb_picture_url());
	
	 if(mBitmap == null){
	 PictureHelper.getPictureHelper(context).loadImageByVolley(i,
	 cook_List.getCook_List().get(i).getCb_picture_url(), 0, 0);
	 }
	
	 if (mBitmap == null) {
	 Log.d("Datas", i + " is 缓存有图");
	
	 } else {
	 Log.d("Datas", i + " is 缓存没图");
	 cook_List.getCook_List().get(i).setCb_picture(mBitmap);
	 }
	 }
	
	 }
	 initListView(cook_List.getCook_List());
	 }

	private void initDatas() {
		// TODO Auto-generated method stub
		list = new ArrayList<CookBook>();

		if (show_item_model == 2) {
			list = save_cook_list.getSaveCook_List();
		} else {
			list = cook_List.getCook_List();
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		context.unregisterReceiver(update_receiver);
		
	}

	private class update_Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Log.d("update", "onReceiver");
			listView.setVisibility(View.VISIBLE);
			initListView(cook_List.getCook_List());
			loadDatas_OK();
		}

	}

}
