/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.zhjl.qihao.pay;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.app.PayTask;
import com.zhjl.qihao.abutil.LogUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class MobileSecurePayer
{
	static String TAG = "MobileSecurePayer";

	Integer lock = 0;
	IAlixPay mAlixPay = null;
	boolean mbPaying = false;
	Activity mActivity = null;
	private ServiceConnection mAlixPayConnection = new ServiceConnection() 
	{
		@Override
		public void onServiceConnected(ComponentName className, IBinder service)
		{
		 
		    // wake up the binder to continue.
		    synchronized( lock )
		    {	
		    	mAlixPay 	= IAlixPay.Stub.asInterface(service);
		    	lock.notify();
		    }
		}
	
		@Override
		public void onServiceDisconnected(ComponentName className)
		{
			mAlixPay	= null;
		}
	};
	
	public boolean pay(final String strOrderInfo, final Handler callback,
			final int myWhat, final Activity activity)
	{
		LogUtils.d("pay");
		if( mbPaying )
			return false;
		mbPaying = true;
		
		//
		mActivity = activity;
		
		// bind the service.
//		if (mAlixPay == null)
//		{
//			mActivity.getApplicationContext().bindService(new Intent(IAlixPay.class.getName()), mAlixPayConnection, Context.BIND_AUTO_CREATE);
//		}
		//else ok.
		
		LogUtils.d("new Thread");
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{

//					// 构造PayTask 对象
					PayTask alipay = new PayTask(activity);
//					// 调用支付接口，获取支付结果
					Map<String,String> result = alipay.payV2(strOrderInfo, true);
					mbPaying = false;
					// send the result back to caller.
					Message msg = callback.obtainMessage(myWhat,result);
					
					LogUtils.d("pay MobileSecurePayer  "+result);

					callback.sendMessage(msg);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					
					// send the result back to caller.
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					LogUtils.d("pay MobileSecurePayer  error is "+sw.toString());
					pw.close();
					try {
						sw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Message msg = callback.obtainMessage(myWhat,e.toString());
					callback.sendMessage(msg);
				}
			}
		}).start();
		
		return true;
	}
	
	 /**
	 * This implementation is used to receive callbacks from the remote
	 * service.
	 */
	private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub()
	{
		/**
		 * This is called by the remote service regularly to tell us
		 * about new values. Note that IPC calls are dispatched through
		 * a thread pool running in each process, so the code executing
		 * here will NOT be running in our main thread like most other
		 * things -- so, to update the UI, we need to use a Handler to
		 * hop over there.
		 */
		@Override
		public void startActivity(String packageName, String className, int iCallingPid, Bundle bundle)
				throws RemoteException
		{
			Intent intent	= new Intent(Intent.ACTION_MAIN, null);
			
			if( bundle == null )
				bundle = new Bundle();
			// else ok.
			
			try
			{
				bundle.putInt("CallingPid", iCallingPid);
				intent.putExtras(bundle);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			intent.setClassName(packageName, className);
			mActivity.startActivity(intent);
		}

		@Override
		public boolean isHideLoadingScreen() throws RemoteException {
			return false;
		}

		@Override
		public void payEnd(boolean arg0, String arg1) throws RemoteException {
			
		}
	};
}