package com.zhjl.qihao.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.entity.FinancialRecords;
import com.zhjl.qihao.entity.InvestmentProgject;
import com.zhjl.qihao.entity.Letter;

/**
 * 
 * @author 黄南榆 
 * 公用类多给后台封装参数
 * 
 */
public class JSONObjectUtil extends JSONObject {
	// 用于封装后台参数
	private static final String KEY = "phoneUserIdRes";

	public JSONObjectUtil(Context context) {
		try {
			this.put(KEY, Session.get(context).getUserId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObjectUtil(String userId) {
		try {
			this.put(KEY, userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObjectUtil() {

	}

	public static HashMap<String, Object> stringToMap(Object obj) {

		try {
			HashMap<String, Object> data = new HashMap<String, Object>();
			JSONObject json = new JSONObject(obj.toString());
			Iterator it = json.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				Object value = json.get(key);
				data.put(key, value);
			}
			return data;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, Object> jsonToMap(String json){
		try {
			return jsonToMap(new JSONObject(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static HashMap<String, Object> jsonToMap(JSONObject json) {
		if (json == null) {
			return null;
		}
		try {
			HashMap<String, Object> data = new HashMap<String, Object>();
			Iterator it = json.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = (String) json.get(key);
				data.put(key, value);
			}
			return data;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String mapToJson(Map<String, String> map) {
		if (map == null) {
			return null;
		}
		JSONObject json = new JSONObject(map);
		return json.toString();
	}

	public static <T> T getBean(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<T>>(){}.getType();
            list = gson.fromJson(jsonString,type );
        } catch (Exception e) {
        	
        }
        return list;
        
    }
	
	public static List<InvestmentProgject> getInvestmentProgject(String jsonString){
		List<InvestmentProgject> list = new ArrayList<InvestmentProgject>();
        try {
//        	LogUtils.E("array = "+jsonString);
            Gson gson = new Gson();
            Type type = new TypeToken<List<InvestmentProgject>>(){}.getType();
            list = gson.fromJson(jsonString, type);
        } catch (Exception e) {
        	e.printStackTrace();
        	LogUtils.e("投资项目 ");
        }
        return list; 
	}
	
	public static List<FinancialRecords> getFinancialRecords(String jsonString){
		List<FinancialRecords> list = new ArrayList<FinancialRecords>();
        try {
//        	LogUtils.E("array = "+jsonString);
            Gson gson = new Gson();
            Type type = new TypeToken<List<FinancialRecords>>(){}.getType();
            list = gson.fromJson(jsonString, type);
        } catch (Exception e) {
        	e.printStackTrace();
        	LogUtils.e("资金记录 ");
        }
        return list; 
	}
	
	public static List<Letter> getLetterRecords(String json){
		List<Letter> list = new ArrayList<Letter>();
        try {
//        	LogUtils.E("array = "+jsonString);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Letter>>(){}.getType();
            list = gson.fromJson(json, type);
        } catch (Exception e) {
        	e.printStackTrace();
        	LogUtils.e("邮箱记录 ");
        }
        return list; 
	}
	
	
	
	
	public static  String listToJson(List<InvestmentProgject> list){
		
		String result = new GsonBuilder().create().toJson(list);
		return result;
		
	}
	
	
	
	public static <T> List<T> getBeans(JSONArray array, Class<T> cls) {
		LogUtils.e("array = "+array.toString());
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<T>>(){}.getType();
            list = gson.fromJson(array.toString(), type);
        } catch (Exception e) {
        	LogUtils.e("解析实体列表出错");
        }
        return list; 
    } 

	public static List<Map<String, Object>> listKeyMaps(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<List<Map<String, Object>>>() {
					}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	
	
	public static  List<Map<String, Object>> jsonArrayToList(String json){

	    try{
	    	return jsonArrayToList(new JSONArray(json));
	       
	    }catch (Exception e){
	      e.printStackTrace();
	      return null;
	    }
	  }
	
	
	public static List<Map<String, Object>> jsonArrayToList(JSONArray jsonArray){
		ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < jsonArray.length();i++){
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			arrayList.add(jsonToMap(jsonObject));
		}
		return arrayList;
	}
	
	public static List<Map<String, Object>> jsonArrayToList(JSONObject json,String key){
		try {
			return jsonArrayToList(json.getJSONArray(key));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean isEmpty(JSONObject json){
		if(json == null) return false;
		if(json.length() <=  0) return false;
		return true;
	}
	
	public static String getJSONValue(String key,JSONObject json){
		try {
			if(json.has(key)){
				return json.getString(key);
			}
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/***
	 * 
	 * @param key
	 * @param json
	 * @return 获取value，不为空则对值去掉空格，再返回
	 */
	public static String getJSONValueTrim(String key,JSONObject json){
		try {
			if(json.has(key)){
				String value =  json.getString(key);
				if(value != null){
					return value.trim();
				}
			}
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
