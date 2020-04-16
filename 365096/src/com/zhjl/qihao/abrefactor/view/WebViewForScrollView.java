package com.zhjl.qihao.abrefactor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * 作者： 黄郑宇
 * 时间： 2018/9/14
 * 类作用：vebView和刷新控件冲突
 */
public class WebViewForScrollView extends WebView{
    private ViewGroup viewGroup;
    public WebViewForScrollView(Context context) {
        super(context);
    }

    public WebViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ViewGroup getViewGroup(){
        return viewGroup;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (this.getScrollY()<=0)
                    this.scrollTo(0,1);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);

    }
}
