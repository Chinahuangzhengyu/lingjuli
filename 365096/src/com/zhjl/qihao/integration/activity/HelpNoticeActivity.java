package com.zhjl.qihao.integration.activity;

import android.os.Bundle;
import android.view.View;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

public class HelpNoticeActivity extends VolleyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_notice);
        NewHeaderBar.createCommomBack(this,"帮助说明",this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
