package com.zhjl.qihao.nearbyinteraction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.greendao.DaoSession;
import com.zhjl.qihao.nearbyinteraction.adapter.SearchNotesListAdapter;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.SearchNoteBean;
import com.zhjl.qihao.nearbyinteraction.bean.SearchNotesBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class SearchNoteDetailActivity extends VolleyBaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.et_search_note)
    EditText etSearchNote;
    @BindView(R.id.tv_search_note)
    TextView tvSearchNote;
    @BindView(R.id.xrv_search_note_list)
    XRecyclerView xrvSearchNoteList;
    @BindView(R.id.img_not_data)
    ImageView imgNotData;
    @BindView(R.id.tv_not_data)
    TextView tvNotData;
    @BindView(R.id.not_data)
    LinearLayout notData;
    private String labelTypeId;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isRefresh = true;
    private SearchNotesListAdapter searchNotesListAdapter;
    private List<SearchNotesBean.DataBean> list = new ArrayList<>();
    private int totalPage;
    private String searchContent = "";
    public static final int RESULT_NOTE_SEARCH_CODE = 0x336;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_note_detail);
        ButterKnife.bind(this);
        labelTypeId = getIntent().getStringExtra("labelTypeId");
        String noteName = getIntent().getStringExtra("noteName");
        daoSession = ZHJLApplication.getInstance().getDaoSession();
        xrvSearchNoteList.setPullRefreshEnabled(false);
        etSearchNote.setText(noteName);
        requestSearchNote();
        xrvSearchNoteList.setLayoutManager(new LinearLayoutManager(mContext));
        searchNotesListAdapter = new SearchNotesListAdapter(this, list);
        xrvSearchNoteList.setAdapter(searchNotesListAdapter);
        xrvSearchNoteList.setLoadingMoreEnabled(true);
        xrvSearchNoteList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                isRefresh = true;
//                pageIndex = 1;
//                requestSearchNote();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                pageIndex++;
                if (pageIndex <= totalPage) {
                    requestSearchNote();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvSearchNoteList.getFootView().setPadding(0, top, 0, bottom);
                    xrvSearchNoteList.setNoMore(true);
                }
            }
        });
        etSearchNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearchNote.getText().toString().trim().length() == 0) {
                    Intent intent = getIntent();
                    setResult(RESULT_NOTE_SEARCH_CODE, intent);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        searchNotesListAdapter.setDeleteNote((id, position) -> deleteNote(id,position));
    }

    private void deleteNote(String forumNoteId, int position) {
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        Call<ResponseBody> call = nearByInterfaces.noteDelete(ParamForNet.put(map));
        activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                if (list.size()>position){
                    list.remove(position);
                    if (list.size()==0){
                        notData.setVisibility(View.VISIBLE);
                        imgNotData.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.search_no_content));
                        tvNotData.setText("没有搜索到相关帖子换个关键词试试吧~");
                    }
                    searchNotesListAdapter.addData(list);
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_search_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = getIntent();
                setResult(RESULT_NOTE_SEARCH_CODE, intent);
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.tv_search_note:
                if (etSearchNote.getText().toString().length() > 0) {
                    SearchNoteBean SearchNoteBean = new SearchNoteBean();
                    SearchNoteBean.setName(etSearchNote.getText().toString().trim());
                    daoSession.insertOrReplace(SearchNoteBean);
                    pageIndex = 1;
                    isRefresh = true;
                    requestSearchNote();
                } else {
                    Toast.makeText(mContext, "请输入要搜索的帖子信息！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void requestSearchNote() {
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", etSearchNote.getText().toString().trim());
        if (labelTypeId != null) {
            map.put("labelTypeId", labelTypeId);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        Call<ResponseBody> call = nearByInterfaces.searchNotes(ParamForNet.put(map));
        activityRequestData(call, SearchNotesBean.class, new RequestResult<SearchNotesBean>() {
            @Override
            public void success(SearchNotesBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (result.getData().size() == 0) {
                    imgNotData.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.search_no_content));
                    tvNotData.setText("没有搜索到相关帖子换个关键词试试吧~");
                    notData.setVisibility(View.VISIBLE);
                } else {
                    notData.setVisibility(View.GONE);
                }
                if (isRefresh) {
                    list.clear();
                    list.addAll(result.getData());
                } else {
                    list.addAll(result.getData());
                    xrvSearchNoteList.loadMoreComplete();
                }
                searchNotesListAdapter.addData(list);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvSearchNoteList.refreshComplete();
                } else {
                    xrvSearchNoteList.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            setResult(RESULT_NOTE_SEARCH_CODE, intent);
            finish();
            overridePendingTransition(0, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            NearByNoteContentBean.DataBean resultData = data.getParcelableExtra("data");
            int position = data.getIntExtra("position", 0);
            if (resultData != null) {
                if (position >= list.size()) {
                    return;
                }
                list.get(position).setPraiseNum(resultData.getPraiseNum());
                list.get(position).setBrowseNumber(resultData.getBrowseNumber());
                list.get(position).setDiscussNum(resultData.getDiscussNum());
                searchNotesListAdapter.addData(list);
            }
        }
    }
}
