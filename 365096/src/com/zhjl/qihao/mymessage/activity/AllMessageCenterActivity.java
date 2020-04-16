package com.zhjl.qihao.mymessage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.complaint.activity.ComplaintDetailActivity;
import com.zhjl.qihao.mymessage.adapter.FragmentAdapter;
import com.zhjl.qihao.mymessage.api.MessageInterface;
import com.zhjl.qihao.mymessage.fragment.MessageFragment;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/7/10.
 */

public class AllMessageCenterActivity extends VolleyBaseActivity {
    @BindView(R.id.tab_message)
    TabLayout tabMessage;
    @BindView(R.id.vp_message_content)
    ViewPager vpMessageContent;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private MessageFragment f2;
    private MessageFragment f1;
    private FragmentAdapter mFragmentAdapter;
    private PopupWindow messagePopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ab_activity_messagecenter);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }


    private void initView() {
        NewHeaderBar mHeaderBar = NewHeaderBar.createCommomBack(this, "我的消息", "全部已读", this);
        mHeaderBar.getRightText().setTextColor(Color.parseColor("#FF23AC38"));
        mHeaderBar.getRightText().setTextSize(14);
        mHeaderBar.getRightText().setOnClickListener(this);
    }

    private void initFragment() {
        f1 = MessageFragment.newInstance(0);
        f2 = MessageFragment.newInstance(1);

        mFragmentList.add(f1);
        mFragmentList.add(f2);
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vpMessageContent.setAdapter(mFragmentAdapter);
        tabMessage.setupWithViewPager(vpMessageContent);
        Utils.setTabLayoutTextBold(tabMessage, "平台消息", "物业消息");
        f1.setSelectNoReadSum(new MessageFragment.SelectNoReadSum() {
            @Override
            public void noRead(int message) {
                if (message > 0) {
                    TextView tvTvNumber = tabMessage.getTabAt(0).getCustomView().findViewById(R.id.tv_tvNumber);
                    tvTvNumber.setVisibility(View.VISIBLE);
                    tvTvNumber.setText(message + "");
                } else {
                    TextView tvTvNumber = tabMessage.getTabAt(0).getCustomView().findViewById(R.id.tv_tvNumber);
                    tvTvNumber.setVisibility(View.GONE);
                }
            }
        });
        f2.setSelectNoReadSum(new MessageFragment.SelectNoReadSum() {
            @Override
            public void noRead(int message) {
                if (message > 0) {
                    TextView tvTvNumber = tabMessage.getTabAt(1).getCustomView().findViewById(R.id.tv_tvNumber);
                    tvTvNumber.setVisibility(View.VISIBLE);
                    tvTvNumber.setText(message + "");
                } else {
                    TextView tvTvNumber = tabMessage.getTabAt(1).getCustomView().findViewById(R.id.tv_tvNumber);
                    tvTvNumber.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
            case R.id.tv_right:
                showMessagePop();
                break;
        }
    }

    private void showMessagePop() {
        View popView = LayoutInflater.from(this).inflate(R.layout.message_pop, null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView not = (TextView) popView.findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAllRead();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagePopWindow.dismiss();
            }
        });
        messagePopWindow = new PopupWindow(popView, Utils.dip2px(mContext, 256), ViewGroup.LayoutParams.WRAP_CONTENT);
        //customerPopWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent, null));
        messagePopWindow.setFocusable(true);
        messagePopWindow.setOutsideTouchable(true);
//        messagePopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        Utils.darkenBackground(0.8f, this);
        messagePopWindow.showAtLocation(llRoot, Gravity.CENTER, 0, 0);


        messagePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, AllMessageCenterActivity.this);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    private void requestAllRead() {
        MessageInterface messageInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(MessageInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = messageInterface.messageAllRead(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < tabMessage.getTabCount(); i++) {
                    TextView tvTvNumber = tabMessage.getTabAt(i).getCustomView().findViewById(R.id.tv_tvNumber);
                    tvTvNumber.setVisibility(View.GONE);
                }
                f1.initData();
                f2.initData();
                messagePopWindow.dismiss();
            }

            @Override
            public void fail() {
                messagePopWindow.dismiss();
            }
        });
    }

}
