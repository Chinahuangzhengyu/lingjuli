package com.zhjl.qihao.opendoor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.bean.OpenDoorListBean;
import com.zhjl.qihao.abrefactor.bean.SmallCommunityListBean;
import com.zhjl.qihao.address.adapter.HomeAddressAdapter;
import com.zhjl.qihao.opendoor.adapter.OpenDoorAddressAdapter;
import com.zhjl.qihao.opendoor.api.OpenInterface;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class OpenDoorAddressChooseActivity extends VolleyBaseActivity {
    @BindView(R.id.xrv_open_door_address_list)
    XRecyclerView xrvOpenDoorAddressList;
    private ArrayList<OpenDoorListBean.DataBean> data = new ArrayList<>();
    private OpenDoorAddressAdapter openDoorAddressAdapter;
    public static final int OPEN_DOOR_ADDRESS_RESULT = 0x112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_door_address_choose);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "选择地址", this);
        int position = getIntent().getIntExtra("position", 0);
        xrvOpenDoorAddressList.setLayoutManager(new LinearLayoutManager(mContext));
        ArrayList<OpenDoorListBean.DataBean> list = getIntent().getParcelableArrayListExtra("data");
        if (list != null) {
            data.addAll(list);
        }
        openDoorAddressAdapter = new OpenDoorAddressAdapter(mContext, data, position);
        xrvOpenDoorAddressList.setAdapter(openDoorAddressAdapter);
        xrvOpenDoorAddressList.setPullRefreshEnabled(false);
        openDoorAddressAdapter.setSetOnItemClickListener(new OpenDoorAddressAdapter.SetOnItemClickListener() {
            @Override
            public void onClick(View view, int position, TextView tv) {
                for (int i = 0; i < xrvOpenDoorAddressList.getChildCount(); i++) {
                    if (i == position) {
                        RelativeLayout rl = (RelativeLayout) xrvOpenDoorAddressList.getChildAt(i + 1);
                        TextView textView = (TextView) rl.getChildAt(2);
                        textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.car_choose_click));
                        Intent intent = new Intent();
                        intent.putExtra("position", position);
                        intent.putExtra("areaId", data.get(position).getAreaId());
                        intent.putExtra("Id", data.get(position).getDoorAccessId());
                        setResult(OPEN_DOOR_ADDRESS_RESULT, intent);
                        finish();
                    } else {
                        if (xrvOpenDoorAddressList.getChildAt(i + 1) instanceof RelativeLayout) {
                            RelativeLayout rl = (RelativeLayout) xrvOpenDoorAddressList.getChildAt(i + 1);
                            TextView textView = (TextView) rl.getChildAt(2);
                            textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.car_choose));
                        }
                    }
                }
            }
        });
        initData();
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
