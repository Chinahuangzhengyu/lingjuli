package com.zhjl.qihao.view;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abutil.PictureHelper;

public class TtqViewFlipper extends CustomViewFlipper {
	private Context context;

	public TtqViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void setData(List<Map<String, String>> mlist, LayoutInflater inflater) {
		for (int postition = 0; postition < mlist.size(); postition++) {
			View convertView = inflater.inflate(R.layout.adapter_list_layout,
					null);
			TextView tx_ttq_titile = (TextView) convertView
					.findViewById(R.id.tx_ttq_titile);
			TextView tx_ttq_time = (TextView) convertView
					.findViewById(R.id.tx_ttq_time);
			TextView tx_ttq_type = (TextView) convertView
					.findViewById(R.id.tx_ttq_type);
			ImageView img_ttq = (ImageView) convertView
					.findViewById(R.id.img_ttq);
			tx_ttq_titile.setText(URLDecoder.decode(mlist.get(postition)
					.get("title").toString()));
			tx_ttq_time.setText(mlist.get(postition).get("publishTime")
					.toString());
			tx_ttq_type.setText("[邻里圈]"
					+ mlist.get(postition).get("typeDesc").toString());
			PictureHelper.showPictureWithSquare(context,img_ttq,
					mlist.get(postition).get("titlePicPath").toString());
			addView(convertView);
		}
	}
}
