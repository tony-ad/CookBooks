package com.example.cookbooks.layout.content.pager1;

import java.util.List;

import com.example.cookbooks.R;
import com.example.cookbooks.helper.CookBook;

import android.content.Context;
import android.drm.DrmStore.RightsStatus;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
  
public class MyAdapter extends AllAdapter<CookBook>{  

    public MyAdapter(Context context, List<CookBook> datas, int layoutId)
	{
		super(context, datas, layoutId);
	}
  
    
	public void convert(ViewHolder vHolder, CookBook t) {
		// TODO Auto-generated method stub
		vHolder.setText(R.id.cb_title, t.getCb_name());
		vHolder.setImageViewForBitmap(R.id.cb_picture, t.getCb_picture());
//		vHolder.setSendItemVisit(R.id.item_send_layout, true);
//		vHolder.setImageButton(R.id.cb_save,t);
	}

}  
