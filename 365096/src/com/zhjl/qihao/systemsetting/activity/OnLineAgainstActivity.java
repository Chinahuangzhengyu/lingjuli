package com.zhjl.qihao.systemsetting.activity;

import android.os.Bundle;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

public class OnLineAgainstActivity extends VolleyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_against);
        NewHeaderBar.createCommomBack(this,"在线申诉",this);
    }
}
