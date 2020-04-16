package com.zhjl.qihao.abrefactor.bean;

import com.zhjl.qihao.abrefactor.view.ViewPagerForScrollView;

/**
 * 作者： 黄郑宇
 * 时间： 2018/9/13
 * 类作用：用来传递viewpager对象
 */
public class ViewPagerBean{
    private ViewPagerForScrollView view;

    public ViewPagerForScrollView getView() {
        return view;
    }

    public ViewPagerBean(ViewPagerForScrollView view) {
        this.view = view;
    }
}
