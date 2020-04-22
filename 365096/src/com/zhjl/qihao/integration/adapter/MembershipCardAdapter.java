package com.zhjl.qihao.integration.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.integration.activity.MembershipCardDetailActivity;
import com.zhjl.qihao.integration.bean.CardListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembershipCardAdapter extends RecyclerView.Adapter<MembershipCardAdapter.ViewHolder> {

    private VolleyBaseActivity context;
    private List<CardListBean> list;
    public static final int REQUEST_CARD_CODE = 0x4;

    public MembershipCardAdapter(VolleyBaseActivity context, List<CardListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.membership_card_list, viewGroup, false);
        return new ViewHolder(view);
    }

    public void addData(List<CardListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if (list.get(i).getIs_default() == 0) {
            holder.imgDefault.setVisibility(View.GONE);
        }else {
            holder.imgDefault.setVisibility(View.VISIBLE);
        }
        holder.tvMembershipCardNumber.setText(list.get(i).getCard_id());
        holder.rlMembershipCardItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, MembershipCardDetailActivity.class);
            intent.putExtra("cardId", list.get(i).getCard_id());
            context.startActivityForResult(intent,REQUEST_CARD_CODE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_membership_card_number)
        TextView tvMembershipCardNumber;
        @BindView(R.id.rl_membership_card_item)
        RelativeLayout rlMembershipCardItem;
        @BindView(R.id.img_default)
        ImageView imgDefault;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
