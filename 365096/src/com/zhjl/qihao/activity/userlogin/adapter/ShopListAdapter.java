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
import com.zhjl.qihao.activity.userlogin.vo.SearchShopVo;
import com.zhjl.qihao.abutil.PictureHelper;

public class ShopListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<SearchShopVo> shopList;

	// 构造方法，参数list传递的就是这一组数据的信息
	public ShopListAdapter(Context context, List<SearchShopVo> shopList) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.shopList = shopList;
	}

	public List<SearchShopVo> getShopList() {
		return shopList;
	}

	public void setShopList(List<SearchShopVo> shopList) {
		this.shopList = shopList;
	}

	// 得到总的数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shopList == null ? 0 : shopList.size();
	}

	// 根据ListView位置返回View
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return shopList.get(position-1);
	}

	// 根据ListView位置得到List中的ID
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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
			viewHolder.tx_store_name = (TextView) convertView
					.findViewById(R.id.tx_store_name);
			viewHolder.tx_time = (TextView) convertView
					.findViewById(R.id.tx_time);
			viewHolder.tx_attention = (TextView) convertView
					.findViewById(R.id.tx_attention);
			viewHolder.tx_main_sell = (TextView) convertView
					.findViewById(R.id.tx_mian_sell);
			viewHolder.tx_adress = (TextView) convertView
					.findViewById(R.id.tx_adress);
			viewHolder.img_repair = (RoundedImageView) convertView
					.findViewById(R.id.img_repair);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			// JSONObject shopObj = shopArray.optJSONObject(position);
			SearchShopVo shopVo = shopList.get(position);
			viewHolder.tx_store_name.setText(shopVo.getShopName());
			viewHolder.tx_time.setText("营业:" + shopVo.getShopTime());
			viewHolder.tx_adress.setText("地址:" + shopVo.getShopAddr());
			viewHolder.tx_main_sell.setText("主营:" + shopVo.getShopMainTypes());
			viewHolder.tx_attention.setText("关注:" + shopVo.getShopFocusNum());
			PictureHelper.showPictureWithSquare(context,
					viewHolder.img_repair, shopVo.getShopLogo());
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
