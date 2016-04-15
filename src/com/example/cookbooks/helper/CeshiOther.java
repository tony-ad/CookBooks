package com.example.cookbooks.helper;

import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import android.content.Intent;
import android.util.Log;

public class CeshiOther {

	private static CeshiOther ceshiOther;

	public static CeshiOther geto() {
		if (ceshiOther == null) {
			synchronized (CeshiOther.class) {
				if (ceshiOther == null) {
					ceshiOther = new CeshiOther();
				}
			}
		}
		return ceshiOther;
	}

	public void volleyGet(final int pos) {
		String aString = "";
		if (pos == 0) {
			aString = "http://apis.haoservice.com/lifeservice/cook/index?cid=1&pn=1&rn=10&key=94b556d54d9e48978aeca20449bcef41";
		} else if (pos == 5) {
			aString = "http://apis.haoservice.com/lifeservice/cook/query?menu=番茄&pn=3&rn=10&key=94b556d54d9e48978aeca20449bcef41";
			try {
				aString = URLDecoder.decode(aString,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.d("测试方式", "传入的url是：" + aString);
		StringRequest request = new StringRequest(Method.GET, aString, new Listener<String>() {
			@Override
			public void onResponse(String s) {// s为请求返回的字符串数据
				analysisJSON(pos, s);
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

	private void analysisJSON(int pos, String s) {
		// TODO Auto-generated method stub
		JSONObject mJsonObject;
		try {
			mJsonObject = new JSONObject(s);
			JSONArray jsonArray = mJsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject JSONList = jsonArray.getJSONObject(i);
				int aint1 = JSONList.getInt("id");
				String aString2 = JSONList.getString("title");
				Log.d("测试方式", "int id 是：" + aint1 +"  ; " +"菜名是：" + aString2);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
