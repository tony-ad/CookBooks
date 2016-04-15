package com.example.cookbooks.layout.content.pager1;

import java.util.List;

import com.example.cookbooks.R.layout;
import com.example.cookbooks.helper.CookBook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;  
  
public abstract class AllAdapter<T> extends BaseAdapter  
{  
    protected LayoutInflater mInflater;  
    protected Context mContext;  
    protected List<T> mDatas;  
    protected int mlayoutId;
    
    public AllAdapter(Context context, List<T> datas, int layoutId)  
    {  
    	this.mContext = context;
		this.mDatas = datas;
		this.mlayoutId = layoutId;
		mInflater = LayoutInflater.from(context);;  
    }  
  
    @Override  
    public int getCount()  
    {  
        return mDatas.size();  
    }  
  
    @Override  
    public T getItem(int position)  
    {  
        return mDatas.get(position);  
    }  
  
  
    public long getItemId(int position)  
    {  
        return position;  
    }   
  
  
    public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mlayoutId, position);
		
		convert(holder, getItem(position));
		
		return holder.getConvertView();
	}
	
	public abstract void convert(ViewHolder holder, T t);
    
  
    public void changeDatas(List<T> list){
    	Log.d("update", "changeDatas - 2");
    	this.mDatas = list;
    	this.notifyDataSetChanged();	
    	Log.d("update", "changeDatas - 3");
    }
    
} 
