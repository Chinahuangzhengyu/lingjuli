package com.zhjl.qihao.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;

public class HeaderBar {
	
	private FrameLayout fl_msg;//消息
	private ImageView iv_notification;
	private TextView tx_notread;
	
	private LinearLayout ll_head_left;//左边
	private ImageView iv_back;//返回
	private TextView tv_left_des;//返回描述
	
	
	
	private LinearLayout ll_title;//标题
	private TextView tv_title;
	private ImageView iv_pulldown;
	//搜索分类
	LinearLayout ll_search_type;
	TextView tv_search_type;
	
	private TextView tv_search_tips;//搜索 
	private ImageView iv_search;
	private LinearLayout ll_search;
	private EditText et_search;
	
	private LinearLayout ll_right;//右边
	private ImageView iv_right;
	private TextView tv_right;

	//购物车
	private TextView tv_cart;
	private ImageView iv_cart;
	private FrameLayout fl_cart;
	private RelativeLayout rl_header_bar;


	/***
	 * 通用  右边无 
	 * @param view
	 * @param title 中间标题
	 * @param des 左边返回 
	 * @param listener 
	 * @return
	 */
	public static HeaderBar createCommomBack(Activity view,String title,String des,OnClickListener listener){
		HeaderBar mHeaderBar = new HeaderBar(view);
		mHeaderBar.setCommonBack(title,des);
		mHeaderBar.setCommonOnClickListener(listener);
		return mHeaderBar;
	}
	
	public static HeaderBar createCommomBack(Activity view,String title,String des,String rightText,OnClickListener listener){
		HeaderBar mHeaderBar = createCommomBack(view, title, des, listener);
		mHeaderBar.setRightText(true, rightText, listener);
		return mHeaderBar;
	}
	
	public static HeaderBar createCommomBack(Activity view,String title,String des,int right_res,OnClickListener listener){
		HeaderBar mHeaderBar = createCommomBack(view, title, des, listener);
		mHeaderBar.setRightImageVisible(true, right_res, listener);
		return mHeaderBar;
	}

	public static HeaderBar createFastDeliveryBack(Activity view,String title,String backdes,int right_res,OnClickListener listener){
		HeaderBar mHeaderBar = createCommomBack(view,title,backdes,listener);
		mHeaderBar.fl_cart.setVisibility(View.VISIBLE);
		mHeaderBar.fl_cart.setOnClickListener(listener);
		return mHeaderBar;
	}
	
	/**主界面*/
	public static HeaderBar createHomeHeaderBar(View view,OnClickListener listener){
		HeaderBar mHeaderBar = new HeaderBar(view);
		mHeaderBar.setHomeVisible();
		mHeaderBar.setHomeOnClickListener(listener);
		return mHeaderBar;
	}
	/**发现*/
	public static HeaderBar createFindHeaderBar(View view){
		HeaderBar mHeaderBar = new HeaderBar(view);
		mHeaderBar.setFindVisible();
		return mHeaderBar;
	}
	/**周边*/
	public static HeaderBar createNearByHeaderBar(View view,OnClickListener listener){
		HeaderBar mHeaderBar = new HeaderBar(view);
		mHeaderBar.setNearByVisible();
		mHeaderBar.setNearbyOnClickListener(listener);
		return mHeaderBar;
	}
	/**个人中心*/
	public static HeaderBar createMyHeaderBar(View view,OnClickListener listener){
		HeaderBar mHeaderBar = new HeaderBar(view);
		mHeaderBar.setMyVisible();
		mHeaderBar.setMyOnClickListener(listener);
		return mHeaderBar;
	}
	
	
	public HeaderBar(View view){
		init(view);
	}
	
	public HeaderBar(Activity activity){
		if(activity != null){
			init(activity.getWindow().getDecorView());
		}
		
	}
	
