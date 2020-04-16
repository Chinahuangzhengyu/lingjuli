/**
 * 文件名：
 * 描     述：
 * 作     者：伦毅均
 * 版     权：聚晖电子
 */
package com.zhjl.qihao.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zhjl.qihao.HandleCode;

/**
 * @author 黄南榆
 * @function 
 * @date 2014-7-17
 */
public class SmsObserver extends ContentObserver{

	private Context context;
	
	private Handler handler;
	
	private Uri SMS_INBOX = Uri.parse("content://sms/");
	
	/**
	 * @param handler
	 */
	public SmsObserver(Context context,Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
		this.handler = handler;
	    this.context = context;
	}
	
    @Override  
    public void onChange(boolean selfChange) {  
        super.onChange(selfChange);  
        //每当有新短信到来时，使用我们获取短消息的方法  
        getSmsFromPhone();  
    }  
    
    public void getSmsFromPhone() {  
        ContentResolver cr = context.getContentResolver();  
        String[] projection = new String[] { "body" };//"_id", "address", "person",, "date", "type  
        String where = " date >  "  
                + (System.currentTimeMillis() - 10 * 60 * 1000);  
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");  
        if (null == cur)  
            return;  
        if (cur.moveToNext()) { 
            //String number = cur.getString(cur.getColumnIndex("address"));//手机号  
           // String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表  
            String body = cur.getString(cur.getColumnIndex("body"));  
            //这里我是要获取自己短信服务号码中的验证码~~  
            Pattern pattern = Pattern.compile("验证码:[0-9]{6}");  
            Matcher matcher = pattern.matcher(body);  
            if (matcher.find()) {  
                String res = matcher.group().substring(4, 10);  
                Log.i("sms_yanzheng:", res);
                
                Message msg = handler.obtainMessage();
	           	msg.arg1 = HandleCode.SMSVERIFICATIONCODE;
	           	msg.obj = res;
	           	msg.sendToTarget();  	           	
            }  
        }  
    } 

}
