package com.zhjl.qihao.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.ServiceAfterDetailBean;
import com.zhjl.qihao.order.bean.ServiceListBean;
import com.zhjl.qihao.order.view.DateTextView;
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

public class ReturnOrderDetailActivity extends VolleyBaseActivity {

    @BindView(R.id.img_order_detail_status)
    ImageView imgOrderDetailStatus;
    @BindView(R.id.tv_order_detail_status)
    TextView tvOrderDetailStatus;
    @BindView(R.id.tv_order_detail_time)
    DateTextView tvOrderDetailTime;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.lv_shop_list)
    ListViewForScrollView lvShopList;
    @BindView(R.id.tv_request_record)
    TextView tvRequestRecord;
    @BindView(R.id.tv_return_money_info)
    TextView tvReturnMoneyInfo;
    @BindView(R.id.tv_return_money_info_content)
    TextView tvReturnMoneyInfoContent;
    @BindView(R.id.tv_request_time)
    TextView tvRequestTime;
    @BindView(R.id.gv_img)
    GridViewForScrollView gvImg;
    @BindView(R.id.sv_order_detail)
    ScrollView svOrderDetail;
    @BindView(R.id.tv_contact_person)
    TextView tvContactPerson;
    @BindView(R.id.btn_update_request)
    Button btnUpdateRequest;
    @BindView(R.id.ll_button)
    RelativeLayout llButton;
    @BindView(R.id.tv_return_money_code)
    TextView tvReturnMoneyCode;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @BindView(R.id.tv_return_money_name)
    TextView tvReturnMoneyName;
    @BindView(R.id.tv_return_money_price)
    TextView tvReturnMoneyPrice;
    private int afterSaleId;
    private int orderId;
    private ServiceListBean.ProductAftersaleBean data;
    private List<ServiceAfterDetailBean.ImagesBean> images = new ArrayList<>();
    private DetailPhotoAdapter detailPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_order_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "订单详情", this);
        afterSaleId = getIntent().getIntExtra("afterSaleId", 0);
        orderId = getIntent().getIntExtra("orderId", 0);
        data = getIntent().getParcelableExtra("data");
        initView();
        initData();
        requestRead();
        detailPhotoAdapter = new DetailPhotoAdapter(images);
        gvImg.setAdapter(detailPhotoAdapter);
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
                    Utils.phpIsLogin(ReturnOrderDetailActivity.this,object.optInt("type"),object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initView() {
        if (data != null) {
            tvOrderStatus.setText(data.getShop_name());
            lvShopList.setAdapter(new ShopListAdapter(data));
        }
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
                    tvOrderDetailStatus.setText(afterDetailBean.getStatus_name());
                    tvReturnMoneyInfoContent.setText(afterDetailBean.getContent());
                    tvRequestTime.setText(afterDetailBean.getCreate_date());
                    tvReturnMoneyCode.setText(afterDetailBean.getNo());
                    tvSumPrice.setText("退款总金额¥" + afterDetailBean.getPrice());
                    if ( afterDetailBean.getStatus_code() == 3) {
                        tvReturnMoneyName.setVisibility(View.GONE);
                        tvReturnMoneyPrice.setVisibility(View.GONE);
                    } else {
                        if (afterDetailBean.getPoints_deduct_price() != 0) {
                            tvReturnMoneyPrice.setText("¥" + afterDetailBean.getPoints_deduct_price());
                            tvReturnMoneyName.setVisibility(View.VISIBLE);
                            tvReturnMoneyPrice.setVisibility(View.VISIBLE);
                        } else {
                            tvReturnMoneyName.setVisibility(View.GONE);
                            tvReturnMoneyPrice.setVisibility(View.GONE);
                        }
                    }
                    if (afterDetailBean.getImages().size() > 0) {
                        llImg.setVisibility(View.VISIBLE);
                        images = afterDetailBean.getImages();
                        detailPhotoAdapter.addData(images);
                    } else {
                        llImg.setVisibility(View.GONE);
                    }
                } else {
                    Utils.phpIsLogin(ReturnOrderDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.tv_contact_person})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_contact_person:
//                Utils.callPerson(this, "是否拨打电话0855-8552588", "0855-8552588");
                Intent intent = new Intent();
                intent.setClass(mContext, UserAgreementActivity.class);
                intent.putExtra("name","在线客服");
                intent.putExtra("webContent","http://p.qiao.baidu.com/cps/chat?siteId=14492024&userId=29966678&cp=&cr=APP&cw=");
                startActivity(intent);
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
            if (productAfterSaleBean.getUnit_name()!=null&&!productAfterSaleBean.getUnit_name().equals("")){
                holder.tvShopType.setVisibility(View.VISIBLE);
                holder.tvShopType.setText(productAfterSaleBean.getUnit_name());
            }else {
                holder.tvShopType.setVisibility(View.GONE);
            }
            holder.tvReturnMoneyName.setText(productAfterSaleBean.getStatus_name());
            PictureHelper.setImageView(mContext, productAfterSaleBean.getProduct_image(), holder.imgShop, R.drawable.img_loading);
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
            @BindView(R.id.tv_return_money_name)
            TextView tvReturnMoneyName;

            ShopListViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class DetailPhotoAdapter extends BaseAdapter {

        private List<ServiceAfterDetailBean.ImagesBean> list;

        public DetailPhotoAdapter(List<ServiceAfterDetailBean.ImagesBean> list) {
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
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.detail_pic, null);
                holder = new ViewHolder();
                holder.img = convertView.findViewById(R.id.img_pic);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PictureHelper.setImageView(mContext, list.get(position).getMin(), holder.img, R.drawable.img_loading);
            return convertView;
        }

        public void addData(List<ServiceAfterDetailBean.ImagesBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        RoundImageView img;
    }
}
