package com.zhjl.qihao.freshshop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.activity.ShopDetailActivity;
import com.zhjl.qihao.freshshop.activity.ShopDetailActivity2;
import com.zhjl.qihao.freshshop.bean.ShopProductBean;
import com.zhjl.qihao.freshshop.utils.ActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopRecommendAdapter extends RecyclerView.Adapter<ShopRecommendAdapter.ViewHolder> {

    private Activity context;
    SpannableString string = null;
    StrikethroughSpan span = null;
    private List<ShopProductBean> shopProductBeanList;

    public ShopRecommendAdapter(Activity context, List<ShopProductBean> shopProductBeanList) {
        this.context = context;
        this.shopProductBeanList = shopProductBeanList;
    }

    public void addData(List<ShopProductBean> shopProductBeanList) {
        this.shopProductBeanList = shopProductBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_recommend, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        if (shopProductBeanList.get(i).getPromotion_price().equals("0.00")) {
            holder.tvOldPrice.setVisibility(View.GONE);
            holder.tvPrice.setText(shopProductBeanList.get(i).getPrice());
        } else {
            holder.tvPrice.setText(shopProductBeanList.get(i).getPromotion_price());
            holder.tvOldPrice.setText(shopProductBeanList.get(i).getPrice());
            holder.tvOldPrice.setVisibility(View.VISIBLE);
        }
        string = new SpannableString(holder.tvOldPrice.getText().toString().trim());
        span = new StrikethroughSpan();
        string.setSpan(span, 0, holder.tvOldPrice.getText().toString().trim().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tvOldPrice.setText(string);
        if (shopProductBeanList.get(i).getStock()==0){
            holder.tvStock.setText("已售罄");
            holder.tvStock.setTextColor(ContextCompat.getColor(context,R.color.red_font));
        }else {
            holder.tvStock.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.tvStock.setText("库存:"+shopProductBeanList.get(i).getStock());
        }
        holder.tvShopName.setText(shopProductBeanList.get(i).getName());
        PictureHelper.setImageView(context, shopProductBeanList.get(i).getImage(), holder.imgShop, R.drawable.img_loading);
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ShopDetailActivity.class);
                intent.putExtra("id", shopProductBeanList.get(i).getId());
                context.startActivity(intent);
                if (ActivityUtil.activityList.size()>1){
                    ActivityUtil.removeOldActivity();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopProductBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_shop)
        RoundImageView imgShop;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.tv_stock)
        TextView tvStock;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
