package com.example.cookbooks.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.R.integer;
import android.R.interpolator;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.TextDirectionHeuristic;
import android.util.Log;
import android.widget.Toast;

/**
 * 访问网络
 * 
 * @author Administrator
 *
 */
public class NetworkAccess {

	/**
	 * 申请的apikey
	 */
	private String CookBook_apikey = "94b556d54d9e48978aeca20449bcef41";
	/**
	 * 访问的服务器的菜谱id
	 */
	private int CookBooks_id = -1;
	/**
	 * 访问服务器的url
	 */
	private String CookBook_Url_1 = "http://apis.haoservice.com/lifeservice/cook/queryid?id=";
	private String CookBook_Url_2 = "&key=" + CookBook_apikey;

	private Context context;

	private Boolean firstLoad=true;
	
	/**
	 * 标签id
	 */
	private int cid = 0;
	/**
	 * 数据返回条数，最大30，默认10
	 */
	private int rn = 10;

	private int pn_PagerNumber;
	
	private static NetworkAccess mNetworkAccess;

	public static NetworkAccess getNetworkAccess(Context context) {
		if (mNetworkAccess == null) {
			synchronized (NetworkAccess.class) {
				if (mNetworkAccess == null) {
					mNetworkAccess = new NetworkAccess();
				}
			}
		}
		mNetworkAccess.context = context;

		return mNetworkAccess;
	}

