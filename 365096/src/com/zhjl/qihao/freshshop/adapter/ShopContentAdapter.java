package com.zhjl.qihao.freshshop.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.bean.ShopListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopContentAdapter extends RecyclerView.Adapter<ShopContentAdapter.ViewHolder> {

    private Activity context;
    private SpannableString string;
    private StrikethroughSpan span;
    private List<ShopListBean.DataBean> data;

    public ShopContentAdapter(Activity context, List<ShopListBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    public void addData(List<ShopListBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    ;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_content, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        if (data.get(i).getPromotion_price().equals("0")) {
            holder.tvDeleteLine.setVisibility(View.GONE);
            holder.tvShopPrice.setText(data.get(i).getPrice());
        } else {
            holder.tvDeleteLine.setVisibility(View.VISIBLE);
            holder.tvDeleteLine.setText("¥" + data.get(i).getPrice());
            string = new SpannableString(holder.tvDeleteLine.getText().toString().trim());
            span = new StrikethroughSpan();
            string.setSpan(span, 0, holder.tvDeleteLine.getText().toString().trim().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.tvShopPrice.setText(data.get(i).getPromotion_price());
        }
        holder.tvSaleNum.setText("月售" + data.get(i).getMonthly_sales());
        if (data.get(i).getStock() == null || data.get(i).getStock().equals("") || (int)Double.parseDouble(data.get(i).getStock()) == 0) {
            holder.tvStock.setText("已售罄");
            holder.imgAddCar.setEnabled(false);
            holder.imgAddCar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.img_shop_list_no_car));
            holder.tvStock.setTextColor(ContextCompat.getColor(context, R.color.color_f80000));
        } else {
            holder.imgAddCar.setEnabled(true);
            holder.imgAddCar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.img_shop_list_car));
            holder.tvStock.setText("库存" + data.get(i).getStock());
            holder.tvStock.setTextColor(ContextCompat.getColor(context, R.color.ff999999));
        }
        holder.tvShopName.setText(data.get(i).getName());
        if (!context.isFinishing()) {
            PictureHelper.setImageView(context, data.get(i).getImage(), holder.imgShop, R.drawable.img_err);
        } else {
            holder.imgShop.setVisibility(View.GONE);
        }
        holder.tvDeleteLine.setText(string);
        holder.llShopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onClick(v, i);
            }
        });
        holder.imgAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnAddCarClickListener.onClick(holder.imgShop, v, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_shop)
        RoundImageView imgShop;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_sale_num)
        TextView tvSaleNum;
        @BindView(R.id.tv_shop_price)
        TextView tvShopPrice;
        @BindView(R.id.tv_delete_line)
        TextView tvDeleteLine;
        @BindView(R.id.ll_shop_item)
        LinearLayout llShopItem;
        @BindView(R.id.img_add_car)
        ImageView imgAddCar;
        @BindView(R.id.tv_stock)
        TextView tvStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public interface SetOnItemClickListener {
        void onClick(View view, int position);
    }

    private SetOnAddCarClickListener setOnAddCarClickListener;

    public void setOnAddCarClickListener(SetOnAddCarClickListener setOnAddCarClickListener) {
        this.setOnAddCarClickListener = setOnAddCarClickListener;
    }

    public interface SetOnAddCarClickListener {
        void onClick(ImageView view, View carView, int position);
    }
}