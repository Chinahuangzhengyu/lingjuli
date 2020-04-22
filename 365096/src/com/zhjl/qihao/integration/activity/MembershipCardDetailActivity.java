package com.zhjl.qihao.integration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.integration.api.IntegralInterface;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.integration.utils.RequestUtils;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MembershipCardDetailActivity extends VolleyBaseActivity {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_card_id)
    TextView tvCardId;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_sum_pay_money)
    TextView tvSumPayMoney;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.sb_default_card)
    SwitchButton sbDefaultCard;
    private PopupWindow removePop;
    private String cardId;
    private boolean isOpen;
    private boolean isUpdate;
    private IntegralInterface integralInterface;
    public static final int RESULT_CARD_CODE = 0x5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_card_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "会员卡详情", "解绑", this);
        if (savedInstanceState != null) {
            cardId = savedInstanceState.getString("cardId");
        } else {
            cardId = getIntent().getStringExtra("cardId");
        }
        integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        initView();
        sbDefaultCard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && !isOpen) {
                settingDefaultCard();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("cardId", cardId);
    }

    private void initData() {
        Call<ResponseBody> call = RequestUtils.getCardDetail(mSession.getmToken(), cardId, integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                if (object.optBoolean("status")) {
                    String cardId = object.optString("card_id");
                    String name = object.optString("name");
                    String mobile = object.optString("mobile");
                    String money = object.optString("money");
                    int aDefault = object.optInt("is_default");
                    if (aDefault == 1) {
                        isOpen = true;
                        sbDefaultCard.setEnabled(false);
                    } else {
                        isOpen = false;
                        sbDefaultCard.setEnabled(true);
                    }
                    sbDefaultCard.setChecked(isOpen);
                    tvCardId.setText(cardId);
                    tvName.setText(name);
                    tvNumber.setText(mobile);
                    tvSumPayMoney.setText(money);
                } else {
                    if (object.optInt("type") == 2) {
                        PopWindowUtils.getInstance().show("会员卡未绑定，是否绑定？", MembershipCardDetailActivity.this);
                        PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                            Intent intent = new Intent(MembershipCardDetailActivity.this, AddMembershipCardActivity.class);
                            startActivity(intent);
                            finish();
                        });
                        PopWindowUtils.getInstance().setNoOnClickListener(() -> {
                            finish();
                        });
                    } else {
                        Utils.phpIsLogin(MembershipCardDetailActivity.this, object.optInt("type"), object.optString("message"));
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
        cardId = intent.getStringExtra("cardId");
    }

    private void initView() {
        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.new_theme_color));
        tvRight.setTextSize(18);
    }

    @OnClick({R.id.iv_back, R.id.btn_update, R.id.tv_right, R.id.tv_forget_password, R.id.sb_default_card})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_back:
                if (isUpdate){
                    setResult(RESULT_CARD_CODE, getIntent());
                }
                finish();
                break;
            case R.id.btn_update:
                if (TextUtils.isEmpty(cardId)) {
                    Toast.makeText(mContext, "卡号出错，无法解绑！", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.setClass(mContext, UpdatePayPasswordActivity.class);
                intent.putExtra("cardId", cardId);
                startActivity(intent);
                break;
            case R.id.tv_right:
                if (TextUtils.isEmpty(cardId)) {
                    Toast.makeText(mContext, "卡号出错，无法解绑！", Toast.LENGTH_SHORT).show();
                    return;
                }
                removePop();
                break;
            case R.id.tv_forget_password:
                if (TextUtils.isEmpty(cardId)) {
                    Toast.makeText(mContext, "卡号出错，无法修改密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.setClass(mContext, ForgetPayPasswordActivity.class);
                intent.putExtra("cardId", cardId);
                startActivity(intent);
                break;
        }
    }

    private void settingDefaultCard() {
        Call<ResponseBody> call = RequestUtils.settingDefaultCard(mSession.getmToken(), cardId, integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status) {
                    isOpen = true;
                    isUpdate = true;
                    sbDefaultCard.setEnabled(false);
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Utils.phpIsLogin(MembershipCardDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void removePop() {
        View popView = LayoutInflater.from(this).inflate(R.layout.login_pop
                , null);
        TextView yes = popView.findViewById(R.id.yes);
        TextView not = popView.findViewById(R.id.no);
        TextView tvMessageTitle = popView.findViewById(R.id.tv_message_title);
        tvMessageTitle.setText("解除绑定后，您将不能使用会员卡余额消费。");
        if (removePop == null) {
            removePop = new PopupWindow();
        }
        removePop.setWidth(Utils.dip2px(this, 256));
        removePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        removePop.setContentView(popView);
        yes.setOnClickListener(v -> {
            requestRemoveBuilding();
            removePop.dismiss();
        });
        not.setOnClickListener(v -> removePop.dismiss());
        removePop.setFocusable(true);
        removePop.setOutsideTouchable(false);
        Utils.darkenBackground(0.8f, this);
        if (!removePop.isShowing()) {
            removePop.showAtLocation(findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }

        removePop.setOnDismissListener(() -> {
            Utils.darkenBackground(1f, this);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });

    }

    private void requestRemoveBuilding() {
        Call<ResponseBody> call = RequestUtils.unbindCard(mSession.getmToken(), cardId, integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                isUpdate = true;
                if (object.optBoolean("status")) {
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CARD_CODE, getIntent());
                    finish();
                } else {
                    Utils.phpIsLogin(MembershipCardDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (isUpdate){
                setResult(RESULT_CARD_CODE, getIntent());
            }
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
