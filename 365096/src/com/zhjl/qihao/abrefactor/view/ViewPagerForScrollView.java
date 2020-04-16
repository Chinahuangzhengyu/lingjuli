package com.zhjl.qihao.abrefactor.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhjl.qihao.freshshop.activity.ShopDetailActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者： 黄郑宇
 * 时间： 2018/8/24
 * 类作用：自定义viewpager用来解决显示不全和切换页面重新设置高度
 */
public class ViewPagerForScrollView extends ViewPager {
    /*private int current;
    *//**
     * 保存position与对于的View
     *//*
    private HashMap<Integer, Integer> maps = new LinkedHashMap<Integer, Integer>();

    public ViewPagerForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerForScrollView(Context context) {
        super(context);
    }

    public int getCurrent() {
        return current;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        // 下面遍历所有child的高度
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            // 采用最大的view的高度
            maps.put(i, h);

        }
        if (getChildCount() > 0) {
            height = getChildAt(current).getMeasuredHeight();
            Log.e("++++++++++++++++++",height+"//////高度"+getChildAt(current).getMeasuredHeight());
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        this.current = current;
        if (maps.size() > current) {

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, maps.get(current));
            } else {
                layoutParams.height = maps.get(current);
                Log.e("++++++++++++++++",layoutParams.height+"高度；；；；"+this.getChildAt(current));
            }
            setLayoutParams(layoutParams);
        }
    }
*/
        private int current;
        private int height = 0;
        /**
         * 保存position与对于的View
         */
        private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

        private boolean scrollble = true;

        public ViewPagerForScrollView(Context context) {
            super(context);
        }

        public ViewPagerForScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (mChildrenViews.size() > current) {
                View child = mChildrenViews.get(current);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = child.getMeasuredHeight();
            }

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        public void resetHeight(int current) {
            this.current = current;
            if (mChildrenViews.size() > current) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
                } else {
                    layoutParams.height = height;
                }
                setLayoutParams(layoutParams);
            }
        }
        /**
         * 保存position与对于的View
         */
        public void setObjectForPosition(View view, int position)
        {
            mChildrenViews.put(position, view);
        }


        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            if (!scrollble) {
                return true;
            }
            return super.onTouchEvent(ev);
        }


        public boolean isScrollble() {
            return scrollble;
        }

        public void setScrollble(boolean scrollble) {
            this.scrollble = scrollble;
        }

}
