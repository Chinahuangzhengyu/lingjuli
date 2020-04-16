package com.zhjl.qihao.activity.userlogin;

import java.util.List;

import com.zhjl.qihao.R;
import com.zhjl.qihao.entity.Person;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 

public class ListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Person> list;
	private ViewHolder viewHolder;

	public ListViewAdapter(Context context, List<Person> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		if (list.get(position).getName().length() == 1)// 
			return false;// ��ʾ���ܵ��
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String item = list.get(position).getName();
		viewHolder = new ViewHolder();
		if (item.length() == 1) {
			convertView = LayoutInflater.from(context).inflate(R.layout.index,
					null);
			viewHolder.indexTv = (TextView) convertView
					.findViewById(R.id.indexTv);
		} else {
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					null);
			viewHolder.itemTv = (TextView) convertView
					.findViewById(R.id.itemTv);
		}
		if (item.length() == 1) {
			viewHolder.indexTv.setText(list.get(position).getName());
		} else {
			viewHolder.itemTv.setText(list.get(position).getName());
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView indexTv;
		private TextView itemTv;
	}

}