	private void init(View view){
		rl_header_bar = (RelativeLayout) view.findViewById(R.id.rl_header_bar);
		fl_msg = (FrameLayout) view.findViewById(R.id.fl_msg);
		iv_notification = (ImageView) view.findViewById(R.id.iv_notification);
		tx_notread = (TextView) view.findViewById(R.id.tx_notread);
		
		ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		iv_pulldown = (ImageView) view.findViewById(R.id.img_home_pulldown);
		
		ll_search_type = (LinearLayout) view.findViewById(R.id.ll_search_type);
		tv_search_type = (TextView) view.findViewById(R.id.tv_search_type);
		
		tv_search_tips = (TextView) view.findViewById(R.id.tv_search_tips);
		iv_search = (ImageView) view.findViewById(R.id.iv_search);
		ll_search = (LinearLayout) view.findViewById(R.id.ll_search);
		et_search = (EditText) view.findViewById(R.id.et_search);
		
		ll_right = (LinearLayout) view.findViewById(R.id.ll_right);
		iv_right = (ImageView) view.findViewById(R.id.iv_right);
		tv_right = (TextView) view.findViewById(R.id.tv_right);
		
		ll_head_left = (LinearLayout) view.findViewById(R.id.ll_head_left);
		iv_back = (ImageView) view.findViewById(R.id.iv_back);
		tv_left_des = (TextView) view.findViewById(R.id.tv_left_des);


		tv_cart = (TextView) view.findViewById(R.id.tv_cart);
		iv_cart = (ImageView) view.findViewById(R.id.iv_cart);
		fl_cart = (FrameLayout) view.findViewById(R.id.fl_cart);
	}
	
	/***
	 * 消息
	 * @param isVisible
	 */
	public void setMsgVisible(boolean isShow){
		if(fl_msg != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			fl_msg.setVisibility(visibility);
		}
	}
	/**返回*/
	public void setBackVisible(boolean isShow){
		if(iv_back != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			iv_back.setVisibility(visibility);
		}
	}
	/**标题和下拉*/
	public void setTitleWithPullDownImageView(boolean titleIsShow,String title,boolean pulldownIsVisible){
		if(tv_title != null){
			int visibility = titleIsShow ? View.VISIBLE : View.GONE;
			tv_title.setVisibility(visibility);
			tv_title.setText(title);
		}
		
		if(iv_pulldown != null){
			int visibility = pulldownIsVisible ? View.VISIBLE : View.GONE;
			iv_pulldown.setVisibility(visibility);
		}
	}
	/**标题和下拉显示*/
	public void setTitleWithPullDownShow(OnClickListener listener){
		tv_title.setVisibility(View.VISIBLE);
		iv_pulldown.setVisibility(View.VISIBLE);
		ll_title.setOnClickListener(listener);
	}
	
	/**标题*/
	public void setTitleVisible(boolean isShow,String text){
		if(tv_title != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			tv_title.setVisibility(visibility);
			tv_title.setText(text);
		}
	}
	
	public void setTitle(String text){
		if(tv_title != null){
			tv_title.setText(text);
		}
	}
	
	
	//右边
	public void setRight(boolean isShow,int resId,String text,OnClickListener listener){
		setRightImageVisible(isShow, resId, listener);
		setRightText(isShow, text, listener);
	}
	
	/**右边图片 */
	public void setRightImageVisible(boolean isShow,int resId,OnClickListener listener){
		if(iv_right != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			iv_right.setVisibility(visibility);

			ll_right.setVisibility(visibility);
			if(resId != -1){
				iv_right.setImageResource(resId);
				iv_right.setOnClickListener(listener);
			}
		}
	}
	
	public void setRightImageView(boolean isShow,Drawable drawable,OnClickListener listener){
		if(iv_right != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			iv_right.setVisibility(visibility);
			ll_right.setVisibility(visibility);
			if(drawable != null){
				iv_right.setImageDrawable(drawable);
				iv_right.setOnClickListener(listener);
			}
			
		}
	}
	
	
	
	/**右边标题*/
	public void setRightTextVisible(boolean isShow){
		if(tv_right != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			tv_right.setVisibility(visibility);
			ll_right.setVisibility(visibility);
		}
	}
	
	public void setRightText(boolean isShow,String text){
		if(tv_right != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			tv_right.setVisibility(visibility);
			ll_right.setVisibility(visibility);
			tv_right.setText(text);
		}
	}
	
