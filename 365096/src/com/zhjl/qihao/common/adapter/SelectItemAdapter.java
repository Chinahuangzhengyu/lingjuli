package com.zhjl.qihao.common.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhjl.qihao.R;

public class SelectItemAdapter extends BaseAdapter {
	public String curArea ;
	JSONArray areaArray ;
	Context mContext ;
	String showKey;
	public SelectItemAdapter(JSONArray areaList,Context context,String showKey,String curArea) {
		this.areaArray = areaList;
		this.mContext = context; 
		this.curArea = curArea;
		this.showKey = showKey;
	}
	@Override
	public int getCount() {
		return areaArray == null?0:areaArray.length();
	}

	@Override
	public Object getItem(int position) {
		return areaArray.optJSONObject(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public String getCurArea() {
		return curArea;
	}
	public void setCurArea(String curArea) {
		this.curArea = curArea;
	}
	
	public JSONArray getAreaArray() {
		return areaArray;
	}
	public void setAreaArray(JSONArray areaArray) {
		this.areaArray = areaArray;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder = null;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_area, null);
			mViewHolder.tv_conent = (TextView)convertView.findViewById(R.id.tv_conent);
			convertView.setTag(mViewHolder);
		} else
		{
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		JSONObject areaObject = areaArray.optJSONObject(position);
		mViewHolder.tv_conent.setText(areaObject.optString(showKey));
		return convertView;
	}
	
	class ViewHolder
	{
		TextView tv_conent;
	}
}
