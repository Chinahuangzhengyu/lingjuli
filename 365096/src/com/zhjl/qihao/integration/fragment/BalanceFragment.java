package com.zhjl.qihao.integration.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.integration.adapter.BalanceAdapter;
import com.zhjl.qihao.integration.api.IntegralInterface;
import com.zhjl.qihao.integration.bean.BalanceListBean;
import com.zhjl.qihao.integration.utils.RequestUtils;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class BalanceFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_balance)
    XRecyclerView xrvBalance;
    Unbinder unbinder;
    private View view;
    private int pageIndex = 1;
    private boolean isRefresh = true;
    private List<BalanceListBean.SpendListBean> balanceList = new ArrayList<>();
    private int totalPage;
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
            view = inflater.inflate(R.layout.fragment_balance, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        xrvBalance.setLayoutManager(new LinearLayoutManager(mContext));
        balanceAdapter = new BalanceAdapter(mContext, balanceList);
        xrvBalance.setAdapter(balanceAdapter);
        xrvBalance.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 1;
                initData(pageIndex,false);
                xrvBalance.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                pageIndex++;
                if (pageIndex <= totalPage) {
                    initData(pageIndex,false);
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvBalance.getFootView().setPadding(0, top, 0, bottom);
                    xrvBalance.setNoMore(true);
                }
            }
        });
        initData(pageIndex,false);
        return view;
    }

    public void initData(int pageIndex,boolean isRefresh) {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.defaultCardDetail(mSession.getmToken(), pageIndex, integralInterface);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                Gson gson = new Gson();
                if (status) {
                    String money = object.optString("money");
                    if (money == null) {
                        getBalanceData.data("¥0.00",isRefresh);
                    }else {
                        getBalanceData.data("¥" + money,isRefresh);
                    }
                    BalanceListBean balanceListBean = gson.fromJson((String) result, BalanceListBean.class);
                    totalPage = balanceListBean.getTotal_page();
                    if (totalPage > 1) {
                        xrvBalance.setLoadingMoreEnabled(true);
                    }
                    if (isRefresh) {
                        balanceList.clear();
                    }
                    balanceList.addAll(balanceListBean.getSpend_list());
                    balanceAdapter.addData(balanceList);
                } else {
                    Utils.phpIsLogin(getActivity(), object.optInt("type"), object.optString("message"));
                }
                if (isRefresh) {
                    xrvBalance.refreshComplete();
                } else {
                    xrvBalance.loadMoreComplete();
                }
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvBalance.refreshComplete();
                } else {
                    xrvBalance.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private GetBalanceData getBalanceData;

    public void setGetBalanceData(GetBalanceData getBalanceData) {
        this.getBalanceData = getBalanceData;
    }

    public interface GetBalanceData {
        void data(String sum,boolean isRefresh);
    }
}
