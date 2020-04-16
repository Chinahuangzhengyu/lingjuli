package com.zhjl.qihao.opendoor.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.michoi.unlock.UnlockManager;
import com.michoi.unlock.mode.RightsRecord;
import com.michoi.unlock.mode.RightsResponseMode;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.bean.OpenDoorListBean;
import com.zhjl.qihao.opendoor.adapter.OpenDoorListAdapter;
import com.zhjl.qihao.opendoor.api.OpenInterface;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class OpenDoorChooseActivity extends VolleyBaseActivity {
    @BindView(R.id.xrv_open_door_list)
    XRecyclerView xrvOpenDoorList;
    private OpenDoorListAdapter openDoorListAdapter;
    private ArrayList<RightsRecord> rights = new ArrayList<>();
    private int areaId;
    private RightsResponseMode responseMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_door_choose);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "远程开门", this);
        rights = (ArrayList<RightsRecord>) getIntent().getSerializableExtra("rights");
        areaId = getIntent().getIntExtra("areaId", 0);
        xrvOpenDoorList.setLayoutManager(new LinearLayoutManager(mContext));
        openDoorListAdapter = new OpenDoorListAdapter(this,rights);
        xrvOpenDoorList.setAdapter(openDoorListAdapter);
        xrvOpenDoorList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                responseMode = UnlockManager.getInstance().getLockRights(mSession.getRegisterMobile(),areaId+"",0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rights.clear();
                        rights.addAll(responseMode.getRights());
                        openDoorListAdapter.addData(rights);
                        xrvOpenDoorList.refreshComplete();
                    }
                });
            }
        }).start();
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
