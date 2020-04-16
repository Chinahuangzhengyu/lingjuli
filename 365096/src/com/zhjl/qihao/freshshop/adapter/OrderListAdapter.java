package com.zhjl.qihao.freshshop.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pickers.widget.PickerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.bean.ShopOrderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


    private Activity context;
    private List<ShopOrderBean.ProductsBean> products;
    private SpannableString string;
    private StrikethroughSpan span;

    public OrderListAdapter(Activity context, List<ShopOrderBean.ProductsBean> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_shop_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        try {
            if (products.get(i).getPromotion_price().equals("0")) {
                holder.tvOldPrice.setVisibility(View.GONE);
                holder.tvPrice.setText(products.get(i).getPrice());
            } else {
                holder.tvPrice.setText(products.get(i).getPromotion_price() + "");
                holder.tvOldPrice.setVisibility(View.VISIBLE);
                holder.tvOldPrice.setText(products.get(i).getPrice());
            }
            holder.tvName.setText(products.get(i).getName());
            string = new SpannableString(holder.tvOldPrice.getText().toString().trim());
            span = new StrikethroughSpan();
            string.setSpan(span, 0, holder.tvOldPrice.getText().toString().trim().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.tvOldPrice.setText(string);
            holder.tvShopListSum.setText("共" + products.get(i).getNumber() + "件");
            if (products.get(i).getUnit_name() != null && !products.get(i).getUnit_name().equals("")) {
                holder.tvShopType.setVisibility(View.VISIBLE);
                holder.tvShopType.setText(products.get(i).getUnit_name());
            } else {
                holder.tvShopType.setVisibility(View.GONE);
            }
            PictureHelper.setImageView(context, products.get(i).getImage(), holder.imgShop, R.drawable.img_err);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_shop)
        RoundImageView imgShop;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.tv_shop_type)
        TextView tvShopType;
        @BindView(R.id.img_shop_list_reduce)
        ImageView imgShopListReduce;
        @BindView(R.id.tv_shop_list_sum)
        TextView tvShopListSum;
        @BindView(R.id.img_shop_list_add)
        ImageView imgShopListAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
