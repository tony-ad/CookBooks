package com.example.cookbooks.layout.content;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
public class MyPager extends ViewPager {

	public MyPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d("viewpager", "pager - dispatchTouchEvent - ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d("viewpager", "pager - dispatchTouchEvent - ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.d("viewpager", "pager - dispatchTouchEvent - ACTION_UP");
			break;

		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d("viewpager", "pager - onInterceptTouchEvent - ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d("viewpager", "pager - onInterceptTouchEvent - ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.d("viewpager", "pager - onInterceptTouchEvent - ACTION_UP");
			break;

		default:
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d("viewpager", "pager - onTouchEvent - ACTION_DOWN");			
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d("viewpager", "pager - onTouchEvent - ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.d("viewpager", "pager - onTouchEvent - ACTION_UP");
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
}
