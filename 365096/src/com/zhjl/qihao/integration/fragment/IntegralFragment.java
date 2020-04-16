package com.zhjl.qihao.integration.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.query.In;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.integration.activity.HelpNoticeActivity;
import com.zhjl.qihao.integration.adapter.BalanceAdapter;
import com.zhjl.qihao.integration.api.IntegralInterface;
import com.zhjl.qihao.integration.bean.IntegralListBean;
import com.zhjl.qihao.util.UrlChangeUtils;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class IntegralFragment extends VolleyBaseFragment {
    @BindView(R.id.tv_help_notice)
    TextView tvHelpNotice;
    @BindView(R.id.xrv_integral)
    XRecyclerView xrvIntegral;
    Unbinder unbinder;
    private View view;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isRefresh = true;
    private int totalPage;
    private List<IntegralListBean.NbitListBean> integralList = new ArrayList<>();
    private BalanceAdapter balanceAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_integral, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        xrvIntegral.setLayoutManager(new LinearLayoutManager(mContext));
        balanceAdapter = new BalanceAdapter(mContext, integralList);
        xrvIntegral.setAdapter(balanceAdapter);
        tvHelpNotice.setOnClickListener(this);
        xrvIntegral.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh =true;
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
                    xrvIntegral.getFootView().setPadding(0, top, 0, bottom);
                    xrvIntegral.setNoMore(true);
                }
            }
        });
        initData();
        return view;
    }

    private void initData() {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("page", pageIndex);
        map.put("limit", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.getIntegral(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                boolean status = object.optBoolean("status");
                Gson gson = new Gson();
                if (status){
                    IntegralListBean integralListBean = gson.fromJson(string, IntegralListBean.class);
                    totalPage = integralListBean.getInfo().getTotal_page();
                    getData.data(integralListBean.getNbit_number()+"个");
                    if (totalPage>1){
                        xrvIntegral.setLoadingMoreEnabled(true);
                    }
                    if (isRefresh) {
                        integralList.clear();
                    }
                    integralList.addAll(integralListBean.getNbit_list());
                    balanceAdapter.addData(integralList);
                }else {
                    Utils.phpIsLogin(getActivity(),object.optInt("type"),object.optString("message"));
                }
                if (isRefresh) {
                    xrvIntegral.refreshComplete();
                } else {
                    xrvIntegral.loadMoreComplete();
                }
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvIntegral.refreshComplete();
                } else {
                    xrvIntegral.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_help_notice:
                Intent intent = new Intent(mContext, UserAgreementActivity.class);
                intent.putExtra("name","帮助说明");
                intent.putExtra("webContent", UrlChangeUtils.API_HOST+UrlChangeUtils.JAVA_PORT_NUMBER+"/static/regular/n_bit_regular.html");
                startActivity(intent);
                break;
        }
    }
    private GetData getData;

    public void setGetData(GetData getData) {
        this.getData = getData;
    }

    public interface GetData{
        void data(String sum);
    }
}
