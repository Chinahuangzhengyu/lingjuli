package com.zhjl.qihao.freshshop.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.integration.bean.CardListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListAdapter extends BaseAdapter {
    private Context context;
    private SpannableString string;
    private int selectPosition = -1;
    private List<CardListBean> list;

    public CardListAdapter(Context context, List<CardListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.card_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getMoney() != null) {
            holder.tvPlane.setText("余额(剩余¥" + list.get(position).getMoney() + ")");
        }
        string = new SpannableString(holder.tvPlane.getText().toString().trim());
        if (selectPosition == position) {
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.car_choose_click);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvPlane.setCompoundDrawables(null, null, drawable, null);
        } else {
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.car_choose);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvPlane.setCompoundDrawables(null, null, drawable, null);
        }
        string.setSpan(new AbsoluteSizeSpan(12, true), 2, holder.tvPlane.getText().toString().trim().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvPlane.setText(string);
        holder.tvMembershipCardNumber.setText(list.get(position).getCard_id());
        return convertView;
    }

    public void check(int position) {
        selectPosition = position;
        notifyDataSetChanged();
    }

    public void clearCheck() {
        selectPosition = -1;
        notifyDataSetChanged();
    }

    public void addData(List<CardListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_plane)
        TextView tvPlane;
        @BindView(R.id.tv_membership_card_number)
        TextView tvMembershipCardNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
