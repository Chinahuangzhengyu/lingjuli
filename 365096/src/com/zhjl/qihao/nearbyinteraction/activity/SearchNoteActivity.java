package com.zhjl.qihao.nearbyinteraction.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.CustomRadioGroup;
import com.zhjl.qihao.greendao.DaoSession;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.nearbyinteraction.bean.NoteHotSearchBean;
import com.zhjl.qihao.nearbyinteraction.bean.SearchNoteBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.nearbyinteraction.activity.SearchNoteDetailActivity.RESULT_NOTE_SEARCH_CODE;

public class SearchNoteActivity extends VolleyBaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.et_search_note)
    EditText etSearchNote;
    @BindView(R.id.tv_search_note)
    TextView tvSearchNote;
    @BindView(R.id.img_shop_history_delete)
    ImageView imgShopHistoryDelete;
    @BindView(R.id.rg_note_history)
    CustomRadioGroup rgNoteHistory;
    @BindView(R.id.rg_note_hot_search)
    CustomRadioGroup rgNoteHotSearch;
    private List<NoteHotSearchBean.DataBean> data = new ArrayList<>();
    private DaoSession daoSession;
    private static final int REQUEST_NOTE_SEARCH = 0x335;
    private String labelTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_note);
        ButterKnife.bind(this);
        labelTypeId = getIntent().getStringExtra("labelTypeId");
        initData();
        etSearchNote.requestFocus();
        rgNoteHotSearch.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                RadioButton rb = (RadioButton) group.getChildAt(i);
                if (rb.getId()==checkedId){
                    etSearchNote.setText(rb.getText().toString().trim());
                    searchNote();
                }
            }
        });

        rgNoteHistory.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                RadioButton rb = (RadioButton) group.getChildAt(i);
                if (rb.getId()==checkedId){
                    etSearchNote.setText(rb.getText().toString().trim());
                    searchNote();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSql();
    }

    private void initSql() {
        daoSession = ZHJLApplication.getInstance().getDaoSession();
        QueryBuilder<SearchNoteBean> builder = daoSession.queryBuilder(SearchNoteBean.class);
        List<SearchNoteBean> list = builder.list();
        rgNoteHistory.removeAllViews();
        if (list.size() > 10) {
            for (int i = list.size() - 1; i > list.size() - 11; i--) {
                final RadioButton button = new RadioButton(mContext);
                button.setButtonDrawable(new StateListDrawable());
                button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_14));
                button.setTextColor(Color.parseColor("#999999"));
                button.setTextSize(14);
                button.setText(list.get(i).getName());
                button.setId(i);
                int left = Utils.dip2px(mContext, 10);
                int top = Utils.dip2px(mContext, 4);
                button.setPadding(left, top, left, top);
                rgNoteHistory.addView(button);
            }
        } else {
            for (int i = list.size() - 1; i >= 0; i--) {
                final RadioButton button = new RadioButton(mContext);
                button.setButtonDrawable(new StateListDrawable());
                button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_14));
                button.setTextColor(Color.parseColor("#999999"));
                button.setTextSize(14);
                button.setText(list.get(i).getName());
                button.setId(i);
                int left = Utils.dip2px(mContext, 10);
                int top = Utils.dip2px(mContext, 4);
                button.setPadding(left, top, left, top);
                rgNoteHistory.addView(button);
            }
        }
    }

    private void searchNote() {
        if (etSearchNote.getText().toString().trim().equals("")) {
            Toast.makeText(mContext, "请输入商品名称！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(mContext, SearchNoteDetailActivity.class);
        intent.putExtra("noteName", etSearchNote.getText().toString().trim());
        SearchNoteBean searchNoteBean = new SearchNoteBean();
        searchNoteBean.setName(etSearchNote.getText().toString().trim());
        daoSession.insertOrReplace(searchNoteBean);
        intent.putExtra("labelTypeId",labelTypeId);
        startActivityForResult(intent, REQUEST_NOTE_SEARCH);
        overridePendingTransition(0, 0);
        rgNoteHistory.clearCheck();
        rgNoteHotSearch.clearCheck();
    }


    private void initData() {
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Call<ResponseBody> call = nearByInterfaces.notHotSearch(new HashMap<>());
        activityRequestData(call, NoteHotSearchBean.class, new RequestResult<NoteHotSearchBean>() {
            @Override
            public void success(NoteHotSearchBean result, String message) throws Exception {
                data = result.getData();
                for (int i = 0; i < data.size(); i++) {
                    final RadioButton button = new RadioButton(mContext);
                    button.setButtonDrawable(new StateListDrawable());
                    button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_14));
                    button.setTextColor(Color.parseColor("#999999"));
                    button.setTextSize(14);
                    button.setText(data.get(i).getName());
                    button.setId(i);
                    int left = Utils.dip2px(mContext, 10);
                    int top = Utils.dip2px(mContext, 4);
                    button.setPadding(left, top, left, top);
                    rgNoteHotSearch.addView(button);
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_search_note,R.id.img_shop_history_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_search_note:
                searchNote();
                break;
            case R.id.img_shop_history_delete:
                PopWindowUtils.getInstance().show("确认要删除全部历史记录?",this);
                PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                    rgNoteHistory.removeAllViews();
                    daoSession.deleteAll(SearchNoteBean.class);
                });
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTE_SEARCH && resultCode == RESULT_NOTE_SEARCH_CODE) {
            etSearchNote.getText().clear();
        }
    }
}
