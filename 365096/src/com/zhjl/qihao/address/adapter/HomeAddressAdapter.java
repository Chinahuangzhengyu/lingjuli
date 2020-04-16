package com.zhjl.qihao.address.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.freshshop.bean.AddressListBean;
import com.zhjl.qihao.freshshop.bean.AddressRoomBean;
import com.zhjl.qihao.freshshop.bean.CollectAddressListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAddressAdapter extends RecyclerView.Adapter<HomeAddressAdapter.ViewHolder> {


    private Context context;
    private List<Object> addressList;
    private final Session session;
    private int position;


    public HomeAddressAdapter(Context context, List<Object> addressList, int position) {
        this.context = context;
        this.position = position;
        this.addressList = addressList;
        session = Session.get(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_address_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        try {
            if (addressList.get(i) instanceof AddressListBean) {
                AddressListBean data = (AddressListBean) addressList.get(i);
                holder.tvAddressCity.setText(data.getZone_name());
                holder.tvAddressCityDetail.setText(data.getAddress() + data.getName());
                holder.tvAddressName.setText("联系电话");
                holder.tvAddressPhone.setText(data.getTel());
            } else if (addressList.get(i) instanceof AddressRoomBean.RoomsBean){
                AddressRoomBean.RoomsBean data = (AddressRoomBean.RoomsBean) addressList.get(i);
                holder.tvAddressCity.setText(data.getArea());
                holder.tvAddressCityDetail.setText(data.getName() + data.getRome_name());
                holder.tvAddressName.setText("联系电话");
                holder.tvAddressPhone.setText(session.getRegisterMobile());
            }else {
                CollectAddressListBean.CollectesBean data = (CollectAddressListBean.CollectesBean) addressList.get(i);
                holder.tvAddressCity.setText(data.getCommunity_name());
                holder.tvAddressCityDetail.setText(data.getAddress());
                holder.tvAddressName.setText("联系电话");
                holder.tvAddressPhone.setText(data.getTel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.llHomeAddressItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onClick(v, i, holder.tvStatus);
            }
        });
        if (i == position) {
            holder.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.car_choose_click));
        } else {
            holder.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.car_choose));
        }
    }

    public void check(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

//    public void clearCheck() {
//        position = -1;
//        notifyDataSetChanged();
//    }


    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public void addData(List<Object> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_address_city)
        TextView tvAddressCity;
        @BindView(R.id.tv_address_city_detail)
        TextView tvAddressCityDetail;
        @BindView(R.id.tv_address_name)
        TextView tvAddressName;
        @BindView(R.id.tv_address_phone)
        TextView tvAddressPhone;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.ll_home_address_item)
        RelativeLayout llHomeAddressItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SetOnItemClickListener {
        void onClick(View view, int position, TextView tv);
    }

    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }
}
