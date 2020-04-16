package com.zhjl.qihao.view.autoscrollviewpager;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

/**
 * 
 * @author 黄南榆 
 * 图片相关处理类
 *
 */
public class MyAdvImageAdapter extends PagerAdapter {

	private List<ImageView> mImageList;
	private Context mContext;

	public MyAdvImageAdapter(List<ImageView> mImageList, Context context) {
		mContext = context;
		this.mImageList = mImageList;
	}

	public void updateAdvList(List<ImageView> mImageList) {
		this.mImageList = mImageList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if(mImageList.size() ==1){
			return mImageList.size();
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * 复用对象 true 复用view false 复用的是Object
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * 更新list
	 */
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	/**
	 * 销毁对象
	 * 
	 * @param position
	 *            被销毁对象的索引位置
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		/*if (mImageList.size() > 0) {
			container.removeView(mImageList.get(position % mImageList.size()));
		}*/
	}

	/**
	 * 初始化一个对象
	 * 
	 * @param position
	 *            初始化对象的索引位置
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 对ViewPager页号求模取出View列表中要显示的项
		if(mImageList.size() > 0){
			position %= mImageList.size();
			if (position < 0) {
				position = mImageList.size() + position;
			}
			ImageView view = mImageList.get(position);
			// 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
			ViewParent vp = view.getParent();
			if (vp != null) {
				ViewGroup parent = (ViewGroup) vp;
				parent.removeView(view);
			}
            final int finalPosition = position;
            view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});

			container.addView(view);
			return view;
		}
		return null;
	}




}
