package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.systemsetting.api.SettingInterface;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MyHomeAddressActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_home_name)
    TextView tvHomeName;
    @BindView(R.id.tv_number_update)
    TextView tvNumberUpdate;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_small_community)
    TextView tvSmallCommunity;
    @BindView(R.id.tv_home_number)
    TextView tvHomeNumber;
    @BindView(R.id.tv_home_type)
    TextView tvHomeType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.btn_update_home)
    Button btnUpdateHome;
    private String residentId = ""; //入住id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_address);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "我的住所", "解绑", this);
        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.new_theme_color));
        tvRight.setTextSize(18);
        residentId = getIntent().getStringExtra("residentId");
        boolean isHomeList = getIntent().getBooleanExtra("isHomeList", false);
        if (isHomeList) {
            UserRoomListBean.DataBean data = getIntent().getParcelableExtra("data");
            if (data != null) {
                tvHomeName.setText(data.getName());
                tvNumber.setText(data.getMobile());
                tvSmallCommunity.setText(data.getSmallCommunityName());
                tvHomeNumber.setText(data.getRoomName());
                if (data.getResidentType().equals("1")) {
                    tvHomeType.setText("业主");
                } else if (data.getResidentType().equals("2")) {
                    tvHomeType.setText("家庭成员");
                } else {
                    tvHomeType.setText("租户");
                }
                if (data.getStatus() == 0) {
                    tvName.setText("住所正在审核中");
                } else {
                    tvName.setText("住所绑定成功");
                }
            }
        } else {
            String name = getIntent().getStringExtra("name");
            String smallCommunity = getIntent().getStringExtra("smallCommunity");
            String room = getIntent().getStringExtra("room");
            String userType = getIntent().getStringExtra("userType");
            tvHomeName.setText(name);
            tvNumber.setText(mSession.getRegisterMobile());
            tvSmallCommunity.setText(smallCommunity);
            tvHomeNumber.setText(room);
            tvHomeType.setText(userType);
        }
    }

    @OnClick({R.id.tv_number_update, R.id.iv_back, R.id.tv_right,R.id.btn_update_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_number_update:
                Intent intent = new Intent(mContext, UpdateNumberActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                PopWindowUtils.getInstance().show("您是否确认解绑？", this);
                PopWindowUtils.getInstance().setSetYesOnClickListener(() -> requestUnBuilding());
                break;
            case R.id.btn_update_home:
                Intent intent1 = new Intent(mContext,AddHomeAddressBindingActivity.class);
                startActivity(intent1);
                break;
        }
    }

    /**
     * 解绑房间
     */
    private void requestUnBuilding() {
        SettingInterface settingInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("residentId", residentId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = settingInterface.unBuildingRoom(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                Toast.makeText(mContext, "解绑成功！", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail() {

            }
        });
    }

}
