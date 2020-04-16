package com.zhjl.qihao.entity;

import java.util.ArrayList;
import java.util.List;
import com.zhjl.qihao.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class OutboxFragmentAdapter  extends BaseAdapter{

	
	public List<Letter> mData;
	public Context mContext;
	public Letter mTitle;
	public OutboxFragmentAdapter(Context context,List<Letter> data ){
		mData = data;
		mContext = context;
	}
	
	public void setTitle(Letter letter){
		mTitle = letter;
	}
	
	public void setData(List<Letter> data ){
		if(mData == null){
			mData = new ArrayList<Letter>();
		}else{
			mData.clear();
		}
		mData.add(mTitle);
		mData.addAll(data);
		
		notifyDataSetChanged();
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mData != null){
			return mData.size();
		}
		
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(mData != null && mData.size() > position){
			return mData.get(position);
		}
		return null;
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
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.mail_box_item, null);
			viewHolder.cb_select = (CheckBox) convertView.findViewById(R.id.cb_select);
			viewHolder.tv_sender = (TextView) convertView.findViewById(R.id.tv_sender);
			viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.tv_sender_timer = (TextView) convertView.findViewById(R.id.tv_send_timer);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(position == 0){//标题
			
		}
		Letter letter = mData.get(position);
//		viewHolder.cb_select.setChecked(checked)
		viewHolder.tv_sender.setText(letter.getSender());
		viewHolder.tv_title.setText(letter.getTitle());
		viewHolder.tv_sender_timer.setText(letter.getSendTimer());
		
		
		
		return convertView;
	}
	
	public static class ViewHolder{
		CheckBox cb_select;
		/**发件人或收件人*/
		TextView tv_sender;
		/**信件标题*/
		TextView tv_title;
		/**发送时间*/
		TextView tv_sender_timer;
	}

}
