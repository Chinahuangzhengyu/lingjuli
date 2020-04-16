package com.zhjl.qihao.mymessage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    String tabTitles[] = new String[]{"平台消息", "物业消息"};
    private List<Fragment> mFramentList;

    public FragmentAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFramentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFramentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return mFramentList.size();
    }
}
