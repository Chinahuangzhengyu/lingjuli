package com.zhjl.qihao.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置文件
 * 
 * @author south
 * 
 */
public class PreferencesHelper {

	SharedPreferences mPreferences;

	SharedPreferences.Editor mEditor;

	Context context;

	public static final String CITYNAME = "city";
	public static final String CITYID = "city_id";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String CHANNEL_ID = "channel_id";
	public static final String USER_ID = "user_id";
	public PreferencesHelper(Context c) {
		context = c;
		mPreferences = context.getSharedPreferences(Config.mSetting, 0);
		mEditor = mPreferences.edit();
	}

	/**
	 * 获取全部
	 * 
	 * @return map
	 */
	public Map<String, ?> getAll() {
		return mPreferences.getAll();
	}

	/**
	 * 设置参数
	 * 
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value) {
		mEditor = mPreferences.edit();
		mEditor.putString(key, value);
		mEditor.commit();

	}

	/**
	 * 获取参数
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return mPreferences.getString(key, null);
	}

	/**
	 * 带默认值的获取参数
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getValue(String key, String defaultValue) {
		if (!mPreferences.contains(key)) {
			return defaultValue;
		}
		return mPreferences.getString(key, defaultValue);
	}

	public void remove(String name) {
		mEditor.remove(name);

	}

	public void removeAll() {
		Map<String, ?> map = getAll();

		Set<String> key = map.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String sKey = it.next();
			remove(sKey);
		}

	}

}
