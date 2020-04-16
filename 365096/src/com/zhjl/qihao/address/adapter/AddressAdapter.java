package com.zhjl.qihao.address.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhjl.qihao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private Activity context;

    public AddressAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_city_name)
        TextView tvCityName;
        @BindView(R.id.tv_address_name)
        TextView tvAddressName;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_check_address)
        TextView tvCheckAddress;
        @BindView(R.id.tv_delete_address)
        TextView tvDeleteAddress;
        @BindView(R.id.tv_edit_address)
        TextView tvEditAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
