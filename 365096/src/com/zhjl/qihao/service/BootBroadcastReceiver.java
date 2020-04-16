package com.zhjl.qihao.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
	private int timeperiod = 600 * 1000;

	// 重写onReceive方法
	@Override
	public void onReceive(Context context, Intent intent) {
		// 后边的XXX.class就是要启动的服务
		AlarmManager alarms = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intentToFire = new Intent(context, InformationService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0,
				intentToFire, PendingIntent.FLAG_UPDATE_CURRENT);
		alarms.setRepeating(AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime(), timeperiod, pendingIntent);
		Log.v("TAG", "开机自动服务自动启动.....");

	}
}
