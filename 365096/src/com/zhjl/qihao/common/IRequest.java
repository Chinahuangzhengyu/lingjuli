package com.zhjl.qihao.common;
import org.json.JSONObject;

import com.zhjl.qihao.abcommon.RequestListener;
import com.zhjl.qihao.util.JSONObjectUtil;

import android.content.Context;


public class IRequest {

	
	/**
	 * 返回String get
	 * 
	 * @param context
	 * @param url
	 * @param l
	 */
	public static  void get(Context context, String url,int action, RequestListener<JSONObject> l) {
		RequestManager.get(url, context,action, l);
	}
	
	
	/**
	 * 返回对象 get
	 * 
	 * @param context
	 * @param url
	 * @param classOfT
	 * @param l
	 * @param <T>
	 */
//	public static <T> void get(Context context, String url, Class<T> classOfT,
//			RequestListener<T> l) {
//		RequestManager.get(url, context, classOfT, null, false, l);
//	}

	/**
	 * 返回String post
	 *
	 * @param context
	 * @param url
	 * @param //params
	 * @param l
	 */
	public static void post(Context context, String url, JSONObjectUtil json,int action, RequestListener<JSONObject> l) {
		RequestManager.post(url, context, json,action, l);
	}


	/**
	 * 返回String post
	 *
	 * @param context
	 * @param url
	 * @param params
	 * @param l
	 */
	/*public static void post1(Context context, String url, JSONObjectUtil json,int action,
							RequestListener<JSONArray> l) {
		RequestManager.post(url, context, json,action, l);
	}*/


	/**
	 * 返回对象 post
	 * 
	 * @param context
	 * @param url
	 * @param classOfT
	 * @param params
	 * @param l
	 */
//	public static <T> void post(Context context, String url, Class<T> classOfT,
//			JSONObjectUtil jsonObject, RequestListener<T> l) {
//		RequestManager.post(url, context, classOfT, jsonObject, null, false, l);
//	}


}
