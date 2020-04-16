package com.zhjl.qihao.abrefactor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/8/21.
 */

public class PopupListView extends ListView {
    public boolean isOnMeasure = false;
    public PopupListView(Context context) {
        super(context);
    }

    public PopupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        isOnMeasure = true;
       /* int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);*/
       // int width = getMaxWidthOfChildren() + getPaddingLeft() + getPaddingRight();//计算listview的宽度
       // super.onMeasure(MeasureSpec.makeMeasureSpec(600, MeasureSpec.EXACTLY), heightMeasureSpec);//设置listview的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /**
     * 计算item的最大宽度
     *
     * @return
     */
    private int getMaxWidthOfChildren() {
        int maxWidth = 0;
        View view = null;
        int count = getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            view = getAdapter().getView(i, view, this);
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            if (view.getMeasuredWidth() > maxWidth)
                maxWidth = view.getMeasuredWidth();
        }
        return maxWidth;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isOnMeasure=false;
        super.onLayout(changed, l, t, r, b);
    }
}
