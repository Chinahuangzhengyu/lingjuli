package com.zhjl.qihao.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.userlogin.adapter.ShopListAdapter;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.ServiceChooseBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ListViewForScrollView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class RequestReplacementActivity extends VolleyBaseActivity {

    @BindView(R.id.lv_shop_list)
    ListViewForScrollView lvShopList;
    @BindView(R.id.rl_only_money)
    RelativeLayout rlOnlyMoney;
    @BindView(R.id.rl_return_money_shop)
    RelativeLayout rlReturnMoneyShop;
    @BindView(R.id.rl_return_shop)
    RelativeLayout rlReturnShop;
    private int orderItemId;
    private long orderId;
    private ShopListAdapter shopListAdapter;
    private ArrayList<ServiceChooseBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_replacement);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "申请退换", this);
        orderItemId = getIntent().getIntExtra("order_item_id", 0);
        orderId = getIntent().getLongExtra("order_id", 0);
        initData();
        shopListAdapter = new ShopListAdapter(list);
        lvShopList.setAdapter(shopListAdapter);
    }

    private void initData() {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("order_id", orderId);
        map.put("order_item_id", orderItemId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.chooseServiceType(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                boolean status = object.optBoolean("status");
                if (status) {
                    list.clear();
                    Gson gson = new Gson();
                    ServiceChooseBean serviceChooseBean = gson.fromJson(string, ServiceChooseBean.class);
                    list.add(serviceChooseBean);
                    shopListAdapter.addData(list);
                } else {
                    Utils.phpIsLogin(RequestReplacementActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.rl_only_money, R.id.rl_return_money_shop, R.id.rl_return_shop, R.id.iv_back})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_only_money:        //退款
                intent.setClass(mContext, ReplacementDetailActivity.class);
                intent.putExtra("name","申请退款");
                intent.putParcelableArrayListExtra("list",list);
                startActivity(intent);
                break;
            case R.id.rl_return_money_shop:     //退货退款
                intent.setClass(mContext, ReplacementDetailActivity.class);
                intent.putExtra("name","申请退款退货");
                intent.putParcelableArrayListExtra("list",list);
                startActivity(intent);
                break;
            case R.id.rl_return_shop:       //退货
                intent.setClass(mContext, ReplacementDetailActivity.class);
                intent.putExtra("name","申请换货");
                intent.putParcelableArrayListExtra("list",list);
                startActivity(intent);
                break;
        }
    }

    class ShopListAdapter extends BaseAdapter {

        private List<ServiceChooseBean> list;

        public ShopListAdapter(List<ServiceChooseBean> list) {
            this.list = list;
        }

        public void addData(List<ServiceChooseBean> list) {
            this.list = list;
            notifyDataSetChanged();
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
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.shop_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                holder.tvShopName.setText(list.get(position).getName());
                holder.tvShopCount.setText("x" + list.get(position).getNumber());
                holder.tvShopPrice.setText("¥"+list.get(position).getPrice());
                if (list.get(position).getUnit_name()!=null&&!list.get(position).getUnit_name().equals("")){
                    holder.tvShopType.setVisibility(View.VISIBLE);
                    holder.tvShopType.setText(list.get(position).getUnit_name());
                }else {
                    holder.tvShopType.setVisibility(View.GONE);
                }
                PictureHelper.setImageView(mContext, list.get(position).getImage(), holder.imgShop, R.drawable.img_loading);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }

    class ViewHolder {
        @BindView(R.id.img_shop)
        RoundImageView imgShop;
        @BindView(R.id.tv_shop_price)
        TextView tvShopPrice;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_shop_count)
        TextView tvShopCount;
        @BindView(R.id.tv_return_money_name)
        TextView tvReturnMoneyName;
        @BindView(R.id.tv_return_type)
        TextView tvReturnType;
        @BindView(R.id.tv_shop_type)
        TextView tvShopType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
