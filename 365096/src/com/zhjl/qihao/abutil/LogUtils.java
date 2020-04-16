package com.zhjl.qihao.abutil;

import com.zhjl.qihao.Constants;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/***
 *  日志帮助文件 方便去掉日志打印
 * @author 黄南榆
 *
 */
public class LogUtils{

	//注意打包的时候设置为false
	private static boolean isDebug = false;
	
	public static final String TAG = Constants.APP_TAG;
	
	public static void e(String log){
		if(isDebug){
			Log.e(TAG, log);
		}

	}
	public static void e(String tag,String log){
		if(isDebug){
			Log.e(tag, log);
		}

	}
	
	public static void w(String log){
		if(isDebug){
			Log.w(TAG, log);
		}

	}
	public static void w(String tag,String log){
		if(isDebug){
			Log.w(tag, log);
		}

	}
	
	public static void d(String log){
		if(isDebug){
			Log.d(TAG, log);
		}

	}
	public static void d(String tag,String log){
		if(isDebug){
			Log.d(tag, log);
		}

	}
	
	public static void i(String log){
		if(isDebug){
			Log.i(TAG, log);
		}

	}
	public static void i(String tag,String log){
		if(isDebug){
			Log.i(tag, log);
		}

	}
	
	public static void v(String log){
		if(isDebug){
			Log.v(TAG, log);
		}

	}
	public static void v(String tag,String log){
		if(isDebug){
			Log.v(tag, log);
		}

	}
	public static void exception(Exception e){
		exception(e,1);
	}

	public static void exception(Exception e,int type){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		switch (type){
			case 0:
				LogUtils.v(sw.toString());
				break;
			case 1:
				LogUtils.d(sw.toString());
				break;
			case 2:
				LogUtils.i(sw.toString());
				break;
			case 3:
				LogUtils.w(sw.toString());
				break;
			case 4:
				LogUtils.e(sw.toString());
				break;
		}

	}
}
