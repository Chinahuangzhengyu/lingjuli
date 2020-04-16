package com.zhjl.qihao.abproperty.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.zhjl.qihao.R;

import java.util.List;
import java.util.Map;

/**
 * 作者： 黄郑宇
 * 时间： 2018/4/28
 * 类作用：服务类型的适配器
 */

public class ServiceTypeAdapter extends BaseAdapter{

    private Context context;
    private List<Map<String, String>> list;

    public ServiceTypeAdapter(Context context,List<Map<String, String>> mProperyList) {
        list=mProperyList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size()!=0?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder") @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(
                R.layout.gv_service_item, null);

        final RadioButton ck = (RadioButton) convertView.findViewById(R.id.ck_status);
        ck.setText(list.get(position).get("costName"));
        ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck.setChecked(true);
            }
        });
        return convertView;
    }

}
