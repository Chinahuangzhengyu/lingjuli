/*
 * Copyright (C) 2013 www.418log.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhjl.qihao.util;

import com.zhjl.qihao.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AbViewUtil.
 */
public class AbViewUtil {
	
	/**
	 * 描述：重置AbsListView的高度.
	 * item 的最外层布局要用 RelativeLayout,如果计算的不准，就为RelativeLayout指定一个高度
	 * @param absListView the abs list view
	 * @param lineNumber 每行几个  ListView一行一个item
	 * @param verticalSpace the vertical space
	 */
	public static void setAbsListViewHeight(AbsListView absListView,int lineNumber,int verticalSpace) {
		
		int totalHeight = getAbsListViewHeight(absListView,lineNumber,verticalSpace);
		ViewGroup.LayoutParams params = absListView.getLayoutParams();
		params.height = totalHeight;
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		absListView.setLayoutParams(params);
	}
	
	/**
	 * 描述：获取AbsListView的高度.
	 * @param absListView the abs list view
	 * @param lineNumber 每行几个  ListView一行一个item
	 * @param verticalSpace the vertical space
	 */
	public static int getAbsListViewHeight(AbsListView absListView,int lineNumber,int verticalSpace) {
		int totalHeight = 0;
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
	    absListView.measure(w, h);
	    ListAdapter mListAdapter = absListView.getAdapter();
		if (mListAdapter == null) {
			return totalHeight;
		}
		
		int count = mListAdapter.getCount();
		if(absListView instanceof ListView){
			for (int i = 0; i < count; i++) {
				View listItem = mListAdapter.getView(i, null, absListView);
				listItem.measure(w, h);
				totalHeight += listItem.getMeasuredHeight();
			}
			if (count == 0) {
				totalHeight = verticalSpace;
			} else {
				totalHeight = totalHeight + (((ListView)absListView).getDividerHeight() * (count - 1));
			}
			
		}else if(absListView instanceof GridView){
			int remain = count % lineNumber;
			if(remain>0){
				remain = 1;
			}
			if(mListAdapter.getCount()==0){
				totalHeight = verticalSpace;
			}else{
				View listItem = mListAdapter.getView(0, null, absListView);
				listItem.measure(w, h);
				int line = count/lineNumber + remain;
				totalHeight = line*listItem.getMeasuredHeight()+(line-1)*verticalSpace;
			}
			
		}
		return totalHeight;

	}
	
	/**
	 * 测量这个view，最后通过getMeasuredWidth()获取宽度和高度.
	 *
	 * @param v 要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View v){
		if(v == null){
			return;
		}
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
	    v.measure(w, h);
	}
	
	/**
	 * 描述：根据分辨率获得字体大小.
	 *
	 * @param screenWidth the screen width
	 * @param screenHeight the screen height
	 * @param textSize the text size
	 * @return the int
	 */
	public static int resizeTextSize(int screenWidth,int screenHeight,int textSize){
		float ratio =  1;
		try {
			float ratioWidth = (float)screenWidth / 480; 
			float ratioHeight = (float)screenHeight / 800; 
			ratio = Math.min(ratioWidth, ratioHeight); 
		} catch (Exception e) {
		}
		return Math.round(textSize * ratio);
	}
	

	
	public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   

    
    /**
	 * 关闭 虚拟键盘
	 */
	public static void colseVirtualKeyboard(Activity activity) {
		View view = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	public void showSwitchProperty(Context context){
		Dialog dialog = new Dialog(context);
        
        // setContentView可以设置为一个View也可以简单地指定资源ID
        // LayoutInflater
        // li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // View v=li.inflate(R.layout.dialog_layout, null);
        // dialog.setContentView(v);
        dialog.setContentView(R.layout.dialog_switch_property);

//        dialog.setTitle("Custom Dialog");

        /* 
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         * 
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
        lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        lp.width = 300; // 宽度
        lp.height = 300; // 高度
        lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);

        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
//        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
//        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
//        dialogWindow.setAttributes(p);

        dialog.show();
	}
	
	public static Dialog showCustomDialog(Context mContext, View view,int width,int height)
	{
		// main.xml中的ImageView
		Dialog loadingDialog = new Dialog(mContext, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 不可以用“返回键”?
		loadingDialog.setCanceledOnTouchOutside(false);
		LayoutParams layoutParam = null;
		if(view instanceof LinearLayout){
			layoutParam = new LinearLayout.LayoutParams(width,height);
		}else if(view instanceof RelativeLayout){
			layoutParam = new RelativeLayout.LayoutParams(width,height);
		}else if(view instanceof TableLayout){
			layoutParam = new TableLayout.LayoutParams(width,height);
		}else if(view instanceof ViewGroup){
			layoutParam = new ViewGroup.LayoutParams(width,height);
		}
		loadingDialog.setContentView(view, layoutParam);// 设置布局
		return loadingDialog;
	}
}
