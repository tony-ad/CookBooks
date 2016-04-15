package com.example.cookbooks.helper;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class cook_List {
	private static cook_List cList;
	private static List<CookBook> acook_List;
	public static List<CookBook> getCook_List(){
		if(cList==null){
			synchronized (cook_List.class) {
				if(cList==null){
					cList=new cook_List();
				}
			}
		}
		if(cList.acook_List==null){
			synchronized (cook_List.class) {
				if(acook_List==null){
					cList.acook_List = new ArrayList<CookBook>();
					for(int i = 0 ; i < 2;i++){
						CookBook cookBook = new CookBook();
						cList.acook_List.add(cookBook);
					}
				}
			}
		}
		return cList.acook_List;
		
	}
	
	
	
}
