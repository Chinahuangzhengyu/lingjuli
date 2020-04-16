package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者： 黄郑宇
 * 时间： 2018/8/23
 * 类作用：
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"水果", "蔬菜", "团购"};
    private List<Fragment> lists;
    private Context context;

    public MyFragmentAdapter(FragmentManager fm, Context context, List<Fragment> lists) {
        super(fm);
        this.context = context;
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
