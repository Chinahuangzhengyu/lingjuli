package com.zhjl.qihao.abmine;

import android.os.Bundle;
import android.view.View;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

public class CompanyInfoActivity extends VolleyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        NewHeaderBar.createCommomBack(this,"公司简介",this);
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
