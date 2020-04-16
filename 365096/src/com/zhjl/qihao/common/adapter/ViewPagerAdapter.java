package com.zhjl.qihao.common.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter{

	private List<Fragment> list;
	List<String> mTitles;
	public ViewPagerAdapter(FragmentManager fm,List<Fragment> list,List<String> titles) {
		super(fm);
		this.list = list;
		mTitles = titles;
	}
	
	public void updateFragment(List<Fragment> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}
	
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		String title = mTitles.get(position);
		return title;
	}

}
