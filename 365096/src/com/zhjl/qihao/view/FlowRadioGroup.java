package com.zhjl.qihao.view;

import com.zhjl.qihao.util.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

public class FlowRadioGroup extends RadioGroup {

    Context context ;
    public FlowRadioGroup(Context context) {
        super(context);
        this.context =context;
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;
        int rightMargin = Utils.dip2px(context, 6);
        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                LayoutParams layoutParams =  (RadioGroup.LayoutParams)child.getLayoutParams();
                layoutParams.rightMargin = 20;
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                child.setLayoutParams(layoutParams);
                x += width;
                y = row * height + height;
                y=y+rightMargin*row;
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * height + height+rightMargin*row;
                }
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        int rightMargin = Utils.dip2px(context, 6);
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width +rightMargin;
                y = row * height + height;
                y=y+rightMargin*row;
                if (x > maxWidth) {
                    x = width+rightMargin;;
                    row++;
                    y = row * height + height +rightMargin*row;
                }
          
                child.layout(x - width, y - height, x, y);
                
            }
        }
    }
}