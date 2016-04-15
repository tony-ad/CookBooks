package com.example.cookbooks.helper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import android.R.interpolator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.Toast;

/**
 * 图片处理帮助类
 * 
 * 功能：
 * 1，缓存：添加、获取、移除、清空 
 * 2,通过Volley加载网络图片
   3.对资源文件进行压缩
 * 
 * @author Administrator
 *
 */
public class PictureHelper {
	private static PictureHelper pictureHelper;
	private static Context context;
	private static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static LruCache<String, Bitmap> mMemoryCache;
    
	public PictureHelper() {
		mMemoryCache = new LruCache<String, Bitmap>(maxMemory) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // TODO Auto-generated method stub
	            // 重写此方法来衡量每张图片的大小，默认返回图片数量。
	            return bitmap.getByteCount() / 1024;
	        }
	    };
	}
	public static PictureHelper getPictureHelper(Context context) {

		if (pictureHelper == null) {
			synchronized (PictureHelper.class) {
				if (pictureHelper == null) {
					pictureHelper = new PictureHelper();
				
				}
			}
		}
		pictureHelper.context=context;
		return pictureHelper;
	}

	/**
	 * 对资源文件进行压缩
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
	

	
	
		
	/**
	 *  通过Volley加载网络图片
	 *
	 *  new ImageRequest(String url,Listener listener,int maxWidth,int maxHeight,Config decodeConfig,ErrorListener errorListener)
	 *  url：请求地址
	 *  listener：请求成功后的回调
	 *  maxWidth、maxHeight：设置图片的最大宽高，如果均设为0则表示按原尺寸显示
	 *  decodeConfig：图片像素的储存方式。Config.RGB_565表示每个像素占2个字节，Config.ARGB_8888表示每个像素占4个字节等。
	 *  errorListener：请求失败的回调
	 */
	public void loadImageByVolley(final int pos ,final String url,int width ,int height) {
	    ImageRequest request = new ImageRequest(
	                        url,
	                        new Listener<Bitmap>() {
	                            @Override
	                            public void onResponse(Bitmap bitmap) {
	                            
	                            	
	                            	
	                            Log.d("Bitmap", bitmap.getWidth() + "   " + bitmap.getHeight());	
	                            	
//	                            Bitmap bitmap2 =  
//	            Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),(int)(bitmap.getHeight()*0.6f));  
//	                            
	                            addBitmapToMemoryCache(url, bitmap);
	                            cook_List.getCook_List().get(pos).setCb_picture(bitmap);	
	                            	
	                            Intent intent = new Intent("update_receiver");
	    	                	intent.putExtra("update_picture_pos", pos);
	    	                	intent.putExtra("updateWhat", 123);
	    	                	context.sendBroadcast(intent);
	                            }
	                        },
	                        width, height, Config.RGB_565,
	                        new ErrorListener() {
	                            @Override
	                            public void onErrorResponse(VolleyError volleyError) {
	                            	Log.d("Volley", "onErrorResponse");
	                            }
	                        });
	
	    //设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
	    
	    request.setTag("Get_Cook");
	    //通过Tag标签取消请求队列中对应的全部请求
	    MyApplication.getHttpQueues().add(request);
	}
	
	/**
	 * 对图片进行压缩
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 添加Bitmap到缓存中
	 * @param key - 缓存的key
	 * @param bitmap - 缓存的Bitmap
	 */
	public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            if (key != null && bitmap != null)
                mMemoryCache.put(key, bitmap);
        }
    }

	/**
	 * 从缓存中获取Bitmap
	 * @param key - 缓存的key
	 * @return
	 */
    public static Bitmap getBitmapFromMemCache(String key) {
        Bitmap bm = mMemoryCache.get(key);
        if (bm != null ) {
            return bm;
        }
        return null;  	
    }

    /**
     * 移除缓存
     * 
     * @param key
     */
    public void removeImageCache(String key) {
    
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        	}
    	
    }
	
    /**
     * 清空缓存
     */
    public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                Log.d("CacheUtils",
                        "mMemoryCache.size() " + mMemoryCache.size());
                mMemoryCache.evictAll();
                Log.d("CacheUtils", "mMemoryCache.size()" + mMemoryCache.size());
            }
        }
    }
    
   
    
    
    
    
 }
