package com.example.cookbooks.helper;

import java.util.ArrayList;
import java.util.List;

public class save_cook_list {
	private static save_cook_list cList;
	private static List<CookBook> acook_List;
	private static int save_Number=0;
	public static List<CookBook> getSaveCook_List(){
		if(cList==null){
			synchronized (cook_List.class) {
				if(cList==null){
					cList=new save_cook_list();
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
	
	public static int getNumber(){	
		int i = save_Number;
		save_Number = save_Number + 1;
		return i;
	}
	
}
