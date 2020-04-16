package com.zhjl.qihao.abrefactor.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.RoundedImageView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.adapter.CoverFlowAdapter;
import com.zhjl.qihao.abrefactor.bean.RecommendLinksBean;
import com.zhjl.qihao.abutil.PictureHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CoverFlowViewPager extends RelativeLayout {
    /**
     * 用于左右滚动
     */
    private final ViewPager mViewPager;
    private final LayoutInflater inflaterService;

    private CoverFlowAdapter mAdapter;

    /**
     * 需要显示的视图集合
     */
    private List<View> mViewList = new ArrayList<>();
     public static List<RecommendLinksBean> lists1;

    public CoverFlowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.ab_conver_flow, this);
        mViewPager = (ViewPager) findViewById(R.id.vp_conver_flow);
        inflaterService = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * 初始化方法
     */
    private void init() {
        // 构造适配器，传入数据源
        mAdapter = new CoverFlowAdapter(mViewList, getContext());
        // 设置选中的回调
        // 设置适配器
        mViewPager.setAdapter(mAdapter);
        // 设置滑动的监听，因为adpter实现了滑动回调的接口，所以这里直接设置adpter
        mViewPager.addOnPageChangeListener(mAdapter);
        //ViewPager限定预加载的页面个数
        mViewPager.setOffscreenPageLimit(5);
        // 设置触摸事件的分发
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 传递给ViewPager 进行滑动处理
                return mViewPager.dispatchTouchEvent(event);

            }
        });

    }

    /**
     * 设置显示的数据，进行一层封装
     *
     * @param lists
     */
    public void setViewList(List<RecommendLinksBean> lists) {
        init();
        if (lists == null) {
            return;
        }
        lists1 = lists;
        mViewList.clear();
        for (RecommendLinksBean view : lists) {

            FrameLayout layout = new FrameLayout(getContext());
            // 设置padding 值，默认缩小
            inflaterService.inflate(R.layout.ab_conver_item, layout);

            //  layout.setLayoutParams(new FrameLayout.LayoutParams(186,162));
           // layout.setBackground(getResources().getDrawable(R.drawable.ab_roomlist_bg,null));

            layout.setPadding(CoverFlowAdapter.sWidthPadding, CoverFlowAdapter.sHeightPadding, CoverFlowAdapter.sWidthPadding, CoverFlowAdapter.sHeightPadding);
            RoundedImageView avatar = (RoundedImageView) layout.findViewById(R.id.iv_conver);
            TextView tv1 = (TextView) layout.findViewById(R.id.tv_conver_title);
            TextView content = (TextView) layout.findViewById(R.id.tv_conver_content);
            tv1.setText(view.getUrl_name());
            content.setText(view.getNote());
           // PictureHelper.showPictureWithSquare(getContext(), avatar, view.getLogo());
            PictureHelper.setPlaceholderImageView(getContext(),view.getLogo(),avatar,R.drawable.square_default_diagram);
//            Glide.with(getContext()).load(view.getLogo()).asBitmap().placeholder(R.drawable.shouye_dianputuijian).error(R.drawable.square_default_diagram).into(avatar);
            mViewList.add(layout);
        }

        if(lists.size()>=3){
            mViewPager.setCurrentItem(1);
          // mViewPager.getAdapter().getItemPosition(0)
        }
        // 刷新数据
        mAdapter.notifyDataSetChanged();

    }

}
