package com.example.cookbooks.helper;

import java.util.List;

import android.graphics.Bitmap;

public class CookBook {
	private int cb_id;
	private String cb_name; //菜名
	private String cb_ingredients; //主要食材
	private String cb_burden; //配料
	private Bitmap cb_picture; //大图
	private String cb_steps; //步骤
	private int cb_goodnumber; //赞一下的数量
	private int cb_time; //上传时间
	private String cb_picture_url; //大图的网络路径
	private boolean isSave = false;
	
	
	
	public boolean isSave() {
		return isSave;
	}
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	public String getCb_picture_url() {
		return cb_picture_url;
	}
	public void setCb_picture_url(String cb_picture_url) {
		this.cb_picture_url = cb_picture_url;
	}
	public int getCb_goodnumber() {
		return cb_goodnumber;
	}
	public void setCb_goodnumber(int cb_goodnumber) {
		this.cb_goodnumber = cb_goodnumber;
	}
	public int getCb_time() {
		return cb_time;
	}
	public void setCb_time(int cb_time) {
		this.cb_time = cb_time;
	}
	public int getCb_id() {
		return cb_id;
	}
	public void setCb_id(int cb_id) {
		this.cb_id = cb_id;
	}
	public String getCb_name() {
		return cb_name;
	}
	public void setCb_name(String cb_name) {
		this.cb_name = cb_name;
	}
	public String getCb_ingredients() {
		return cb_ingredients;
	}
	public void setCb_ingredients(String cb_ingredients) {
		this.cb_ingredients = cb_ingredients;
	}
	public String getCb_burden() {
		return cb_burden;
	}
	public void setCb_burden(String cb_burden) {
		this.cb_burden = cb_burden;
	}
	
	
	public Bitmap getCb_picture() {
		return cb_picture;
	}
	public void setCb_picture(Bitmap cb_picture) {
		this.cb_picture = cb_picture;
	}
	public String getCb_steps() {
		return cb_steps;
	}
	public void setCb_steps(String cb_steps) {
		this.cb_steps = cb_steps;
	}
	
	
	
}