	/**
	 * 
	 * 根据菜谱id获取单个菜谱信息
	 * 
	 * 使用Get方式请求数据返回StringRequest对象
	 * 
	 * 
	 * 
	 * new StringRequest(int method,String url,Listener listener,ErrorListener
	 * errorListener) method：请求方式，Get请求为Method.GET，Post请求为Method.POST url：请求地址
	 * listener：请求成功后的回调 errorListener：请求失败的回调
	 */
	public void volleyGet(final int pos) {

		CookBooks_id = cookbook_ids.ids[pos];
		String url = CookBook_Url_1 + CookBooks_id + CookBook_Url_2;
		Log.d("网络请求", "根据菜谱id获取单个菜谱信息");

		StringRequest request = new StringRequest(Method.GET, url, new Listener<String>() {
			@Override
			public void onResponse(String s) {// s为请求返回的字符串数据
				Log.d("asd", s);
				analysisJSON(pos, s);

				Intent intent = new Intent("update_receiver");
				intent.putExtra("update_layout", pos);
				context.sendBroadcast(intent);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
		// 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
		request.setTag("Get_Cook");
		// 将请求加入全局队列中
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * 按标签检索菜谱，获取菜谱信息
	 * 
	 * @param cid：1~9
	 * 
	 *            以下的参数， 前面为cid，后面为此cid中 总共有的菜数量为：pn*rn 1，1451 2，1444 3，1466
	 *            4，1409 5，1318 6，1425 7，1324 8，1438 9，570
	 * 
	 *            pn- rn-返回数默认为10个
	 * 
	 */
	public void volleyGet2() {
		cid = ((int) (Math.random()) * 3 + 1);
		int pn = (int) ((Math.random()) * 135 + 1);
		String url = "http://apis.haoservice.com/lifeservice/cook/index?" + "cid=" + cid + "&pn=" + pn + "&rn=" + rn
				+ "&key=94b556d54d9e48978aeca20449bcef41";
		Log.d("测试方式", "volleyGet2-按标签检索菜谱，获取菜谱信息");
		Log.d("cookid", "cid pn rn is " + cid + ";" + pn + ";" + rn);
		StringRequest request = new StringRequest(Method.GET, url, new Listener<String>() {
			@Override
			public void onResponse(String s) {// s为请求返回的字符串数据

				analysisJSON_For2(s,2);

				Intent intent = new Intent("update_receiver");

				context.sendBroadcast(intent);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
		// 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
		request.setTag("Get_Cook");
		// 将请求加入全局队列中
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * 按标签搜索菜谱的JSON解析
	 * 
	 * @param pos
	 * @param s
	 */
	private void analysisJSON_For2(String s,int model) {
		// TODO Auto-generated method stub
		JSONObject mJsonObject;
		try {
			mJsonObject = new JSONObject(s);
			String string = mJsonObject.getString("result");
			if (string == null) {
				Toast.makeText(context, "查询无结果，请输入正确的搜索字词", Toast.LENGTH_SHORT).show();
				if(model==3){
					firstLoad=true;
				}
			} else {
				JSONArray jsonArray = mJsonObject.getJSONArray("result");
				// for (int i = 0; i < jsonArray.length(); i++) {
				for (int i = 0; i < 2; i++) {
					JSONObject JSONList = jsonArray.getJSONObject(i);
					int cb_id = JSONList.getInt("id");

					String cb_name = JSONList.getString("title"); // 菜名

					Log.d("cookid", "id is " + cb_id + ";Name is " + cb_name);

					String cb_ingredients = JSONList.getString("ingredients"); // 主要食材
					String cb_burden = JSONList.getString("burden"); // 配料
					String cb_albums = JSONList.getString("albums"); // 大图

					Log.d("cookid", "setps is " + JSONList.getString("steps"));
					JSONArray jsonArray2 = JSONList.getJSONArray("steps"); // 步骤
					String cb_content = "";
					for (int j = 0; j < jsonArray2.length(); j++) {
						JSONObject JSONList2 = jsonArray2.getJSONObject(j);
						String aString = JSONList2.getString("step");
						Log.d("cookid", "step is + " + aString);
						cb_content = cb_content + "\n" + aString;

					}
					cook_List.getCook_List().get(i).setCb_id(cb_id);
					cook_List.getCook_List().get(i).setCb_picture_url(cb_albums);
					cook_List.getCook_List().get(i).setCb_burden(cb_burden);
					cook_List.getCook_List().get(i).setCb_name(cb_name);
					cook_List.getCook_List().get(i).setCb_ingredients(cb_ingredients);
					cook_List.getCook_List().get(i).setCb_steps(cb_content);
					PictureHelper.getPictureHelper(context).loadImageByVolley(i, cb_albums, 0, 0);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 解析 单个菜谱的 JSON数据
	 */
	private void analysisJSON(int pos, String content) {
		JSONObject mJsonObject;
		try {
			mJsonObject = new JSONObject(content);
			String JSON_result = mJsonObject.getString("result");
			JSONObject mJsonObject2 = new JSONObject(JSON_result);
			int cb_id = mJsonObject2.getInt("id");
			String cb_name = mJsonObject2.getString("title"); // 菜名
			String cb_ingredients = mJsonObject2.getString("ingredients"); // 主要食材
			String cb_burden = mJsonObject2.getString("burden"); // 配料
			String cb_albums = mJsonObject2.getString("albums"); // 大图

			JSONArray jsonArray = mJsonObject2.getJSONArray("steps"); // 步骤
			String cb_content = "";
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject JSONList = jsonArray.getJSONObject(i);
				String aString = JSONList.getString("step");
				cb_content = cb_content + "\n" + aString;
				Log.d("JSONAA", pos + ":" + " i  aString:" + aString);
				Log.d("JSONAA", pos + ":" + " i cb_content:" + cb_content);
			}

			Log.d("asd", "cb_name is " + cb_name);
			cook_List.getCook_List().get(pos).setCb_id(cb_id);
			cook_List.getCook_List().get(pos).setCb_picture_url(cb_albums);
			cook_List.getCook_List().get(pos).setCb_burden(cb_burden);
			cook_List.getCook_List().get(pos).setCb_name(cb_name);
			cook_List.getCook_List().get(pos).setCb_ingredients(cb_ingredients);
			Log.d("JSONAA", pos + ":" + " i lastttttttttttttt cb_content:" + cb_content);
			cook_List.getCook_List().get(pos).setCb_steps(cb_content);
			PictureHelper.getPictureHelper(context).loadImageByVolley(pos, cb_albums, 0, 0);
			Log.d("asd", "list's cb_name is " + cook_List.getCook_List().get(pos).getCb_name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 对传入的中文 - 进行编码转换
	 */
	private String Encoding(String keyCookName) {
		String name = "";
		try {
			name = URLEncoder.encode(keyCookName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * 根据关键字查询相关菜谱
	 * 
	 * @param keyCookName
	 *            - 关键字相关的菜，例如想要找关于鱼的
	 * @param pn_PagerNumber
	 *            - 页码， 1~？
	 * 
	 *            使用例子：NetworkAccess.getNetworkAccess(context).volleyGet_3("鱼",1
	 *            );
	 * 
	 */
	public void volleyGet_3(String keyCookName) {
		
		if (firstLoad) {
			pn_PagerNumber = 1;
			firstLoad=false;
		} else {
			pn_PagerNumber++;
		}

		String bString = "http://apis.haoservice.com/lifeservice/cook/query?" + "menu=" + Encoding(keyCookName) + "&pn="
				+ pn_PagerNumber + "&rn=" + rn + "&key=94b556d54d9e48978aeca20449bcef41";

		Log.d("测试方式", "volleyGet_3 - 传入的url是：" + bString);
		StringRequest request = new StringRequest(Method.GET, bString, new Listener<String>() {
			@Override
			public void onResponse(String s) {// s为请求返回的字符串数据
				// analysisJSON3(s);
				analysisJSON_For2(s,3);
				Intent intent = new Intent("update_receiver");
				context.sendBroadcast(intent);
			}

		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
		// 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
		request.setTag("Get_Cook");
		// 将请求加入全局队列中
		MyApplication.getHttpQueues().add(request);
	}

	private void analysisJSON3(String s) {
		// TODO Auto-generated method stub
		JSONObject mJsonObject;
		try {
			mJsonObject = new JSONObject(s);
			JSONArray jsonArray = mJsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject JSONList = jsonArray.getJSONObject(i);
				int aint1 = JSONList.getInt("id");
				String aString2 = JSONList.getString("title");
				Log.d("测试方式", "analysisJSON3 - int id 是：" + aint1 + "  ; " + "菜名是：" + aString2);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
