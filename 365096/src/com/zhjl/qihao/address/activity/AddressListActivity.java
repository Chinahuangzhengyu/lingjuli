package com.zhjl.qihao.address.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.address.adapter.AddressAdapter;
import com.zhjl.qihao.util.NewHeaderBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListActivity extends VolleyBaseActivity {

    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.btn_add_address)
    Button btnAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,"地址管理",this);
        btnAddAddress.setOnClickListener(this);
        rvAddress.setLayoutManager(new LinearLayoutManager(mContext));
        rvAddress.setAdapter(new AddressAdapter(this));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_add_address:
                Intent intent = new Intent(this,AddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
