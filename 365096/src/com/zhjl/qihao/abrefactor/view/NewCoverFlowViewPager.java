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

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.adapter.NewCoverFlowAdapter;
import com.zhjl.qihao.abrefactor.model.MainTjShopModel;
import com.zhjl.qihao.abutil.PictureHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 黄郑宇
 * 时间： 2018/6/8
 * 类作用：更改后的CoverFlowViewPager
 */

public class NewCoverFlowViewPager extends RelativeLayout implements NewCoverFlowAdapter.OnItemClickLintener {
    /**
     * 用于左右滚动
     */
    private final ViewPager mViewPager;
    private final LayoutInflater inflaterService;

    private NewCoverFlowAdapter mAdapter;
    private int position;
    /**
     * 需要显示的视图集合
     */
    private List<View> mViewList = new ArrayList<>();
     public static ArrayList<MainTjShopModel.ShoplistBean> lists1;

    public NewCoverFlowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.ab_new_conver_flow, this);
        mViewPager = (ViewPager) findViewById(R.id.vp_conver_flow);
        inflaterService = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * 初始化方法
     */
    private void init() {
        // 构造适配器，传入数据源
        mAdapter = new NewCoverFlowAdapter(mViewList, getContext());
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
        mAdapter.setSetItemClickLintener(this);
    }

    /**
     * 设置显示的数据，进行一层封装
     *
     * @param lists
     */
    public void setViewList(ArrayList<MainTjShopModel.ShoplistBean> lists) {
        init();
        if (lists == null) {
            return;
        }
        lists1 = lists;
        mViewList.clear();
        for (int i = 0; i < lists.size(); i++) {

            FrameLayout layout = new FrameLayout(getContext());
            // 设置padding 值，默认缩小
            inflaterService.inflate(R.layout.ab_new_conver_item, layout);
            TextView tv1 = (TextView) layout.findViewById(R.id.tv_conver_title);
            TextView content = (TextView) layout.findViewById(R.id.tv_conver_content);
            tv1.setText(lists.get(i).getUrl_name());
            content.setText(lists.get(i).getNote());
//            Glide.with(getContext()).load(lists.get(i).getLogo()).asBitmap().placeholder(R.drawable.shouye_dianputuijian).error(R.drawable.square_default_diagram).into(avatar);
            mViewList.add(layout);
        }

        if (lists1.size()!=0){
            mViewPager.setCurrentItem(position);
        }
        // 刷新数据
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void item(int position) {
        this.position=position;
    }
}
