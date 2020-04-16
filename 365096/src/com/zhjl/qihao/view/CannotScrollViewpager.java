package com.zhjl.qihao.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CannotScrollViewpager extends ViewPager {

    private boolean count = true;    //大于2个可以滑动

    public CannotScrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 触摸没有反应就可以了
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (count) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (count) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setPagingCount(boolean count) {
        this.count = count;
    }
}
