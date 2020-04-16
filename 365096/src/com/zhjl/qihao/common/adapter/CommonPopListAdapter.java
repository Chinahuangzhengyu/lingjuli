package com.zhjl.qihao.common.adapter;

import java.util.List;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abutil.LogUtils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommonPopListAdapter extends BaseAdapter {
	private List<String>  titles;
	private List<String>  numbers;
	private Context mContext;
	private int mTitleColor = 0;
	public CommonPopListAdapter(Context context,List<String>  titles,List<String>  numbers){
		this.titles = titles;
		this.numbers = numbers;
		mContext = context;
	}
	
	public void setTitleColor(int color){
		LogUtils.e("color -- "+color);
		mTitleColor = color;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(titles != null){
			return titles.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(titles != null && titles.size() > position){
			return titles.get(position);
		}
		return position;
		
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_common,null);
			viewHolder = new ViewHolder();
			viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tx_menu_title);
			viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tx_notreadnums);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tv_title.setText(titles.get(position));
		if(mTitleColor != 0){
			LogUtils.e("color -- "+mTitleColor);
			viewHolder.tv_title.setTextColor(mTitleColor);
		}
		
		if(numbers != null && !"0".equals(numbers.get(position)) && !TextUtils.isEmpty(numbers.get(position))){
			viewHolder.tv_number.setVisibility(View.VISIBLE);
			viewHolder.tv_number.setText(numbers.get(position));
		}
		
		
		return convertView;
	}
	
	
	final static class ViewHolder{
		TextView tv_title;
		TextView tv_number;
	}

}
