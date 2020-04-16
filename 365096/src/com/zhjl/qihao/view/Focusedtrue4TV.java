package com.zhjl.qihao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class Focusedtrue4TV extends TextView {
	public Focusedtrue4TV(Context context) {
		super(context);
	}

	public Focusedtrue4TV(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Focusedtrue4TV(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}
