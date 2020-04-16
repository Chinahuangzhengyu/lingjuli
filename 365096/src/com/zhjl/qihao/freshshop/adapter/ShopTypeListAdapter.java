package com.zhjl.qihao.freshshop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.activity.ShopTypeListDetailActivity;
import com.zhjl.qihao.freshshop.activity.ShopTypeMoreActivity;
import com.zhjl.qihao.freshshop.bean.ShopTypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopTypeListAdapter extends RecyclerView.Adapter<ShopTypeListAdapter.ViewHolder> {


    private Activity context;
    private List<ShopTypeBean> list;

    public ShopTypeListAdapter(Activity context, List<ShopTypeBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<ShopTypeBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_type_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        try {
            holder.tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopTypeMoreActivity.class);
                    intent.putExtra("typeId",list.get(i).getId());
                    intent.putExtra("name",list.get(i).getName());
                    context.startActivity(intent);
                }
            });
            holder.tvShopType.setText(list.get(i).getName());
            holder.gvShopTypeList.setAdapter(new ShopTypeItemAdapter(list.get(i).getChildren()));
            holder.gvShopTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, ShopTypeListDetailActivity.class);
                    intent.putExtra("typeId",list.get(i).getChildren().get(position).getId());
                    intent.putExtra("name",list.get(i).getChildren().get(position).getName());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "数据出错！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ShopTypeItemAdapter extends BaseAdapter {

        private List<ShopTypeBean.ChildrenBean> children;

        public ShopTypeItemAdapter(List<ShopTypeBean.ChildrenBean> children) {
            this.children = children;
        }

        @Override
        public int getCount() {
            return children.size();
        }

        @Override
        public Object getItem(int position) {
            return children.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GvViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.shop_type_item, null);
                holder = new GvViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (GvViewHolder) convertView.getTag();
            }
            PictureHelper.setOptionsGlide(context, 0, children.get(position).getImage(), holder.imgShopType, R.drawable.img_err);
            holder.tvShopTypeName.setText(children.get(position).getName());
            return convertView;
        }


        class GvViewHolder {
            @BindView(R.id.img_shop_type)
            RoundImageView imgShopType;
            @BindView(R.id.tv_shop_type_name)
            TextView tvShopTypeName;

            GvViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.tv_shop_type)
        TextView tvShopType;
        @BindView(R.id.gv_shop_type_list)
        GridView gvShopTypeList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
