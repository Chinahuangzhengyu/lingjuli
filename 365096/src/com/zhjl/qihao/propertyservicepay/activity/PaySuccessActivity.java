package com.zhjl.qihao.propertyservicepay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.freshshop.activity.ShopCarActivity;
import com.zhjl.qihao.freshshop.activity.ShopDetailActivity;
import com.zhjl.qihao.order.activity.OrderDetailActivity;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.PropertyPayTypeListBean;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PaySuccessActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_back_main)
    TextView tvBackMain;
    @BindView(R.id.tv_select_order)
    TextView tvSelectOrder;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    private int carId;
    private ArrayList<UserRoomListBean.DataBean> data;
    private int propertyId;
    private String from;
    private boolean formCar;
    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_sucess);
        ButterKnife.bind(this);
        String payType = getIntent().getStringExtra("payType");
        DecimalFormat df = new DecimalFormat("0.00");   //保留2位小数
        double price = getIntent().getDoubleExtra("price", 0);
        from = getIntent().getStringExtra("from");
        tvPayType.setText(payType);
        tvPrice.setText("¥" + df.format(price));
        if (from.equals("物业")) {
            initData();
            initUserRoomData();
        } else {
            orderId = getIntent().getLongExtra("orderId", 0);
            formCar = getIntent().getBooleanExtra("formCar", false);
        }
    }

    private void initUserRoomData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        RequestBody body = ParamForNet.put(new HashMap<String, Object>());
        Call<ResponseBody> call = propertyPayInterface.userRoomList(body);
        activityRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                if (result.getData() != null && result.getData().size() > 0) {
                    data = (ArrayList<UserRoomListBean.DataBean>) result.getData();
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        RequestBody body = ParamForNet.put(new HashMap<String, Object>());
        Call<ResponseBody> call = propertyPayInterface.getPropertyPayTypeList(body);
        activityRequestData(call, PropertyPayTypeListBean.class, new RequestResult<PropertyPayTypeListBean>() {
            @Override
            public void success(PropertyPayTypeListBean result, String message) {
                if (result.getData().size() > 0) {
                    for (int i = 0; i < result.getData().size(); i++) {
                        if (result.getData().get(i).getCateName().equals("停车费")) {
                            carId = result.getData().get(i).getCateId();
                        } else if (result.getData().get(i).getCateName().equals("物业费")) {
                            propertyId = result.getData().get(i).getCateId();
                        }
                    }
                }
            }

            @Override
            public void fail() {

            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @OnClick({R.id.tv_back_main, R.id.tv_select_order, R.id.tv_finish})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_back_main:
                intent.setClass(mContext, RefactorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_select_order:
                if (from.equals("物业")) {
                    backActivity(intent);
                } else {
                    backShopOrderDetail(intent);
                }
                break;
            case R.id.tv_finish:
                if (from.equals("物业")) {
                    backActivity(intent);
                } else {
                    backShopActivity(intent);
                }
                break;
        }
    }

    private void backShopOrderDetail(Intent intent) {
        intent.setClass(mContext, OrderDetailActivity.class);
        intent.putExtra("orderStatus", 1);
        intent.putExtra("id", orderId);
        startActivity(intent);
        finish();
    }

    private void backShopActivity(Intent intent) {
//        if (formCar) {
//            intent.setClass(mContext, ShopCarActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//        } else {
//            intent.setClass(mContext, ShopDetailActivity.class);
//            intent.putExtra("id", orderId);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            if (from.equals("物业")) {
                backActivity(intent);
            } else {
                backShopActivity(intent);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void backActivity(Intent intent) {
        intent.putExtra("isPaySuccess", true);
        if (mSession.isCarPay()) {
            if (data != null && carId != 0) {
                intent.setClass(mContext, CarPayDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("carId", carId);
                intent.putParcelableArrayListExtra("roomList", data);
                startActivity(intent);
                finish();
            } else {
                intent.setClass(mContext, RefactorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        } else {
            if (data != null && propertyId != 0) {
                intent.setClass(mContext, PropertyPayDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("propertyId", propertyId);
                intent.putParcelableArrayListExtra("roomList", data);
                startActivity(intent);
                finish();
            } else {
                intent.setClass(mContext, RefactorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

        }
    }
}
