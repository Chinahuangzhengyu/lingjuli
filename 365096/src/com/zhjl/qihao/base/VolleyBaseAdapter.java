package com.zhjl.qihao.base;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.view.LoadingAlertDialog;
import com.zhjl.qihao.volley.VolleyErrorHelper;
import com.zhjl.qihao.volley.VolleyRequestManager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class VolleyBaseAdapter extends BaseAdapter {
	private LoadingAlertDialog dialog;
	
	
	protected void executeRequest(Request<?> request) {
		request.setRetryPolicy(new DefaultRetryPolicy(10000, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		VolleyRequestManager.addRequest(request, this);
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				String errorInfo = VolleyErrorHelper
						.getMessage(error, ZHJLApplication.getContext());
				showErrortoast(errorInfo);
				dismissdialog();
			}
		};
	}
	
	protected void showErrortoast(Context context) {
		ToastUtils.showToast(context, "网络不给力，请稍后再试！");
	}
	
	protected void showErrortoast(String errorInfo) {
		ToastUtils.showToast(ZHJLApplication.getContext(), errorInfo);
		// ToastUtils.showToast(mContext, "网络不给力，请稍后再试！");
	}

	protected void showErrortoast(Context mContext, String errorInfo) {
		ToastUtils.showToast(mContext, errorInfo);
		// ToastUtils.showToast(mContext, "网络不给力，请稍后再试！");
	}
	
	/**
	 * 显示请求数据的processdialog
	 */
	protected void showprocessdialog(Context context) {
		if (null == dialog) {
			dialog = new LoadingAlertDialog(context);
		}
		dialog.show();
	}

	/**
	 * 取消请求数据的processdialog
	 */
	protected void dismissdialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
