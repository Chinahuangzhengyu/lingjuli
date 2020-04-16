package com.zhjl.qihao.zq;

import android.util.Log;

import com.google.gson.JsonArray;
import com.zhjl.qihao.util.JSONObjectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * time   :  2018/11/7
 * author :  Z
 * des    :   针对 Retrofit 请求参数 为 Json  一个 简单 省的粘贴代码的 类
 * 使用：   Map<String, String> map = new HashMap<>();
 * map.put("page", String.valueOf(page));
 * map.put("type", String.valueOf(type));
 * RequestBody body = ParamForNet.put(map);
 */

public class ParamForNet {

    public ParamForNet() {
    }


    public static RequestBody put(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        // 遍历集合Map;
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            try {
                json.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.e("ParamForNet ", "检查参数 json = " + json.toString());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());
        return body;
    }


    public static RequestBody putArrayObject(Map<String, Object> map,String key,List<Map<String, Object>> idList) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            // 遍历集合Map;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }

            if (idList.size()!=0){
                for (int i = 0; i < idList.size(); i++) {
                    JSONObject listArray = new JSONObject();
                    // 遍历集合Map;
                    for (Map.Entry<String, Object> entry : idList.get(i).entrySet()) {
                        listArray.put(entry.getKey(), entry.getValue());
                    }
                    array.put(i,listArray);
                }
            }
            json.put(key, array);
            Log.e("ParamForNet ", "检查参数 json = " + json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());
        return body;
    }

    /**
     * 图片上传的请求
     * @param map
     * @param key
     * @return
     */
    public static RequestBody putContainsArray(Map<String, Object> map,String key,List<?> idList) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            // 遍历集合Map;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }

            if (idList.size()!=0){
                for (int i = 0; i < idList.size(); i++) {
                    array.put(i,idList.get(i));
                }
            }
            json.put(key, array);
            Log.e("ParamForNet ", "检查参数 json = " + json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());
        return body;
    }
}