	public void setRightText(boolean isShow,String text,OnClickListener listener){
		if(tv_right != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			tv_right.setVisibility(visibility);
			ll_right.setVisibility(visibility);
			tv_right.setText(text);
			tv_right.setOnClickListener(listener);
		}
	}
	
	

	/**搜索分类*/
	public void setSearchSort(boolean isShow,OnClickListener listener){
		if(ll_search_type != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			ll_search_type.setVisibility(visibility);
			ll_search_type.setOnClickListener(listener);
			
		}
	}
	
	public void setSearchSortText(String text){
		tv_search_type.setText(text);
	}
	/**搜索*/
	public void setSearchVisible(boolean isShow){
		if(ll_search != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			ll_search.setVisibility(visibility);
		}
	}
	
	
	public void setSearch(boolean isShow,int resId,int bg,String hint,OnClickListener listener){
		if(ll_search != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			ll_search.setVisibility(visibility);
			ll_search.setBackgroundResource(bg);
			iv_search.setImageResource(resId);
			tv_search_tips.setHint(hint);
			tv_search_tips.setVisibility(visibility);
			ll_search.setOnClickListener(listener);
		}
	}
	
	public void setSearch(boolean isShow,String hint,OnClickListener listener){
		if(ll_search != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			ll_search.setVisibility(visibility);
			tv_search_tips.setVisibility(visibility);
			ll_search.setOnClickListener(listener);
			
			if(!TextUtils.isEmpty(hint)){
				tv_search_tips.setHint(hint);
			}
		}
	}
	
	public void setSearchEditView(boolean isShow,String hint){
		if(ll_search != null){
			int visibility = isShow ? View.VISIBLE : View.GONE;
			ll_search.setVisibility(visibility);
			et_search.setVisibility(visibility);
//			ll_search.setOnClickListener(listener);
			
			if(!TextUtils.isEmpty(hint)){
				et_search.setHint(hint);
			}
		}
	}
	
	/**通用返回*/
	public void setCommonBack(String title,String des,OnClickListener listener){
		iv_back.setVisibility(View.VISIBLE);
		tv_left_des.setVisibility(View.VISIBLE);
		tv_title.setVisibility(View.VISIBLE);
		if(!TextUtils.isEmpty(des)){
			tv_left_des.setText(des);
		}
		
		if(!TextUtils.isEmpty(title)){
			tv_title.setText(title);
		}
		ll_head_left.setOnClickListener(listener);
		
	}
	
	public void setCommonBack(String title,String des){
		iv_back.setVisibility(View.VISIBLE);
		tv_left_des.setVisibility(View.VISIBLE);
		tv_title.setVisibility(View.VISIBLE);
		if(!TextUtils.isEmpty(des)){
			tv_left_des.setText(des);
		}
		
		if(!TextUtils.isEmpty(title)){
			tv_title.setText(title);
		}
	}
	
	public void setBack(String des,OnClickListener listener){
		iv_back.setVisibility(View.VISIBLE);
		tv_left_des.setVisibility(View.VISIBLE);
		ll_head_left.setOnClickListener(listener);
		ll_head_left.setVisibility(View.VISIBLE);
		if(!TextUtils.isEmpty(des)){
			tv_left_des.setText(des);
		}
		
	}
	
	/***/
//	public void setCommonBack()
	
	public void setCommonOnClickListener(OnClickListener listener){
		ll_head_left.setOnClickListener(listener);
	}
	
	
	
	
	/**主页*/
	public void setHomeVisible(){
	//	fl_msg.setVisibility(View.VISIBLE);
	//	iv_notification.setVisibility(View.VISIBLE);
		tv_title.setVisibility(View.VISIBLE);
		iv_pulldown.setVisibility(View.VISIBLE);
	//	iv_right.setVisibility(View.VISIBLE);
	//	iv_right.setImageResource(R.drawable.button_call);
	}
	
	public void setHomeOnClickListener(OnClickListener listener){
		fl_msg.setOnClickListener(listener);
		ll_title.setOnClickListener(listener);
		iv_right.setOnClickListener(listener);
	}
	/**发现*/
	public void setFindVisible(){
		tv_title.setVisibility(View.VISIBLE);
		tv_title.setText("发现");
	}
	
	
	/**周边*/
	
