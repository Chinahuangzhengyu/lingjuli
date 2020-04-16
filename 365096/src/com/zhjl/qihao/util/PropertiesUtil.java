package com.zhjl.qihao.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties loadConfig(String file) {  
		Properties properties = new Properties();  
		try {  
			FileInputStream s = new FileInputStream(file);  
			properties.load(s);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return properties;  
	}  
	public static Properties loadConfig(InputStream file) {  
		Properties properties = new Properties();  
		try {  
			properties.load(file);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return properties;  
	}  

	public static void saveConfig(String file, Properties properties) {  
		try {  
			FileOutputStream s = new FileOutputStream(file, false);  
			properties.store(s, "");  
		} catch (Exception e){  
			e.printStackTrace();  
		}  
	}  
}
