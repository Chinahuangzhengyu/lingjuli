package com.zhjl.qihao.order.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.activity.ShopPayActivity;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.ShopDetailBean;
import com.zhjl.qihao.util.MapUtils;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ListViewForScrollView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.math.BigDecimal;
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

import static com.zhjl.qihao.address.activity.HomeAddressListActivity.FINE_LOCATION_CODE;
import static com.zhjl.qihao.util.RequestPermissionUtils.REQUEST_PERMISSION_SETTING;

/**
 * 订单详情
 */
public class OrderDetailActivity extends VolleyBaseActivity {


    @BindView(R.id.img_order_detail_status)
    ImageView imgOrderDetailStatus;
    @BindView(R.id.tv_order_detail_status)
    TextView tvOrderDetailStatus;
    @BindView(R.id.img_navigation)
    ImageView imgNavigation;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @BindView(R.id.tv_order_address)
    TextView tvOrderAddress;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.lv_shop_list)
    ListViewForScrollView lvShopList;
    @BindView(R.id.tv_shop_sum_price)
    TextView tvShopSumPrice;
    @BindView(R.id.tv_shop_send_price)
    TextView tvShopSendPrice;
    @BindView(R.id.tv_lose_money)
    TextView tvLoseMoney;
    @BindView(R.id.tv_sure_money)
    TextView tvSureMoney;
    @BindView(R.id.tv_order_info)
    TextView tvOrderInfo;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_notify_business)
    TextView tvNotifyBusiness;
    @BindView(R.id.tv_return_money)
    TextView tvReturnMoney;
    @BindView(R.id.img_shop_car)
    ImageView imgShopCar;
    @BindView(R.id.img_phone)
    ImageView imgPhone;
    @BindView(R.id.tv_order_send_status)
    TextView tvOrderSendStatus;
    @BindView(R.id.btn_return_money_progress)
    Button btnReturnMoneyProgress;
    @BindView(R.id.rl_order_logistics)
    RelativeLayout rlOrderLogistics;
    @BindView(R.id.tv_order_send_detail)
    TextView tvOrderSendDetail;
    @BindView(R.id.tv_call_person)
    TextView tvCallPerson;
    @BindView(R.id.tv_memo)
    TextView tvMemo;
    @BindView(R.id.tv_memo_content)
    TextView tvMemoContent;
    @BindView(R.id.tv_pick_code_name)
    TextView tvPickCodeName;
    @BindView(R.id.tv_pick_code)
    TextView tvPickCode;
    private int orderStatus;
    private long id;
    private ShopListAdapter shopListAdapter;
    private List<ShopDetailBean.OrderItemsBean> orderItems = new ArrayList<>();
    private OrderApiInterface orderInterface;
    private PopupWindow surePopWindow;
    private ShopDetailBean detailBean;
    private String tel;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private double longitude;
    private double latitude;
    public static final int RESULT_ORDER = 0x116;
    private static final int REQUEST_PAY = 0x117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "订单详情", this);
        initMap();
        orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        orderStatus = getIntent().getIntExtra("orderStatus", 0);
        switch (orderStatus) {
            case 0:     //待支付订单
                tvReturnMoney.setText("取消订单");
                tvNotifyBusiness.setText("付款");
                tvNotifyBusiness.setVisibility(View.VISIBLE);
                tvReturnMoney.setVisibility(View.VISIBLE);
                break;
            case 1:     //待发货订单
                tvReturnMoney.setVisibility(View.GONE);
                tvNotifyBusiness.setText("提醒商家");
                tvNotifyBusiness.setVisibility(View.VISIBLE);
                break;
            case 2:     //已发货订单
                tvReturnMoney.setVisibility(View.GONE);
                tvNotifyBusiness.setText("确认收货");
                tvNotifyBusiness.setVisibility(View.VISIBLE);
                break;
            case 3:     //已完成订单
                tvReturnMoney.setText("删除订单");
                tvNotifyBusiness.setVisibility(View.GONE);
                tvReturnMoney.setVisibility(View.VISIBLE);
                break;
        }
        if (tvNotifyBusiness.getVisibility() == View.GONE) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tvReturnMoney.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvReturnMoney.setLayoutParams(lp);
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tvReturnMoney.getLayoutParams();
            lp.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvReturnMoney.setLayoutParams(lp);
        }
        id = getIntent().getLongExtra("id", 0);
        initData();
        shopListAdapter = new ShopListAdapter(orderItems);
        lvShopList.setAdapter(shopListAdapter);
    }

    private void initMap() {
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //注册监听函数
        mLocationClient.setLocOption(option);
        if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION})) {
            mLocationClient.start();// 定位SDK
        } else {
            RequestPermissionUtils.requestPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
        }


    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //纬度
            latitude = location.getLatitude();
            //经度
            longitude = location.getLongitude();

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
        }
    }

    private void initData() {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("id", id);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.orderShopDetail(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                if (object.optBoolean("status")) {
                    detailBean = new ShopDetailBean();
                    detailBean.setStatus_name(object.optString("status_name"));
                    tvOrderDetailStatus.setText(detailBean.getStatus_name());
                    detailBean.setDelivery_code(object.optInt("delivery_code"));
                    int deliveryCode = detailBean.getDelivery_code();
                    detailBean.setStatus_code(object.optInt("status_code"));
                    int statusCode = detailBean.getStatus_code();
                    //配送人员电话
                    detailBean.setTel(object.optString("tel"));
                    tel = detailBean.getTel();
                    detailBean.setDelivery_title(object.optString("delivery_title"));
                    detailBean.setDelivery_detail(object.optString("delivery_detail"));
                    detailBean.setMemo(object.optString("memo"));
                    //备注是否显示
                    if (detailBean.getMemo() != null && !detailBean.getMemo().equals("")) {
                        tvMemo.setVisibility(View.VISIBLE);
                        tvMemoContent.setVisibility(View.VISIBLE);
                        tvMemoContent.setText(detailBean.getMemo());
                    } else {
                        tvMemo.setVisibility(View.GONE);
                        tvMemoContent.setVisibility(View.GONE);
                    }

                    if (deliveryCode == 1) {       //门店自提
                        imgNavigation.setVisibility(View.VISIBLE);
                        rlOrderLogistics.setVisibility(View.GONE);
                    } else {
                        if (statusCode == 1 || statusCode == 2) {
                            rlOrderLogistics.setVisibility(View.VISIBLE);
                            tvOrderSendStatus.setText(detailBean.getDelivery_title());
                            tvOrderSendDetail.setText(detailBean.getDelivery_detail());
                        } else {
                            rlOrderLogistics.setVisibility(View.GONE);
                        }
                        imgNavigation.setVisibility(View.GONE);
                    }
                    detailBean.setDelivery_type(object.optString("delivery_type"));
                    tvOrderStatus.setText(detailBean.getDelivery_type());
                    detailBean.setName(object.optString("name"));
                    tvOrderName.setText(detailBean.getName());
                    tvOrderPhone.setText(tel);
                    detailBean.setAddress(object.optString("address"));
                    tvOrderAddress.setText(detailBean.getAddress());
                    detailBean.setOriginal_price(object.optString("original_price"));
                    tvShopSumPrice.setText("¥" + detailBean.getOriginal_price());
                    detailBean.setDelivery_price(object.optString("delivery_price"));
                    tvShopSendPrice.setText("¥" + detailBean.getDelivery_price());
                    detailBean.setDeduct_price(object.optString("deduct_price"));
                    tvLoseMoney.setText("¥" + detailBean.getDeduct_price());
                    detailBean.setTotal_price(object.optString("total_price"));
                    tvSureMoney.setText("¥" + detailBean.getTotal_price());
                    detailBean.setOrder_no(object.optString("order_no"));
                    tvOrderNumber.setText(detailBean.getOrder_no());
                    detailBean.setCreate_time(object.optString("create_time"));
                    tvCreateTime.setText(detailBean.getCreate_time());
                    detailBean.setPayment_type(object.optString("payment_type"));
                    tvPayType.setText(detailBean.getPayment_type());
                    detailBean.setPick_code(object.optString("pick_code"));
                    if (detailBean.getPick_code()!=null&&!detailBean.getPick_code().equals("")){
                        tvPickCodeName.setVisibility(View.VISIBLE);
                        tvPickCode.setVisibility(View.VISIBLE);
                        tvPickCode.setText(detailBean.getPick_code());
                    }else {
                        tvPickCodeName.setVisibility(View.GONE);
                        tvPickCode.setVisibility(View.GONE);
                    }
                    detailBean.setPay_time(object.optString("pay_time"));
                    tvPayTime.setText(detailBean.getPay_time());
                    if (object.opt("latitude") instanceof Boolean) {
                        detailBean.setLatitude(0);
                    } else {
                        detailBean.setLatitude(object.optDouble("latitude"));
                    }
                    if (object.opt("longitude") instanceof Boolean) {
                        detailBean.setLongitude(0);
                    } else {
                        detailBean.setLongitude(object.optDouble("longitude"));
                    }

                    List<ShopDetailBean.OrderItemsBean> order_items = new ArrayList<>();
                    for (int i = 0; i < object.optJSONArray("order_items").length(); i++) {
                        JSONObject orderItems = object.optJSONArray("order_items").getJSONObject(i);
                        ShopDetailBean.OrderItemsBean orderItemsBean = new ShopDetailBean.OrderItemsBean();
                        orderItemsBean.setName(orderItems.optString("name"));
                        orderItemsBean.setAfter_sale_format(orderItems.optString("after_sale_format"));
                        orderItemsBean.setCan_after_sale(orderItems.optInt("can_after_sale"));
                        orderItemsBean.setComment(orderItems.optInt("comment"));
                        orderItemsBean.setImage(orderItems.optString("image"));
                        orderItemsBean.setNumber(orderItems.optInt("number"));
                        orderItemsBean.setOrder_item_id(orderItems.optInt("order_item_id"));
                        orderItemsBean.setPrice(orderItems.optString("price"));
                        orderItemsBean.setProduct_id(orderItems.optInt("product_id"));
                        orderItemsBean.setUnit_name(orderItems.optString("unit_name"));
                        order_items.add(orderItemsBean);
                    }
                    detailBean.setOrder_items(order_items);
                    if (detailBean.getOrder_items().size() > 0) {
                        orderItems = detailBean.getOrder_items();
                        shopListAdapter.addData(orderItems);
                    }

                } else {
                    Utils.phpIsLogin(OrderDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.img_phone, R.id.btn_return_money_progress, R.id.img_navigation, R.id.tv_return_money, R.id.tv_notify_business, R.id.tv_call_person})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_phone:
                Intent intentPhone = new Intent(Intent.ACTION_DIAL);
                intentPhone.setData(Uri.parse("tel:" + tel));
                startActivity(intentPhone);
                break;
            case R.id.btn_return_money_progress:
                break;
            case R.id.tv_call_person:
//                Utils.callPerson(this, "是否拨打电话0855-8552588", "0855-8552588");
                Intent intentCall = new Intent();
                intentCall.setClass(mContext, UserAgreementActivity.class);
                intentCall.putExtra("name", "在线客服");
                intentCall.putExtra("webContent", "http://p.qiao.baidu.com/cps/chat?siteId=14492024&userId=29966678&cp=&cr=APP&cw=");
                startActivity(intentCall);
                break;
            case R.id.img_navigation:
                requestBaidu();
                break;
            case R.id.tv_return_money:
                if (tvReturnMoney.getText().toString().equals("取消订单")) {
                    initSurePop(5, id);
                } else if (tvReturnMoney.getText().toString().equals("删除订单")) {
                    initSurePop(6, id);
                }
                break;
            case R.id.tv_notify_business:
                if (tvNotifyBusiness.getText().toString().equals("提醒商家")) {
                    Toast.makeText(mContext, "提醒商家成功！", Toast.LENGTH_SHORT).show();
                } else if (tvNotifyBusiness.getText().toString().equals("付款")) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ShopPayActivity.class);
                    intent.putExtra("order_id", id);
                    if (detailBean != null) {
                        BigDecimal bigDecimal = new BigDecimal(detailBean.getTotal_price()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        intent.putExtra("currentMoney", bigDecimal.doubleValue());
                        if (detailBean.getOrder_items().size() > 0) {
                            intent.putExtra("shopName", detailBean.getOrder_items().get(0).getName());
                            intent.putExtra("shopImg", detailBean.getOrder_items().get(0).getImage());
                        }
                    } else {
                        Toast.makeText(mContext, "商品出错,请稍后再试！", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_ORDER, intent);
                        finish();
                        return;
                    }
                    startActivityForResult(intent, REQUEST_PAY);
                } else if (tvNotifyBusiness.getText().toString().equals("确认收货")) {
                    initSurePop(0, id);
                }
                break;
        }
    }

    private void initSurePop(final int type, final long order_id) {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.login_pop
                , null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView tvMessageTitle = (TextView) popView.findViewById(R.id.tv_message_title);
        TextView not = (TextView) popView.findViewById(R.id.no);
        if (type == 1) {
            tvMessageTitle.setText("是否确认已自提？");
        } else if (type == 5) {
            tvMessageTitle.setText("是否取消订单？");
        } else if (type == 6) {
            tvMessageTitle.setText("确认删除订单？");
        } else {
            tvMessageTitle.setText("是否确认收货？");
        }
        surePopWindow = new PopupWindow(popView, Utils.dip2px(mContext, 256), ViewGroup.LayoutParams.WRAP_CONTENT);
        yes.setOnClickListener(v -> requestOrderChange(type, order_id));
        not.setOnClickListener(v -> surePopWindow.dismiss());
        surePopWindow.setFocusable(true);
        surePopWindow.setOutsideTouchable(true);
        Utils.darkenBackground(0.8f, this);
        if (!surePopWindow.isShowing()) {
            surePopWindow.showAtLocation(findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }

        surePopWindow.setOnDismissListener(() -> {
            Utils.darkenBackground(1f, OrderDetailActivity.this);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
    }


    private void requestOrderChange(int type, long order_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("order_id", order_id);
        if (type == 5) {
            map.put("deal_type", "cancel");
        } else if (type == 6) {
            map.put("deal_type", "delete");
        } else {
            map.put("deal_type", "confirm");
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.orderHandle(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                surePopWindow.dismiss();
                JSONObject object = new JSONObject(string);
                boolean status = object.optBoolean("status");
                if (status) {
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_ORDER, getIntent());
                    finish();
                } else {
                    int type = object.optInt("type");
                    setResult(RESULT_ORDER, getIntent());
                    Utils.phpIsLogin(OrderDetailActivity.this, type, object.optString("message"));
                }
            }

            @Override
            public void fail() {
                surePopWindow.dismiss();
            }
        });
    }


    private void requestBaidu() {


//构建导航参数
//        NaviParaOption para = new NaviParaOption()
//                .startPoint(startPoint)
//                .endPoint(endPoint)
//                .startName("天安门")
//                .endName("百度大厦");
//调起百度地图
        if (detailBean.getLatitude() == 0 && detailBean.getLongitude() == 0) {
            Toast.makeText(mContext, "暂无店铺位置！", Toast.LENGTH_SHORT).show();
            return;
        }
        //定义起终点坐标（天安门和百度大厦）
        LatLng startPoint = new LatLng(latitude, longitude);
        LatLng endPoint = new LatLng(detailBean.getLatitude(), detailBean.getLongitude());
        try {
//            boolean navi = BaiduMapNavigation.openBaiduMapWalkNavi(para, this);
            if (MapUtils.haveGaodeMap(mContext)) {
                MapUtils.openGaodeMap(mContext, endPoint, detailBean.getAddress());
            } else if (MapUtils.haveBaiduMap(mContext)) {
                MapUtils.openBaiduMap(mContext, endPoint, "");
            } else if (MapUtils.haveTencentMap(mContext)) {
                MapUtils.openTentcentMap(mContext, endPoint, "");
            } else {
                MapUtils.openBrowserMap(mContext, startPoint, "", endPoint, "");
            }
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            //调起失败的处理
        }
        BaiduMapNavigation.finish(this);
    }

    class ShopListAdapter extends BaseAdapter {
        List<ShopDetailBean.OrderItemsBean> orderItems;

        public ShopListAdapter(List<ShopDetailBean.OrderItemsBean> orderItems) {
            this.orderItems = orderItems;
        }

        public void addData(List<ShopDetailBean.OrderItemsBean> orderItems) {
            this.orderItems = orderItems;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return orderItems.size();
        }

        @Override
        public Object getItem(int position) {
            return orderItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            try {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.shop_list, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tvShopName.setText(orderItems.get(position).getName());
                holder.tvShopCount.setText("x" + orderItems.get(position).getNumber());
                holder.tvShopPrice.setText("¥" + orderItems.get(position).getPrice());
                holder.tvReturnMoneyName.setText(orderItems.get(position).getAfter_sale_format());
                PictureHelper.setImageView(mContext, orderItems.get(position).getImage(), holder.imgShop, R.drawable.img_loading);
                if (orderStatus == 3 && orderItems.get(position).getComment() == 0) {
                    holder.tvShopEvaluate.setVisibility(View.VISIBLE);
                } else {
                    holder.tvShopEvaluate.setVisibility(View.GONE);
                }
                if (orderItems.get(position).getUnit_name() != null && !orderItems.get(position).getUnit_name().equals("")) {
                    holder.tvShopType.setVisibility(View.VISIBLE);
                    holder.tvShopType.setText(orderItems.get(position).getUnit_name());
                } else {
                    holder.tvShopType.setVisibility(View.GONE);
                }
                if (orderStatus == 1 && orderItems.get(position).getCan_after_sale() == 1 || orderStatus == 2 && orderItems.get(position).getCan_after_sale() == 1 || orderStatus == 3 && orderItems.get(position).getCan_after_sale() == 1) {
//                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) holder.tvReturnType.getLayoutParams();
//                    if (holder.tvShopEvaluate.getVisibility() == View.GONE) {
//                        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                        holder.tvReturnType.setLayoutParams(rl);
//                    } else {
//                        rl.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                        holder.tvReturnType.setLayoutParams(rl);
//                    }
                    holder.llOrderType.setVisibility(View.VISIBLE);
                    holder.tvReturnType.setVisibility(View.VISIBLE);
                } else {
                    holder.llOrderType.setVisibility(View.GONE);
                    holder.tvReturnType.setVisibility(View.GONE);
                }
                holder.tvShopEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShopFinishEvaluateActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                holder.tvReturnType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (orderStatus == 1) {
                            Intent intent = new Intent(mContext, ReplacementDetailActivity.class);
                            intent.putExtra("name", "申请退款");
                            intent.putExtra("isNoPay", true);
                            intent.putExtra("order_id", id);
                            intent.putExtra("order_item_id", orderItems.get(position).getOrder_item_id());
                            mContext.startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, RequestReplacementActivity.class);
                            intent.putExtra("order_id", id);
                            intent.putExtra("order_item_id", orderItems.get(position).getOrder_item_id());
                            mContext.startActivity(intent);
                        }


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
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
            @BindView(R.id.tv_shop_evaluate)
            TextView tvShopEvaluate;
            @BindView(R.id.tv_shop_type)
            TextView tvShopType;
            @BindView(R.id.ll_order_type)
            LinearLayout llOrderType;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            for (int i = 0; i < grantResults.length; i++) {

                //判断权限的结果，如果点击不在提示
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                        RequestPermissionUtils.showShortCut(getActivity(), "开启定位需要位置信息权限！", rlAddress);
                    }
                }
            }
            if (isAllGranted) {
                mLocationClient.start();// 定位SDK
            } else {
                Toast.makeText(mContext, "权限开启失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION})) {
                mLocationClient.start();// 定位SDK
            } else {
                Toast.makeText(mContext, "设置位置信息权限失败！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_PAY && resultCode != 0) {
            setResult(RESULT_ORDER, getIntent());
            finish();
        }
    }
}
