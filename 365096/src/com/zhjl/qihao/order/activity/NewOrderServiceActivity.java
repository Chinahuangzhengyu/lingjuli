package com.zhjl.qihao.order.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.order.adapter.OrderServiceAdapter;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.ServiceListBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class NewOrderServiceActivity extends VolleyBaseActivity {
    @BindView(R.id.xrv_order_service_list)
    XRecyclerView xrvOrderServiceList;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isRefresh;
    private int totalPage;
    private List<ServiceListBean.ProductAftersaleBean> productAfterSale = new ArrayList<>();
    private OrderServiceAdapter orderServiceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_service);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "售后", this);
        xrvOrderServiceList.setLayoutManager(new LinearLayoutManager(mContext));
        orderServiceAdapter = new OrderServiceAdapter(mContext, productAfterSale);
        xrvOrderServiceList.setAdapter(orderServiceAdapter);
        xrvOrderServiceList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                pageIndex++;
                if (pageIndex <= totalPage) {
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvOrderServiceList.getFootView().setPadding(0, top, 0, bottom);
                    xrvOrderServiceList.setNoMore(true);
                }
            }
        });
        initData();
    }

    private void initData() {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("page", pageIndex);
        map.put("limit", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.getServiceList(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                if (object.optBoolean("status")) {
                    Gson gson = new Gson();
                    ServiceListBean serviceListBean = gson.fromJson(string, ServiceListBean.class);
                    totalPage = serviceListBean.getTotal_page();
                    if (totalPage > 1) {
                        xrvOrderServiceList.setLoadingMoreEnabled(true);
                    }
                    if (isRefresh) {
                        productAfterSale.clear();
                        productAfterSale.addAll(serviceListBean.getProduct_aftersale());
                    } else {
                        productAfterSale.addAll(serviceListBean.getProduct_aftersale());
                    }
                    orderServiceAdapter.addData(productAfterSale);
                } else {
                    Utils.phpIsLogin(NewOrderServiceActivity.this, object.optInt("type"), object.optString("message"));
                }
                if (isRefresh) {
                    xrvOrderServiceList.refreshComplete();
                } else {
                    xrvOrderServiceList.loadMoreComplete();
                }
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvOrderServiceList.refreshComplete();
                } else {
                    xrvOrderServiceList.loadMoreComplete();
                }
            }
        });
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
