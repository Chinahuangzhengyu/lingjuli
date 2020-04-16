package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.zhjl.qihao.abrefactor.view.CoverFlowViewPager;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 * 首页左右滑动的模块的PagerAdapter
 */

public class CoverFlowAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    /**
     * 默认缩小的padding值
     */
    public static int sWidthPadding;

    public static int sHeightPadding;
    /**
     * 子元素的集合
     */
    private List<View> mViewList;


    /**
     * 上下文对象
     */
    private Context mContext;

    public CoverFlowAdapter(List<View> mImageViewList, Context context) {
        this.mViewList = mImageViewList;
        mContext = context;
        // 设置padding值，因为显示的时候是以px为单位

        sWidthPadding = dp2px(10);
        sHeightPadding = dp2px(12);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mViewList.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(mContext, LinksActivity.class);
//                intent.putExtra("name", CoverFlowViewPager.lists1.get(position).getUrl_name());
//                intent.putExtra("url",CoverFlowViewPager.lists1.get(position).getUrl());
//                intent.putExtra("photo",CoverFlowViewPager.lists1.get(position).getLogo());
//                intent.putExtra("note",CoverFlowViewPager.lists1.get(position).getNote());
//                mContext.startActivity(intent);
            }
        });
      /* if(mViewList.size()>=3&&position==0){
            view.setPadding(sWidthPadding,sHeightPadding,sWidthPadding,sHeightPadding);
        }*/
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      
        // 该方法回调ViewPager 的滑动偏移量
        if (mViewList.size() > 0 && position < mViewList.size()) {
            //当前手指触摸滑动的页面,从0页滑动到1页 offset越来越大，padding越来越大
            Log.i("info", "重新设置padding" + position);
            Log.i("info", "重新设置padding1111111-----" + positionOffset + "-----------" + positionOffsetPixels);
            int outHeightPadding = (int) (positionOffset * sHeightPadding);
            int outWidthPadding = (int) (positionOffset * sWidthPadding);
            // 从0滑动到1时，此时position = 0，其应该是缩小的，符合
            Log.i("info", "重新设置padding222222------" + outWidthPadding + "-----------" + outHeightPadding);

            mViewList.get(position).setPadding(outWidthPadding, outHeightPadding, outWidthPadding, outHeightPadding);
            Log.i("info", "重新设置padding444444------" + mViewList.get(position).toString());
            // position+1 为即将显示的页面，越来越大
            if (position < mViewList.size() - 1) {
                int inWidthPadding = (int) ((1 - positionOffset) * sWidthPadding);
                int inHeightPadding = (int) ((1 - positionOffset) * sHeightPadding);
                mViewList.get(position + 1).setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding);
                Log.i("info", "重新设置padding333333------" + inWidthPadding + "-----------" + inHeightPadding);
                Log.i("info", "重新设置padding555555------" + mViewList.get(position + 1).toString());
            }

        }

    }


    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i("info", "重新设置padding66666666------" + state);
    }

    @Override
    public void onPageSelected(int i) {
        Log.i("info", "重新设置padding77777777------" + i);

    }

    /**
     * dp 转 px
     *
     * @param dp
     * @return
     */
    public int dp2px(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        return px;
    }

}
