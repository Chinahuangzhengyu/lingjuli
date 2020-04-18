package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.systemsetting.adapter.HomeAddressBindingAdapter;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class HomeAddressBindingActivity extends VolleyBaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_add_home_address_binding)
    TextView tvAddHomeAddressBinding;
    @BindView(R.id.xrv_home_address_binding)
    XRecyclerView xrvHomeAddressBinding;
    private HomeAddressBindingAdapter homeAddressBindingAdapter;
    private ArrayList<UserRoomListBean.DataBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_address_binding);
        ButterKnife.bind(this);
        xrvHomeAddressBinding.setLayoutManager(new LinearLayoutManager(mContext));
        homeAddressBindingAdapter = new HomeAddressBindingAdapter(mContext,data);
        xrvHomeAddressBinding.setAdapter(homeAddressBindingAdapter);
        xrvHomeAddressBinding.setPullRefreshEnabled(false);
        homeAddressBindingAdapter.setSetOnItemClickListener((view, position) -> {
            Intent intent = new Intent(mContext,MyHomeAddressActivity.class);
            intent.putExtra("residentId",data.get(position).getResidentId());
            intent.putExtra("status",data.get(position).getStatus());
            intent.putExtra("data",data.get(position));
            intent.putExtra("isHomeList",true);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        RequestBody body = ParamForNet.put(new HashMap<String, Object>());
        Call<ResponseBody> call = propertyPayInterface.userRoomList(body);
        activityRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                data = result.getData();
                homeAddressBindingAdapter.addData(data);
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_add_home_address_binding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_add_home_address_binding:
                Intent intent = new Intent(mContext,AddHomeAddressBindingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
