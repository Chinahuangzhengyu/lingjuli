package com.zhjl.qihao.homemanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.ButtonRepect;
import com.zhjl.qihao.homemanage.api.HomeManageInterface;
import com.zhjl.qihao.homemanage.bean.HomeManageListBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class HomeManageDetailActivity extends VolleyBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_home_name)
    TextView tvHomeName;
    @BindView(R.id.tv_phone_value)
    TextView tvPhoneValue;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_door_number_value)
    TextView tvDoorNumberValue;
    @BindView(R.id.tv_user_type_value)
    TextView tvUserTypeValue;
    @BindView(R.id.rb_sure)
    RadioButton rbSure;
    @BindView(R.id.rb_cancel)
    RadioButton rbCancel;
    @BindView(R.id.rg_is_sure_value)
    RadioGroup rgIsSureValue;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private int isAgree = 1;    //默认同意
    private String residentId = "";
    public static final int RESULT_HOME_AGREE_CODE = 0x22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_manage_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "住所绑定", this);
        HomeManageListBean.DataBean data = getIntent().getParcelableExtra("data");
        if (data != null) {
            residentId = data.getResidentId();
            if (data.getResidentType().equals("1")) {
                tvUserTypeValue.setText("业主");
            } else if (data.getResidentType().equals("2")) {
                tvUserTypeValue.setText("家人");
            } else {
                tvUserTypeValue.setText("租客");
            }
            if (data.getStatus() == 0) {
                tvName.setText("住所绑定审核中");
            } else if (data.getStatus() == 1) {
                tvName.setText("住所绑定成功");
            } else {
                tvName.setText("住所绑定审核未通过");
            }
            tvCityName.setText(data.getSmallCommunityName());
            tvDoorNumberValue.setText(data.getRoomName());
            tvPhoneValue.setText(data.getMobile());
            tvHomeName.setText(data.getName());

        }

        rgIsSureValue.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == rbSure.getId()) {
                isAgree = 1;
            } else {
                isAgree = 2;
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                boolean fastClick = ButtonRepect.isFastClick();
                if (!fastClick) {
                    return;
                }
                requestSure();
                break;
        }
    }

    /**
     * 审核同意
     */
    private void requestSure() {
        HomeManageInterface manageInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(HomeManageInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("residentId", residentId);
        map.put("result", isAgree);
        Call<ResponseBody> call = manageInterface.residentApply(ParamForNet.put(map));
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
//                intent.putExtra("position", getIntent().getIntExtra("position", -1));
                setResult(RESULT_HOME_AGREE_CODE, intent);
                finish();
            }

            @Override
            public void fail() {

            }
        });
    }
}
