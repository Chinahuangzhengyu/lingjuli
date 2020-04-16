package com.zhjl.qihao.volley;
import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * @description Volley请求管理类 单例
 * @version 1.0
 * @author 黄南榆
 * @date 2014-9-7 
 */
public class VolleyRequestManager {
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	private VolleyRequestManager() {
	}
	
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		//如果 sd卡 挂载状态，可写入文件，则使用SD卡文件缓存，最大缓存为20M
//		if (getSDPath()!=null) {
//			LogUtils.e("VolleyRequestManager getSDPath()!=null");
//			int max_cache_size = 1024 * 1024 *20;
//			File file=new File(getSDPath().getAbsolutePath()+"/zhjlCache");
//			LogUtils.e("VolleyRequestManager getSDPath()!=null 1");
//			mImageLoader = new ImageLoader(mRequestQueue, new DiskBitmapCache(file,max_cache_size));
//			LogUtils.e("VolleyRequestManager getSDPath()!=null 2");
//		}
//		else{  //SD卡不可用，则使用内存缓存，最大缓存为可用内存的八分之一
//			LogUtils.e("VolleyRequestManager init sd null");
//			int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
//					.getMemoryClass();
//			LogUtils.e("VolleyRequestManager init sd null 1");
//			int cacheSize = 1024 * 1024 * memClass / 8;
//			mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
//			LogUtils.e("VolleyRequestManager init sd null 2");
//		}
		
	}
    /***
     * @description 获取请求队列
     * @return RequestQueue
     */
	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}
	/***
	 * @description 把请求加入请求队列
	 * @param request 请求
	 * @param tag  标签
	 */
	public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }
	
	public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);//请求取消所有的消息
    }

	/**
	 * @return 图片加载器
	 */
//	public static ImageLoader getImageLoader() {
//		if (mImageLoader != null) {
//			return mImageLoader;
//		} else {
//			throw new IllegalStateException("ImageLoader not initialized");
//		}
//	}
	private static File getSDPath(){ 
	       File sdDir = null; 
	       boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在 
	       if   (sdCardExist)   
	       {                               
	         sdDir = Environment.getExternalStorageDirectory();//获取跟目录 
	      }   
	       return sdDir; 
	}
}
