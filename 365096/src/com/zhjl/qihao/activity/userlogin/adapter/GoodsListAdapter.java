package com.zhjl.qihao.activity.userlogin.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.activity.userlogin.vo.SearchGoodsVo;
import com.zhjl.qihao.abutil.PictureHelper;

public class GoodsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<SearchGoodsVo> goodsList;

    // 构造方法，参数list传递的就是这一组数据的信息
    public GoodsListAdapter(Context context, List<SearchGoodsVo> goodsList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.goodsList = goodsList;
    }

    public List<SearchGoodsVo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<SearchGoodsVo> goodsList) {
        this.goodsList = goodsList;
    }

    // 得到总的数量
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return goodsList == null ? 0 : goodsList.size();
    }

    // 根据ListView位置返回View
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return goodsList.get(position);
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
            convertView = layoutInflater
                    .inflate(R.layout.item_shop_goods, null);
            viewHolder.tv_goods_name = (TextView) convertView
                    .findViewById(R.id.tv_goods_name);
            viewHolder.tv_goods_status = (TextView) convertView
                    .findViewById(R.id.tv_goods_status);
            viewHolder.tv_price_label = (TextView) convertView
                    .findViewById(R.id.tv_price_label);
            viewHolder.tv_cur_price = (TextView) convertView
                    .findViewById(R.id.tv_cur_price);
            viewHolder.tv_market_price = (TextView) convertView
                    .findViewById(R.id.tv_market_price);
            viewHolder.tv_stock = (TextView) convertView
                    .findViewById(R.id.tv_stock);
            viewHolder.img_goods_pic = (RoundedImageView) convertView
                    .findViewById(R.id.img_goods_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            SearchGoodsVo goodsVo = goodsList.get(position);
            viewHolder.tv_goods_name.setText(goodsVo.getGoodsName());
            // 商品有出肖价格 ，级限购价格 ，
            if ("0".equals(goodsVo.getGoodsStatus())) {
                viewHolder.tv_goods_status.setVisibility(View.VISIBLE);
                viewHolder.tv_goods_status.setText("限购");
                viewHolder.tv_price_label.setText("限购价:");
                viewHolder.tv_cur_price
                        .setText(goodsVo.getGoodsRestrictPrice());
            } else if ("1".equals(goodsVo.getGoodsStatus())) {
                viewHolder.tv_goods_status.setVisibility(View.VISIBLE);
                viewHolder.tv_goods_status.setText("促销");
                viewHolder.tv_price_label.setText("促销价:");
                viewHolder.tv_cur_price.setText(goodsVo.getGoodsPromotePrice());
            } else {
                viewHolder.tv_goods_status.setVisibility(View.GONE);
                viewHolder.tv_price_label.setText("现价:");
                viewHolder.tv_cur_price.setText("¥" + goodsVo.getGoodsPrice());
            }
            if (goodsVo.getGoodsMarketPrice() == null || goodsVo.getGoodsMarketPrice().equals("0.00")) {
                viewHolder.tv_market_price.setVisibility(View.GONE);
            } else {
                viewHolder.tv_market_price.setText("原价: ¥" + goodsVo.getGoodsMarketPrice());
                viewHolder.tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            viewHolder.tv_stock.setText("库存: " + goodsVo.getGoodsStock());
            PictureHelper.showPictureWithSquare(context,
                    viewHolder.img_goods_pic, goodsVo.getGoodsPic());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_goods_name;
        TextView tv_goods_status;
        TextView tv_price_label;
        TextView tv_cur_price;
        TextView tv_market_price;
        TextView tv_stock;
        RoundedImageView img_goods_pic;
    }
}
