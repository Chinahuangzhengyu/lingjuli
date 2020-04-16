package com.zhjl.qihao.systemsetting.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;

public class WatchTutorialActivity extends VolleyBaseActivity {
	private ViewPager advPager;
	private List<View> advPics;
	private int[] guidepages = { R.drawable.daojia_cpt };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup_viewflipper);
		initViewPager();
	}

	private void initViewPager() {
		advPager = (ViewPager) findViewById(R.id.adv_pager);
		advPics = new ArrayList<View>();
		for (int i = 0; i < guidepages.length; i++) {
			ImageView img = new ImageView(mContext);
			img.setScaleType(ScaleType.CENTER_CROP);
			advPics.add(img);
		}
		advPager.setAdapter(new AdvAdapter(advPics));
		advPager.setOnPageChangeListener(new GuidePageChangeListener());
	}

	boolean misScrolled = false;

	private final class GuidePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				misScrolled = false;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				misScrolled = true;
				break;
			case ViewPager.SCROLL_STATE_IDLE:

				if (advPager.getCurrentItem() == advPager.getAdapter()
						.getCount() - 1 && !misScrolled) {
					finish();
				}
				misScrolled = true;
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		advPics.clear();
		advPics = null;
		guidepages = null;
		advPager = null;
	}

	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return views == null ? 0 : views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			ImageView img = (ImageView) views.get(arg1);
			img.setImageResource(guidepages[arg1]);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
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
}
