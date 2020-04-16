package com.zhjl.qihao.common.adapter;

import java.util.List;
import java.util.Map;

import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ImageAndTextAdapter extends BaseAdapter {
	
	private List<Map<String,Object>> list;
	private Context mContext;
	private int size = 0;
	public ImageAndTextAdapter(Context context,List<Map<String,Object>> list,int size){
		mContext = context;
		this.list = list;
		this.size = size;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list != null) return list.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(list != null && list.size() > position) return list.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.common_imageview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_common);
			if(size == 0){
				size = -2;
			}
			
			viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_common);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.iv.setLayoutParams(new LayoutParams(size, size));
		viewHolder.iv.setImageResource(Integer.parseInt(list.get(position).get(Constants.ICON)+""));
		viewHolder.tv.setText(list.get(position).get(Constants.TITLE)+"");
	//	viewHolder.iv.setImageResource(resIds.get(position));
		return convertView;
	}
	
	final static class ViewHolder {
		ImageView iv;
		TextView tv;
	}

}
