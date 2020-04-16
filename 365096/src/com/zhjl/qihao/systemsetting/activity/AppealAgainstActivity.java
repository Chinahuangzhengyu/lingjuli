package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppealAgainstActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_against)
    TextView tvAgainst;
    @BindView(R.id.tv_off_against)
    TextView tvOffAgainst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_against);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,"我要申诉",this);
    }

    @OnClick({R.id.tv_against, R.id.tv_off_against,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_against:
                Intent intent = new Intent(mContext, OnLineAgainstActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_off_against:
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
