package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhjl.qihao.abrefactor.view.NewCoverFlowViewPager;

import java.util.List;

/**
 * 作者： 黄郑宇
 * 时间： 2018/6/8
 * 类作用：更改后的CoverFlowAdapter
 */

public class NewCoverFlowAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    /**
     * 默认缩小的padding值
     */
    public static int sWidthPadding;

    public static int sHeightPadding;
    /**
     * 子元素的集合
     */
    private List<View> mViewList;

    private OnItemClickLintener setItemClickLintener;

    public void setSetItemClickLintener(OnItemClickLintener setItemClickLintener) {
        this.setItemClickLintener = setItemClickLintener;
    }

    /**
     * 上下文对象
     */
    private Context mContext;

    public NewCoverFlowAdapter(List<View> mImageViewList, Context context) {
        this.mViewList = mImageViewList;
        mContext = context;
        // 设置padding值，因为显示的时候是以px为单位

        sWidthPadding = dp2px(10);
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
                if (NewCoverFlowViewPager.lists1.get(position).getUrl()!=null&&!"".equals(NewCoverFlowViewPager.lists1.get(position).getUrl())){
                    setItemClickLintener.item(position);
                    Intent intent=new Intent();
                    intent.putExtra("noShare",true);
                    intent.putExtra("name", NewCoverFlowViewPager.lists1.get(position).getUrl_name());
                    intent.putExtra("url",NewCoverFlowViewPager.lists1.get(position).getUrl());
                    intent.putExtra("photo",NewCoverFlowViewPager.lists1.get(position).getLogo());
                    intent.putExtra("note",NewCoverFlowViewPager.lists1.get(position).getNote());
                    mContext.startActivity(intent);
                }else {
                    Toast.makeText(mContext, "暂无公益活动！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public float getPageWidth(int position) {
        return (float)0.95;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        // 该方法回调ViewPager 的滑动偏移量
//        if (mViewList.size() > 0 && position < mViewList.size()) {
//            //当前手指触摸滑动的页面,从0页滑动到1页 offset越来越大，padding越来越大
//            int outWidthPadding = (int) (positionOffset * sWidthPadding);
//            // 从0滑动到1时，此时position = 0，其应该是缩小的，符合
//            Log.i("info", "重新设置padding++++++-----" + positionOffset + "-----------" + outWidthPadding);
//
//            mViewList.get(position).setPadding(outWidthPadding, 0, outWidthPadding, 0);
//            // position+1 为即将显示的页面，越来越大
//            if (position < mViewList.size() - 1) {
//                int inWidthPadding = (int) ((1 - positionOffset) * sWidthPadding);
//                mViewList.get(position + 1).setPadding(inWidthPadding, 0, inWidthPadding, 0);
//                Log.i("info", "重新设置第二個padding-----" + positionOffset + "-----------" + inWidthPadding);
//            }
//
//        }

    }


    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageSelected(int i) {
    }

    /**
     * dp 转 px
     */
    public int dp2px(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        return px;
    }


    public interface OnItemClickLintener{
        void item(int position);
    }
}
