package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.bean.RoomBean;

import java.util.List;
//用户拥有的房间列表的Adapter
public class RoomListAdapter extends BaseAdapter {
	private Context mContext;
	private List<RoomBean> list;

	public RoomListAdapter(Context context, List<RoomBean> list) {
		this.mContext = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_roomlist_item, null);
			holder = new ViewHolder();
			holder.room_name = (TextView) convertView
					.findViewById(R.id.room_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.room_name.setText(list.get(position).getSmallCommunityName()+":"+list.get(position).getRoomName());
		return convertView;
	}

	private class ViewHolder {
		TextView room_name;
	}
}
