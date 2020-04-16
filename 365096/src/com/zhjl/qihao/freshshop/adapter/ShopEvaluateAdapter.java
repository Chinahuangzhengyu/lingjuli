package com.zhjl.qihao.freshshop.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.bean.ShopEvaluateBean;
import com.zhjl.qihao.freshshop.fragment.ComplaintPopFragment;
import com.zhjl.qihao.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopEvaluateAdapter extends RecyclerView.Adapter<ShopEvaluateAdapter.ViewHolder> {

    private VolleyBaseActivity context;
    private List<ShopEvaluateBean.ProductCommentsBean> list;

    public ShopEvaluateAdapter(VolleyBaseActivity context, List<ShopEvaluateBean.ProductCommentsBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<ShopEvaluateBean.ProductCommentsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_evaluate_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        try {
            holder.tvEvaluateName.setText(list.get(i).getNick_name());
            holder.tvEvaluateDate.setText(list.get(i).getCreate_date());
            holder.tvEvaluateContent.setText(list.get(i).getContent());
            holder.tvEvaluateType.setText(list.get(i).getScore_status());
            if (list.get(i).getImages() != null && list.get(i).getImages().getMin().size() > 0) {
                holder.gvEvaluateImg.setVisibility(View.VISIBLE);
                holder.gvEvaluateImg.setAdapter(new EvaluateImgAdapter(list.get(i).getImages().getMin()));
            } else {
                holder.gvEvaluateImg.setVisibility(View.GONE);
            }
            PictureHelper.setHeadImageView(context, list.get(i).getUser_picture(), holder.imgEvaluateHead, R.drawable.ic_head);
            holder.tvEvaluateComplaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComplaintPopFragment fragment = new ComplaintPopFragment(context,list.get(i).getId());
                    fragment.show(context.getSupportFragmentManager(), "3");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_evaluate_head)
        CircleImageView imgEvaluateHead;
        @BindView(R.id.tv_evaluate_name)
        TextView tvEvaluateName;
        @BindView(R.id.tv_evaluate_date)
        TextView tvEvaluateDate;
        @BindView(R.id.tv_evaluate_complaint)
        ImageView tvEvaluateComplaint;
        @BindView(R.id.tv_evaluate_content)
        TextView tvEvaluateContent;
        @BindView(R.id.tv_evaluate_type)
        TextView tvEvaluateType;
        @BindView(R.id.gv_evaluate_img)
        GridViewForScrollView gvEvaluateImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class EvaluateImgAdapter extends BaseAdapter {

        private List<String> list;

        public EvaluateImgAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.evaluate_img_item, null);
            }
            RoundImageView imgEvaluate = convertView.findViewById(R.id.img_evaluate);
            PictureHelper.setImageView(context, list.get(position), imgEvaluate, R.drawable.bg_cs);
            return convertView;
        }
    }

}
