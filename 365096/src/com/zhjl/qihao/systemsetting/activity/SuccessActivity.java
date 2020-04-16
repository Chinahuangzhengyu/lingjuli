package com.zhjl.qihao.systemsetting.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_success_type)
    TextView tvSuccessType;
    @BindView(R.id.tv_back)
    TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);
        String successType = getIntent().getStringExtra("successType");
        tvSuccessType.setText(successType);
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
