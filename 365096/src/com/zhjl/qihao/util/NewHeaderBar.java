package com.zhjl.qihao.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;

public class NewHeaderBar {

	private FrameLayout fl_msg;//消息
	private LinearLayout ll_head_left;//左边
	private ImageView iv_back;//返回
	private TextView tv_left_des;//返回描述
	private LinearLayout ll_title;//标题
	private TextView tv_title;
	private ImageView iv_pulldown;
	//搜索分类
	LinearLayout ll_search_type;
	TextView tv_search_type;



	private LinearLayout ll_right;//右边
	private ImageView iv_right;
	private TextView tv_right;
	private ImageView iv_share;


	/***
	 * 通用  右边无
	 * @param view
	 * @param title 中间标题
	 * @param des 左边返回
	 * @param listener
	 * @return
	 */
	public static NewHeaderBar createCommomBack(Activity view, String title, OnClickListener listener){
		NewHeaderBar mHeaderBar = new NewHeaderBar(view);
		mHeaderBar.setCommonBack(title);
		mHeaderBar.setCommonOnClickListener(listener);
		return mHeaderBar;
	}

	public static NewHeaderBar createCommomBack(Activity view, String title, String rightText, OnClickListener listener){
		NewHeaderBar mHeaderBar = createCommomBack(view, title,listener);
		mHeaderBar.setRightText(rightText);
		return mHeaderBar;
	}



	public static NewHeaderBar createCommomBack(Activity view, String title,  int right_res, OnClickListener listener){
		NewHeaderBar mHeaderBar = createCommomBack(view, title,listener);
		mHeaderBar.setRightImage();
		return mHeaderBar;
	}

	private void setRightImage() {
		iv_share.setVisibility(View.VISIBLE);
	}

	public NewHeaderBar(Activity activity){
		if(activity != null){
			init(activity.getWindow().getDecorView());
		}
	}
	
	private void init(View view){

		/*fl_msg = (FrameLayout) view.findViewById(R.id.fl_msg);
		ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
		iv_pulldown = (ImageView) view.findViewById(R.id.img_home_pulldown);

		ll_search_type = (LinearLayout) view.findViewById(R.id.ll_search_type);
		tv_search_type = (TextView) view.findViewById(R.id.tv_search_type);

		ll_right = (LinearLayout) view.findViewById(R.id.ll_right);*/

//		iv_right = (ImageView) view.findViewById(R.id.iv_right);
		tv_right = (TextView) view.findViewById(R.id.tv_right);
		iv_share = (ImageView) view.findViewById(R.id.iv_share);

		tv_title = (TextView) view.findViewById(R.id.tv_title);
		ll_head_left = (LinearLayout) view.findViewById(R.id.ll_head_left);
		iv_back = (ImageView) view.findViewById(R.id.iv_back);

	}

	
	public void setTitle(String text){
		if(tv_title != null){
			tv_title.setText(text);
		}
	}
	public void setGoneIvBack(){
		iv_back.setVisibility(View.GONE);
	}

	
	public void setCommonBack(String title){
		iv_back.setVisibility(View.VISIBLE);
		tv_title.setVisibility(View.VISIBLE);
		if(!TextUtils.isEmpty(title)){
			tv_title.setText(title);
		}
	}
	public void setRightText(String rightText){
		tv_right.setVisibility(View.VISIBLE);
		tv_right.setText(rightText);
	}
	public TextView getRightText(){
		return tv_right;
	}
	
	public void setCommonOnClickListener(OnClickListener listener){
		ll_head_left.setOnClickListener(listener);
	}

	public TextView getTextViewTitle() {
		return tv_title;
	}

}
