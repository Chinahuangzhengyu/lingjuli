package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressSureActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_request)
    Button btnRequest;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private String myPhone = "0855-8552588";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_sure);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "住所认证", this);
        String phone = getIntent().getStringExtra("phone");
        tvMessage.setText("您这套房屋已绑定，绑定的手机号为" + phone + ",如有疑问您可以致电" + myPhone);
        String message = tvMessage.getText().toString().trim();
        String[] split = message.split("电");
        SpannableString string = new SpannableString(message);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FFB126"));
        string.setSpan(colorSpan, split[0].length(), message.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvMessage.setText(string);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_request, R.id.iv_back, R.id.tv_cancel, R.id.tv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.tv_message:
                Utils.callPerson(this,"是否拨打电话0855-8552588",myPhone);
                break;
            case R.id.btn_request:
                Intent intent = new Intent(mContext, AppealAgainstActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }
}
