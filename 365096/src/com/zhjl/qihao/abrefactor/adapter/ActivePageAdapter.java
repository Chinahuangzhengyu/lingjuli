package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhjl.qihao.abrefactor.db.PublicBenefitActivitiesBean;
import com.zhjl.qihao.abrefactor.model.MainTjShopModel;

import java.util.List;

/**
 * 作者： 黄郑宇
 * 时间： 2018/7/27
 * 类作用：公益活动的适配器
 */

public class ActivePageAdapter extends PagerAdapter {
    private List<View> list;
    private Context context;
    private List<?> list1;
    private List<MainTjShopModel.ShoplistBean> shopList;
    private List<PublicBenefitActivitiesBean> publicActivitiesList;


    public ActivePageAdapter(List<View> list,Context context,List<?> list1) {
        this.list = list;
        this.list1 = list1;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.4f;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = list.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list1.size() > 0 && list1.get(position) != null) {
                    if (list1.get(position) instanceof MainTjShopModel.ShoplistBean) {
                        shopList = (List<MainTjShopModel.ShoplistBean>) list1;
                    } else if (list1.get(position) instanceof PublicBenefitActivitiesBean) {
                        publicActivitiesList = (List<PublicBenefitActivitiesBean>) list1;
                    }
                    Intent intent=new Intent();
                    if (shopList != null && shopList.get(position).getUrl() != null && !shopList.get(position).getUrl().equals("")) {
                        intent.putExtra("noShare",true);
                        intent.putExtra("name", shopList.get(position).getUrl_name());
                        intent.putExtra("url",shopList.get(position).getUrl());
                        intent.putExtra("photo",shopList.get(position).getLogo());
                        intent.putExtra("note",shopList.get(position).getNote());
                    } else if (publicActivitiesList != null && publicActivitiesList.get(position).getUrl() != null && !publicActivitiesList.get(position).getUrl().equals("")) {
                        intent.putExtra("noShare",true);
                        intent.putExtra("name", publicActivitiesList.get(position).getUrl_name());
                        intent.putExtra("url",publicActivitiesList.get(position).getUrl());
                        intent.putExtra("photo",publicActivitiesList.get(position).getLogo());
                        intent.putExtra("note",publicActivitiesList.get(position).getNote());
                    }else {
                        Toast.makeText(context, "加载失败了，请稍后再试！", Toast.LENGTH_SHORT).show();
                    }
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "暂无公益活动！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

}
