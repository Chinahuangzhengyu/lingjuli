package com.zhjl.qihao.propertyservicepay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.propertyservicecomplaint.activity.PropertyComplaintActivity;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.propertyservicerepair.activity.PropertyRepairActivity;
import com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity.BINDING_ADDRESS_SUCCESS;

public class PropertyMainActivity extends VolleyBaseActivity {
    @BindView(R.id.rl_pay)
    RelativeLayout rlPay;
    @BindView(R.id.rl_repair)
    RelativeLayout rlRepair;
    @BindView(R.id.rl_complaint)
    RelativeLayout rlComplaint;
    List<LoginBean.DataBean.UserInfoBean.UserRoomsBean> rooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_service);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "物业服务", this);
        initData();
    }

    private void initData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Call<ResponseBody> call = propertyPayInterface.userRoomList(ParamForNet.put(new HashMap<String, Object>()));
        activityRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                if (result.getData().size() > 0) {
                    rooms.clear();
                    for (int i = 0; i < result.getData().size(); i++) {
                        LoginBean.DataBean.UserInfoBean.UserRoomsBean roomsBean = new LoginBean.DataBean.UserInfoBean.UserRoomsBean();
                        roomsBean.setRoomName(result.getData().get(i).getRoomName());
                        roomsBean.setRoomId(result.getData().get(i).getRoomId());
                        roomsBean.setSmallCommunityName(result.getData().get(i).getSmallCommunityName());
                        rooms.add(roomsBean);
                    }
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.rl_pay, R.id.rl_repair, R.id.rl_complaint, R.id.iv_back})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        boolean pop;
        switch (view.getId()) {
            case R.id.rl_pay:
                pop = isPop();
                if (pop) {
                    return;
                }
                intent.setClass(mContext, PropertyPayActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_repair:
                pop = isPop();
                if (pop) {
                    return;
                }
                intent.setClass(mContext, PropertyRepairActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_complaint:
                pop = isPop();
                if (pop) {
                    return;
                }
                intent.setClass(mContext, PropertyComplaintActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public boolean isPop() {
        if (rooms.size() == 0) {
            PopWindowUtils.getInstance().show("您还没有绑定住所，点击绑定吧！", this);
            PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                Intent intent = new Intent();
                intent.setClass(mContext, AddHomeAddressBindingActivity.class);
                intent.putExtra("isPopAddress", true);
                startActivityForResult(intent,0x167);
            });
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BINDING_ADDRESS_SUCCESS) {
            initData();
        }
    }
}
