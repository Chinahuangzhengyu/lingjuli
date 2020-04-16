package com.zhjl.qihao.view;

import java.util.List;

import android.os.Parcelable;
import android.view.View;

public class VerticalPagerAdapter extends PagerAdapter {

	public List<View> mListViews;

	public VerticalPagerAdapter(List<View> mListViews) {
		this.mListViews = mListViews;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((VerticalViewPager) arg0).removeView(mListViews.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return mListViews == null ? 0 : mListViews.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((VerticalViewPager) arg0).addView(mListViews.get(arg1), 0);
		return mListViews.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
