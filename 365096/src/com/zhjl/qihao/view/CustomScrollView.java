package com.zhjl.qihao.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * 作者： 黄郑宇
 * 时间： 2018/6/7
 * 类作用：自定义scrollview解决回滚问题
 */

public class CustomScrollView extends ScrollView {
    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
        if (child instanceof EditText)
            return true;
        return false;
    }
    /**
     * 解决 由于子控件的大小导致ScrollView滚动到底部的问题
     *
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}
