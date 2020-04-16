package com.zhjl.qihao.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtil {
    static Gson gson = new Gson();

    public static <T> T getBean(String jsonStr, Class<T> className) {
        return gson.fromJson(jsonStr, className);
    }

    public static <T> String toJsonString(T object) {
        return gson.toJson(object);
    }

    /**
     * 把 Json 字符串 转换成 JavaBean
     *
     * @param json
     * @param clazz
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T getBean(String json, Class clazz, Class<T> t) {
        Gson gson = new Gson();
        Type objectType = type(t, clazz);
        return gson.fromJson(json, objectType);
    }


    public static <T> List<T> getArrayList(String json, Class<T> className) {
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(json).getAsJsonArray();
        List<T> list = new ArrayList<T>();
        for (JsonElement obj : array) {
            list.add(gson.fromJson(obj, className));
        }
        return list;
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
