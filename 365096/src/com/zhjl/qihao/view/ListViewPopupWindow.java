package com.zhjl.qihao.view;
import com.zhjl.qihao.R;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class ListViewPopupWindow  {
	private  PopupWindow pop;
	private  ListView list;
	private ImageView iv_triangle;
	private LinearLayout ll_content;

	public ListViewPopupWindow(Context context,BaseAdapter adapter,int width,int height){
		if(pop == null){
			View view = LayoutInflater.from(context).inflate(R.layout.sweetpopwindow, null);
			list = (ListView) view.findViewById(R.id.poplist);
			iv_triangle = (ImageView) view.findViewById(R.id.iv_triangle);
			ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
			list.setAdapter(adapter);
			pop =  new PopupWindow(view,width,height, false);
			// 需要设置一下此参数，点击外边可消失
			pop.setBackgroundDrawable(new ColorDrawable(
					context.getResources().getColor(android.R.color.transparent)));
			// 设置点击窗口外边窗口消失
			pop.setOutsideTouchable(true);
			// 设置此参数获得焦点，否则无法点击
			pop.setFocusable(true);
			
			
		}
		
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		list.setOnItemClickListener(listener);
	}
	
	public void setListViewBackground(int resid){
		ll_content.setBackgroundResource(resid);
	}
	
	public void setAdapter(BaseAdapter adapter){
		list.setAdapter(adapter);
	}
	
	public void setTriangleShow(boolean isShow){
		if(iv_triangle != null){
			iv_triangle.setVisibility(isShow?View.VISIBLE:View.GONE);
		}
	}
	
	public void show(View view,int xoff,int yoff){
		if(pop != null){
			pop.showAsDropDown(view, xoff, yoff);
		}
	}
	
	public void dismiss(){
		pop.dismiss();
		
		
	}
	
	public void setOnDismissListener(OnDismissListener  onDismissListener){
		pop.setOnDismissListener(onDismissListener);
	}
	
	
	
	
	
}
