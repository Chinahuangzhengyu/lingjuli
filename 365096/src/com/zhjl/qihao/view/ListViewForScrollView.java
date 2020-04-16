package com.zhjl.qihao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class ListViewForScrollView extends ListView {

	private int expandSpec;

	public ListViewForScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public ListViewForScrollView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
		        MeasureSpec.AT_MOST);
		        super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
