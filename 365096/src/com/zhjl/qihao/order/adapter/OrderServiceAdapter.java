package com.zhjl.qihao.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.order.activity.OrderDetailActivity;
import com.zhjl.qihao.order.activity.ReturnMoneyDetailActivity;
import com.zhjl.qihao.order.activity.ReturnOrderDetailActivity;
import com.zhjl.qihao.order.bean.ServiceListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderServiceAdapter extends RecyclerView.Adapter<OrderServiceAdapter.ViewHolder> {


    private Context context;
    private List<ServiceListBean.ProductAftersaleBean> productAfterSale;

    public OrderServiceAdapter(Context context, List<ServiceListBean.ProductAftersaleBean> productAfterSale) {
        this.context = context;
        this.productAfterSale = productAfterSale;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_service_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.lvShopList.setAdapter(new ShopListAdapter(productAfterSale.get(i)));
        holder.tvShopAddressName.setText(productAfterSale.get(i).getShop_name());
        holder.tvServiceStatus.setText(productAfterSale.get(i).getStatus_name());
        holder.tvSeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (productAfterSale.get(i).getStatus_code() == 1 || productAfterSale.get(i).getStatus_code() == 0) {   //未处理
                    intent.setClass(context, ReturnOrderDetailActivity.class);
                } else {                     //已处理
                    intent.setClass(context, ReturnMoneyDetailActivity.class);
                }
                intent.putExtra("data", productAfterSale.get(i));
                intent.putExtra("afterSaleId", productAfterSale.get(i).getAftersale_id());
                intent.putExtra("orderId", productAfterSale.get(i).getOrder_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productAfterSale.size();
    }

    public void addData(List<ServiceListBean.ProductAftersaleBean> productAfterSale) {
        this.productAfterSale = productAfterSale;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_shop_address_name)
        TextView tvShopAddressName;
        @BindView(R.id.lv_shop_list)
        ListView lvShopList;
        @BindView(R.id.tv_see_detail)
        TextView tvSeeDetail;
        @BindView(R.id.tv_service_status)
        TextView tvServiceStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ShopListAdapter extends BaseAdapter {

        private ServiceListBean.ProductAftersaleBean productAfterSaleBean;

        public ShopListAdapter(ServiceListBean.ProductAftersaleBean productAfterSaleBean) {
            this.productAfterSaleBean = productAfterSaleBean;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ShopListViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.shop_list, null);
                holder = new ShopListViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ShopListViewHolder) convertView.getTag();
            }
            holder.tvShopPrice.setText("¥" + productAfterSaleBean.getPrice());
            holder.tvShopName.setText(productAfterSaleBean.getProduct_name());
            holder.tvShopCount.setText("x" + productAfterSaleBean.getNumber());
            if (productAfterSaleBean.getUnit_name()!=null&&!productAfterSaleBean.getUnit_name().equals("")){
                holder.tvShopType.setVisibility(View.VISIBLE);
                holder.llShopType.setVisibility(View.GONE);
                holder.tvShopType.setText(productAfterSaleBean.getUnit_name());
            }else {
                holder.llShopType.setVisibility(View.GONE);
                holder.tvShopType.setVisibility(View.GONE);
            }
            PictureHelper.setImageView(context, productAfterSaleBean.getProduct_image(), holder.imgShop, R.drawable.img_loading);
            return convertView;
        }

        class ShopListViewHolder {
            @BindView(R.id.img_shop)
            RoundImageView imgShop;
            @BindView(R.id.tv_shop_price)
            TextView tvShopPrice;
            @BindView(R.id.tv_shop_count)
            TextView tvShopCount;
            @BindView(R.id.tv_shop_name)
            TextView tvShopName;
            @BindView(R.id.tv_shop_type)
            TextView tvShopType;
            @BindView(R.id.ll_shop_type)
            LinearLayout llShopType;

            ShopListViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
