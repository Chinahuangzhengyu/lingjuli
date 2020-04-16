package com.zhjl.qihao.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public class UrlChangeUtils {
    //public static final String API_HOST = "http://192.168.1.84";
//    public static final String JAVA_PORT_NUMBER = ":8081";
    public static final String API_HOST = "https://psms.qihaolingjuli.com";
    public static final String JAVA_PORT_NUMBER = "";
    public static final String PHP_API_HOST = "https://mall.qihaolingjuli.com";
    public static final String PHT_PORT_NUMBER = "";

    public static void reject(Object model, boolean isZS) {
        String ZS_API_HOST = "http://psms.qihaolingjuli.com";
        String ZS_PHP_API_HOST = "http://tp.qihaolingjuli.com";

        List<String> allBase = new LinkedList<>();

        Field[] fields = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < fields.length; j++) { // 遍历所有属性
                String name = fields[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = fields[j].getGenericType().toString(); // 获取属性的类型
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名

                    if (judgeContain(name)) {//不处理
                        Field field = fields[j];
                        String value = field.get(null).toString();
                        allBase.add(value);
                    } else {
                        Log.e("text", "" + name);
                        Field field = fields[j];
                        //将字段的访问权限设为true：即去除private修饰符的影响
                        field.setAccessible(true);
//                        /*去除final修饰符的影响，将字段设为可修改的*/
                        Field modifiersField = null;
                        try {
                            if (name.equals("API_HOST")) {
                                modifiersField = UrlChangeUtils.class.getDeclaredField(field.getName());
                                modifiersField.setAccessible(true);
                                if (isZS) {
                                    modifiersField.set(field, "http://psms.qihaolingjuli.com");
                                } else {
                                    modifiersField.set(field, "http://tj.qihaolingjuli.com");
                                }

                            } else if (name.equals("PHP_API_HOST")) {
                                modifiersField = UrlChangeUtils.class.getDeclaredField(field.getName());
                                modifiersField.setAccessible(true);
                                if (isZS) {
                                    modifiersField.set(field, "http://tp.qihaolingjuli.com");
                                } else {
                                    modifiersField.set(field, "http://tp.qihaolingjuli.com");
                                }
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
//                        String value  = field.get(null).toString();
//                        //把字段值设置为自己的值t
//                        if (isZS){
//                            field.set(null, value.replace("http://tj.qihaolingjuli.com","http://psms.qihaolingjuli.com"));
//                            field.set(null,value.replace("http://tp.qihaolingjuli.com","http://tp.qihaolingjuli.com"));
//                        }else {
//                            field.set(null,value.replace("http://tj.qihaolingjuli.com",""));
//                            field.set(null, value.replace("http://tp.qihaolingjuli.com",""));
//                        }
                    }

                }

            }
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.e("text", "" + e.getStackTrace());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("text", "" + e.getStackTrace());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e("text", "" + e.getStackTrace());
        }
    }

    //加入自己例外处理的base的那么
    public static boolean judgeContain(String name) {
        String[] array = new String[]{"JAVA_PORT_NUMBER", "PHT_PORT_NUMBER"};
        for (String dest : array) {
            if (name.contains(dest)) {
                return true;
            }
        }
        return false;
    }
}
