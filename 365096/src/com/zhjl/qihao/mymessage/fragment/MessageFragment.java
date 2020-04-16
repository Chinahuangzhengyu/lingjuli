package com.zhjl.qihao.mymessage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.mymessage.activity.MessageDetailActivity;
import com.zhjl.qihao.mymessage.adapter.MessageAdapter;
import com.zhjl.qihao.mymessage.api.MessageInterface;
import com.zhjl.qihao.propertyservicecomplaint.bean.MessageListBean;
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
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.mymessage.activity.MessageDetailActivity.MESSAGE_RESULT;

/**
 * Created by Administrator on 2017/6/19.
 */

public class MessageFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_message)
    XRecyclerView xrvMessage;
    @BindView(R.id.not_data)
    LinearLayout notData;
    Unbinder unbinder;
    private View view;
    private long firstMillis = 0;// 用于保存时间戳
    private long timeLong = 600;// 时间戳
    private LinearLayout not_data;
    private int pageIndex = 1;
    private int pageSize = 10;
    private MessageAdapter messageAdapter;
    private int type;
    private int totalPage;
    private int noReadNum;
    private boolean isRefresh = true;
    private List<MessageListBean.DataBean.ListBean> list = new ArrayList<>();
    private static final int MESSAGE_REQUEST_CODE = 0x114;
    private boolean isResume;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    //fragment与Activity中数据交互
    public static MessageFragment newInstance(int type) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.ab_fragment_message, container, false);
        }

        unbinder = ButterKnife.bind(this, view);
        xrvMessage.setLayoutManager(new LinearLayoutManager(mContext));
        messageAdapter = new MessageAdapter(getActivity(), list);
        xrvMessage.setAdapter(messageAdapter);
        xrvMessage.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isRefresh = true;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (totalPage > pageIndex) {
                    pageIndex++;
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvMessage.getFootView().setPadding(0, top, 0, bottom);
                    xrvMessage.setNoMore(true);
                }
            }
        });

        messageAdapter.setSetOnItemClickListener(new MessageAdapter.SetOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                requestDetailData(position);
            }
        });
        initData();
        return view;
    }

    private void requestDetailData(int position) {
        MessageInterface messageInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(MessageInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", list.get(position).getMessageId());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = messageInterface.messageDetailList(body);
        fragmentRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    JSONObject data = object.optJSONObject("data");
                    String url = data.optString("url");
                    if (!url.equals("")) {
                        Intent intent = new Intent(mContext, MessageDetailActivity.class);
                        intent.putExtra("url", url);
                        startActivityForResult(intent, MESSAGE_REQUEST_CODE);
                    } else {
                        Toast.makeText(mContext, "暂无消息详情！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });
    }


    public void initData() {
        MessageInterface messageInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(MessageInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("type", type);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = messageInterface.messageList(body);
        fragmentRequestData(call, MessageListBean.class, new RequestResult<MessageListBean>() {
            @Override
            public void success(MessageListBean result, String message) {
                if (result.getData() != null) {
                    totalPage = result.getData().getTotalPage();
                    if (totalPage > 1) {
                        xrvMessage.setLoadingMoreEnabled(true);
                    } else {
                        xrvMessage.setLoadingMoreEnabled(false);
                    }
                    noReadNum = result.getData().getNoReadNum();
                    selectNoReadSum.noRead(noReadNum);
                    if (isRefresh) {
                        list.clear();
                        list.addAll(result.getData().getList());
                        messageAdapter.addData(list);
                    } else {
                        list.addAll(result.getData().getList());
                        messageAdapter.addData(list);
                    }
                    if (totalPage > 1) {
                        notData.setVisibility(View.GONE);
                        xrvMessage.setLoadingMoreEnabled(true);
                    }
                    if (list.size() == 0) {
                        notData.setVisibility(View.VISIBLE);
                    }

                }
                if (isRefresh) {
                    xrvMessage.refreshComplete();
                } else {
                    xrvMessage.loadMoreComplete();
                }
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvMessage.refreshComplete();
                } else {
                    xrvMessage.loadMoreComplete();
                }
            }
        });
    }

    public interface SelectNoReadSum {
        void noRead(int message);
    }

    private SelectNoReadSum selectNoReadSum;

    public void setSelectNoReadSum(SelectNoReadSum selectNoReadSum) {
        this.selectNoReadSum = selectNoReadSum;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGE_REQUEST_CODE) {
            isRefresh = true;
            pageIndex = 1;
            initData();
        }
    }
}
