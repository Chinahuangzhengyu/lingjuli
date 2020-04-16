package com.zhjl.qihao.view;

public class ButtonSingle {

	/**
	  
	 * @description 
	 * @version 1.0
	 * @author 黄南榆
	 * @date 2014-10-18
	 * @param args
	 * @return void
	 * @throws
	
	 */
	private static long lastClickTime;   
	    public static boolean isFastDoubleClick() {   
	        long time = System.currentTimeMillis();   
	        long timeD = time - lastClickTime;   
	        if ( 0 < timeD && timeD < 600) {      
	           return true;      
	        }      
        lastClickTime = time;      
	       return false;      
	    }  

}
