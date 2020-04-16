package com.zhjl.qihao.abmine;

import android.os.Bundle;
import android.view.View;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

public class MyCollectionActivity extends VolleyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        String name = getIntent().getStringExtra("name");
        NewHeaderBar.createCommomBack(this,name,this);
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
