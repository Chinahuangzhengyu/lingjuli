package com.zhjl.qihao.propertyservicepay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.PropertyPayTypeListBean;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


public class PropertyPayActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_property_pay)
    TextView tvPropertyPay;
    @BindView(R.id.tv_car_pay)
    TextView tvCarPay;
    private boolean isCanPropertyPay;
    private int propertyId;
    private boolean isCanCarPay;
    private int carId;
    private ArrayList<UserRoomListBean.DataBean> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_pay);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "物业缴费", this);
        initData();
        initUserRoomData();
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
                } else {
                    Toast.makeText(mContext, "获取入住房间失败！", Toast.LENGTH_SHORT).show();
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
                            isCanCarPay = true;
                            carId = result.getData().get(i).getCateId();
                        } else if (result.getData().get(i).getCateName().equals("物业费")) {
                            isCanPropertyPay = true;
                            propertyId = result.getData().get(i).getCateId();
                        }
                    }
                } else {
                    Toast.makeText(mContext, "该小区未开放缴费功能！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail() {

            }
        });

    }

    @OnClick({R.id.rl_property_pay, R.id.rl_car_pay, R.id.iv_back})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_property_pay:
                initData();
                if (isCanPropertyPay && propertyId != 0 && data != null) {
                    intent.setClass(mContext, PropertyPayDetailActivity.class);
                    intent.putExtra("propertyId", propertyId);
                    intent.putParcelableArrayListExtra("roomList",data);
                    startActivity(intent);
                }
                break;
            case R.id.rl_car_pay:
                initData();
                if (isCanCarPay && carId != 0 && data != null) {
                    intent.setClass(mContext, CarPayDetailActivity.class);
                    intent.putExtra("carId", carId);
                    intent.putParcelableArrayListExtra("roomList",data);
                    startActivity(intent);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
