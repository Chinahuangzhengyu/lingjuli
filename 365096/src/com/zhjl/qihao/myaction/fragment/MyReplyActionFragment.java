package com.zhjl.qihao.myaction.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.myaction.adapter.MyReplyAdapter;
import com.zhjl.qihao.myaction.api.MyActionInterface;
import com.zhjl.qihao.myaction.bean.MyReplyActionBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.myaction.adapter.MySendAdapter.REQUEST_NOTE_DATA;
import static com.zhjl.qihao.nearbyinteraction.adapter.NearByNoteContentAdapter.RESULT_DELETE_NOTE;

public class MyReplyActionFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_my_reply_list)
    XRecyclerView xrvMyReplyList;
    Unbinder unbinder;
    private View view;
    private int pageIndex = 1;
    private int pageSize = 10;
    private List<MyReplyActionBean.DataBean> data = new ArrayList<>();
    private MyReplyAdapter myReplyAdapter;
    private int totalPage;
    private boolean isRefresh;
    private VolleyBaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (VolleyBaseActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my_reply_action, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initData();
        xrvMyReplyList.setLayoutManager(new LinearLayoutManager(mContext));
        myReplyAdapter = new MyReplyAdapter(mContext, data);
        xrvMyReplyList.setAdapter(myReplyAdapter);
        xrvMyReplyList.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                    xrvMyReplyList.getFootView().setPadding(0, top, 0, bottom);
                    xrvMyReplyList.setNoMore(true);
                }
            }
        });
        return view;
    }

    private void initData() {
        MyActionInterface actionInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(MyActionInterface.class);
        Map<String,Object> map = new HashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        Call<ResponseBody> call = actionInterface.myReplyList(map);
        fragmentRequestData(call, MyReplyActionBean.class, new RequestResult<MyReplyActionBean>() {
            @Override
            public void success(MyReplyActionBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh){
                    data.clear();
                    data = result.getData();
                    xrvMyReplyList.refreshComplete();
                }else {
                    data.addAll(result.getData());
                    xrvMyReplyList.loadMoreComplete();
                }
                myReplyAdapter.addData(data);
            }

            @Override
            public void fail() {
                if (isRefresh){
                    xrvMyReplyList.refreshComplete();
                }else {
                    xrvMyReplyList.loadMoreComplete();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTE_DATA && resultCode == RESULT_DELETE_NOTE) {
            isRefresh = true;
            pageIndex = 1;
            initData();
        }
    }
}
