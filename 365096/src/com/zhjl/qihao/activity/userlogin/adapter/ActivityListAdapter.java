package com.zhjl.qihao.activity.userlogin.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.activity.userlogin.vo.SearchActivityVo;

public class ActivityListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<SearchActivityVo> activityList;

	// 构造方法，参数list传递的就是这一组数据的信息
	public ActivityListAdapter(Context context, List<SearchActivityVo> activityList) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.activityList = activityList;
	}


	public List<SearchActivityVo> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<SearchActivityVo> activityList) {
		this.activityList = activityList;
	}

	// 得到总的数量
	@Override
	public int getCount() {
		return activityList==null?0:activityList.size();
	}

	// 根据ListView位置返回View
	@Override
	public SearchActivityVo getItem(int position) {
		return activityList.get(position);
	}

	// 根据ListView位置得到List中的ID
	@Override
	public long getItemId(int position) {
		return position;
	}

	// 根据位置得到View对象
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_suround_list,
					null);
			viewHolder.tx_store_name = (TextView) convertView.findViewById(R.id.tx_store_name);
			viewHolder.tx_time = (TextView) convertView.findViewById(R.id.tx_time);
			viewHolder.tx_attention = (TextView) convertView.findViewById(R.id.tx_attention);
			viewHolder.tx_main_sell = (TextView) convertView.findViewById(R.id.tx_mian_sell);
			viewHolder.tx_adress = (TextView) convertView.findViewById(R.id.tx_adress);
			viewHolder.img_repair = (RoundedImageView) convertView.findViewById(R.id.img_repair);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		try {
			SearchActivityVo activityVo = activityList.get(position);
//			viewHolder.tx_store_name.setText(shopObj.optString("company"));
//			viewHolder.tx_time.setText("营业:"+ shopObj.optString("start_time")+ "—" + shopObj.optString("end_time"));
//			viewHolder.tx_adress.setText("地址:"+ shopObj.optString("area"));
//			viewHolder.tx_main_sell.setText("主营:"+ shopObj.optString("main_pro"));
//			viewHolder.tx_attention.setText("关注:"+ shopObj.optString("view_times"));
//			imageLoader.displayImage(shopObj.optString("logo").trim(), viewHolder.img_repair, options);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		TextView tx_store_name;
		TextView tx_time;
		TextView tx_attention;
		TextView tx_main_sell;
		TextView tx_adress;
		RoundedImageView img_repair;
	}
}