	public void setNearByVisible(){
		iv_back.setVisibility(View.VISIBLE);
		iv_back.setImageResource(R.drawable.ic_search);
		tv_title.setVisibility(View.VISIBLE);
		tv_right.setVisibility(View.VISIBLE);
		ll_right.setVisibility(View.VISIBLE);
		tv_title.setText("周边");
		tv_right.setText("分类");
	}
	
	public void setNearbyOnClickListener(OnClickListener listener){
//		iv_back.setOnClickListener(listener);
		tv_right.setOnClickListener(listener);
		ll_head_left.setOnClickListener(listener);
		
	}
	
	/**我的*/
	
	public void setMyVisible(){
		tv_title.setVisibility(View.VISIBLE);
		tv_title.setText("我的");
		iv_right.setVisibility(View.VISIBLE);
		ll_right.setVisibility(View.VISIBLE);
		iv_right.setImageResource(R.drawable.ic_system_set);
	}
	
	public void setMyOnClickListener(OnClickListener listener){
		iv_right.setOnClickListener(listener);
	}
	
	//设置监听
	public void setHeaderLeftListener(OnClickListener listener){
		ll_head_left.setOnClickListener(listener);
	}
	
	public void setRightImageListener(OnClickListener listener){
		iv_right.setOnClickListener(listener);
	}
	
	public void setBackLeftDes(boolean isShow,String des){
		int visibility = isShow ? View.VISIBLE : View.GONE;
		tv_left_des.setVisibility(visibility);
		tv_left_des.setText(des);
	}
	
	
	//设置
	public FrameLayout getFrameLayoutMsg() {
		return fl_msg;
	}
	

	public ImageView getImageViewNotification() {
		return iv_notification;
	}

	public TextView getTextViewNotread() {
		return tx_notread;
	}

	public ImageView getImageViewBack() {
		return iv_back;
	}

	public LinearLayout getLinearLayoutTitle() {
		return ll_title;
	}

	public TextView getTextViewTitle() {
		return tv_title;
	}

	public ImageView getImageViewPulldown() {
		return iv_pulldown;
	}

	public TextView getTextViewSearchTips() {
		return tv_search_tips;
	}

	public ImageView getImageViewSearch() {
		return iv_search;
	}

	public LinearLayout getLinearLaoutSearch() {
		return ll_search;
	}

	public LinearLayout getLinearLayoutRight() {
		return ll_right;
	}

	public ImageView getImageViewRight() {
		return iv_right;
	}

	public TextView getTextViewRight() {
		return tv_right;
	}

	public LinearLayout getHeaderLeft() {
		return ll_head_left;
	}

	public void setHeaderLeft(LinearLayout ll_head_left) {
		this.ll_head_left = ll_head_left;
	}

	public TextView getBackLeftDes() {
		return tv_left_des;
	}

	public void setBackLeftDes(TextView tv_left_des) {
		this.tv_left_des = tv_left_des;
	}

	public EditText getEditSearch() {
		return et_search;
	}

	public void setEditSearch(EditText et_search) {
		this.et_search = et_search;
	}

	public LinearLayout getLl_search_type() {
		return ll_search_type;
	}

	public void setLl_search_type(LinearLayout ll_search_type) {
		this.ll_search_type = ll_search_type;
	}

	public TextView getTv_search_type() {
		return tv_search_type;
	}

	public void setTv_search_type(TextView tv_search_type) {
		this.tv_search_type = tv_search_type;
	}


	public FrameLayout getFl_cart() {
		return fl_cart;
	}

	public void setFl_cart(FrameLayout fl_cart) {
		this.fl_cart = fl_cart;
	}

	public TextView getTv_cart() {
		return tv_cart;
	}

	public void setTv_cart(TextView tv_cart) {
		this.tv_cart = tv_cart;
	}

	public ImageView getIv_cart() {
		return iv_cart;
	}

	public void setIv_cart(ImageView iv_cart) {
		this.iv_cart = iv_cart;
	}
}
