package com.example.cookbooks.layout.content.pager1;

import com.example.cookbooks.R;
import com.example.cookbooks.helper.CookBook;
import com.example.cookbooks.helper.save_cook_list;

import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Loader;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHolder  
{  
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context;
	
	public ViewHolder
	(Context context,ViewGroup parent,int layoutId,int position){
		this.mPosition=position;
		this.context=context;
		this.mViews=new SparseArray<View>();
		mConvertView=LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
	}
	
	/**
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId, ��item�Ĳ���Id
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context,View convertView,
			ViewGroup parent,int layoutId,int position){
		if(convertView==null){
			return new ViewHolder(context, parent, layoutId, position);
		}else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition=position;
			return holder;
		}
				
	}
	
	/**
	 * ͨ��ViewId��ȡ�ؼ�
	 * @param ViewId
	 * @return
	 */
	public <T extends View> T getView(int ViewId){
		View view = mViews.get(ViewId);
		if(view==null){
			view=mConvertView.findViewById(ViewId);
			mViews.put(ViewId, view);
		}
		return (T) view;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	
	/**
	 * 
	 * @param viewId���ؼ���id
	 * @param text����ʾ������
	 * @return
	 */
	public ViewHolder setText(int viewId,String text){
		TextView tView = getView(viewId);
		tView.setText(text);
		return this;
	}
	
	public ViewHolder setImageViewForBitmap(int viewId,Bitmap bitmap){
		ImageView iv = getView(viewId);
		 Log.d("Bitmap", iv.getWidth() + "   " + iv.getHeight());
		iv.setImageBitmap(bitmap);
		return this;
	}
	
	public ViewHolder setSendItemVisit(int viewId,Boolean ViewVISIBLE){
		RelativeLayout rl = getView(viewId);
		if(ViewVISIBLE){
			rl.setVisibility(View.VISIBLE);
		}else {
			rl.setVisibility(View.GONE);
		}
		return this;
	}
	
	public ViewHolder setImageButton(int viewId,final CookBook cook){
		final ImageView imageView = getView(viewId);
		imageView.setImageResource(R.drawable.save_drak);
		
		
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(cook.isSave()){
					//如果返回时true，则表示已经收藏
					//所以要取消收藏，移除收藏栏的菜谱，更换图
					save_cook_list.getSaveCook_List().remove(cook);
					cook.setSave(false);
					imageView.setImageResource(R.drawable.save_drak);
				}else {
					Toast.makeText(context, "已收藏此条食谱！", Toast.LENGTH_SHORT).show();
					save_cook_list.getSaveCook_List().add(
							save_cook_list.getNumber(), cook);
					Log.d("save", "number is "  + save_cook_list.getNumber());
					cook.setSave(true);
					imageView.setImageResource(R.drawable.save_light);
				}
			}
		});
		return this;
	}
	
}  
