package com.zhjl.qihao.order.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.ServiceAfterDetailBean;
import com.zhjl.qihao.order.bean.ServiceListBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ListViewForScrollView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ReturnMoneyDetailActivity extends VolleyBaseActivity {

    @BindView(R.id.tv_return_success)
    TextView tvReturnSuccess;
    @BindView(R.id.tv_return_success_time)
    TextView tvReturnSuccessTime;
    @BindView(R.id.tv_return_info)
    TextView tvReturnInfo;
    @BindView(R.id.tv_return_money_case)
    TextView tvReturnMoneyCase;
    @BindView(R.id.tv_return_money_info_content)
    TextView tvReturnMoneyInfoContent;
    @BindView(R.id.tv_request_time)
    TextView tvRequestTime;
    @BindView(R.id.tv_return_money_code)
    TextView tvReturnMoneyCode;
    @BindView(R.id.tv_return_money_price)
    TextView tvReturnMoneyPrice;
    @BindView(R.id.tv_return_money_type)
    TextView tvReturnMoneyType;
    @BindView(R.id.rl_return_info)
    RelativeLayout rlReturnInfo;
    @BindView(R.id.rl_background)
    RelativeLayout rlBackground;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.lv_shop_list)
    ListViewForScrollView lvShopList;
    @BindView(R.id.tv_return_money_name)
    TextView tvReturnMoneyName;
    @BindView(R.id.tv_return_money_price_name)
    TextView tvReturnMoneyPriceName;
    @BindView(R.id.tv_return_nb_price_name)
    TextView tvReturnNbPriceName;
    @BindView(R.id.tv_return_nb_price)
    TextView tvReturnNbPrice;
    private String orderType = "";
    private int afterSaleId;
    private int orderId;
    private ServiceListBean.ProductAftersaleBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_money_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "退款详情", this);
        orderType = getIntent().getStringExtra("orderType");
        afterSaleId = getIntent().getIntExtra("afterSaleId", 0);
        orderId = getIntent().getIntExtra("orderId", 0);
        data = getIntent().getParcelableExtra("data");
        initData();
        requestRead();
        lvShopList.setAdapter(new ShopListAdapter(data));
    }

    private void requestRead() {
        OrderApiInterface orderApiInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String,Object> map = new HashMap<>();
        map.put("user_token",mSession.getmToken());
        map.put("aftersale_id",afterSaleId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderApiInterface.readAfterSale(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (!status){
                    Utils.phpIsLogin(ReturnMoneyDetailActivity.this,object.optInt("type"),object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initData() {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("aftersale_id", afterSaleId);
        map.put("order_id", orderId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.serviceListDetail(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                if (object.optBoolean("status")) {
                    Gson gson = new Gson();
                    ServiceAfterDetailBean afterDetailBean = gson.fromJson(string, ServiceAfterDetailBean.class);
                    tvReturnMoneyInfoContent.setText(afterDetailBean.getContent());
                    tvRequestTime.setText(afterDetailBean.getCreate_date());
                    tvReturnMoneyCode.setText(afterDetailBean.getNo());
                    tvReturnMoneyPrice.setText("¥" + afterDetailBean.getPrice());
                    tvReturnMoneyType.setText(afterDetailBean.getReturn_msg());
                    tvContent.setText(afterDetailBean.getReply());
                    int dealCode = afterDetailBean.getDeal_code();
                    int statusCode = afterDetailBean.getStatus_code();
                    tvReturnSuccess.setText(afterDetailBean.getStatus_name());
                    if (statusCode == 3) {
                        tvReturnInfo.setText("换货信息");
                        tvReturnMoneyName.setVisibility(View.GONE);
                        tvReturnMoneyType.setVisibility(View.GONE);
                        tvReturnMoneyPriceName.setVisibility(View.GONE);
                        tvReturnMoneyPrice.setVisibility(View.GONE);
                    } else {
                        tvReturnInfo.setText("退款信息");
                        tvReturnMoneyName.setVisibility(View.VISIBLE);
                        tvReturnMoneyType.setVisibility(View.VISIBLE);
                        tvReturnMoneyPriceName.setVisibility(View.VISIBLE);
                        tvReturnMoneyPrice.setVisibility(View.VISIBLE);
                    }
                    if (dealCode == -1) {
                        tvReturnMoneyName.setVisibility(View.GONE);
                        tvReturnMoneyType.setVisibility(View.GONE);
                        tvReturnMoneyPriceName.setVisibility(View.GONE);
                        tvReturnMoneyPrice.setVisibility(View.GONE);
                        tvReturnNbPriceName.setVisibility(View.GONE);
                        tvReturnNbPrice.setVisibility(View.GONE);
                        rlBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.img_return_money_err));
                    } else {
                        if (afterDetailBean.getPoints_deduct_price()!=0){
                            tvReturnNbPrice.setText("¥"+afterDetailBean.getPoints_deduct_price());
                            tvReturnNbPriceName.setVisibility(View.VISIBLE);
                            tvReturnNbPrice.setVisibility(View.VISIBLE);
                        }else {
                            tvReturnNbPriceName.setVisibility(View.GONE);
                            tvReturnNbPrice.setVisibility(View.GONE);
                        }
                        rlBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.img_return_money_bg));
                    }

                } else {
                    Utils.phpIsLogin(ReturnMoneyDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
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
                convertView = View.inflate(mContext, R.layout.shop_list, null);
                holder = new ShopListViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ShopListViewHolder) convertView.getTag();
            }
            holder.tvShopPrice.setText("¥" + productAfterSaleBean.getPrice());
            holder.tvShopCount.setText("x" + productAfterSaleBean.getNumber());
            holder.tvShopName.setText(productAfterSaleBean.getProduct_name());
            holder.tvReturnMoneyName.setText(productAfterSaleBean.getStatus_name());
            if (productAfterSaleBean.getUnit_name()!=null&&!productAfterSaleBean.getUnit_name().equals("")){
                holder.tvShopType.setVisibility(View.VISIBLE);
                holder.tvShopType.setText(productAfterSaleBean.getUnit_name());
            }else {
                holder.tvShopType.setVisibility(View.GONE);
            }
            PictureHelper.setImageView(mContext, productAfterSaleBean.getProduct_image(), holder.imgShop, R.drawable.img_loading);
            return convertView;
        }

        class ShopListViewHolder {
            @BindView(R.id.img_shop)
            RoundImageView imgShop;
            @BindView(R.id.tv_shop_price)
            TextView tvShopPrice;
            @BindView(R.id.tv_shop_type)
            TextView tvShopType;
            @BindView(R.id.tv_shop_count)
            TextView tvShopCount;
            @BindView(R.id.tv_shop_name)
            TextView tvShopName;
            @BindView(R.id.tv_return_money_name)
            TextView tvReturnMoneyName;

            ShopListViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
