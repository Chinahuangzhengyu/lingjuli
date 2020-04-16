package com.zhjl.qihao.freshshop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.view.CustomRadioGroup;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.SearchHistoryBean;
import com.zhjl.qihao.greendao.DaoSession;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.freshshop.activity.ShopSearchDetailActivity.RESULT_CODE;

public class SearchShopActivity extends VolleyBaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.et_search_shop)
    EditText etSearchShop;
    @BindView(R.id.img_call_person)
    ImageView imgCallPerson;
    @BindView(R.id.tv_search_shop)
    TextView tvSearchShop;
    @BindView(R.id.img_shop_history_delete)
    ImageView imgShopHistoryDelete;
    @BindView(R.id.rg_shop_history)
    CustomRadioGroup rgShopHistory;
    @BindView(R.id.rg_shop_hot_search)
    CustomRadioGroup rgShopHotSearch;
    private PopupWindow surePopWindow;
    private List<String> list = new ArrayList<>();
    private DaoSession daoSession;
    private static final int REQUEST = 0x333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop);
        ButterKnife.bind(this);
        initData();
        etSearchShop.requestFocus();
        rgShopHistory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {     //搜索历史
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (rb.getId()==checkedId){
                        etSearchShop.setText(rb.getText().toString().trim());
                        searchShop();
                    }
                }

            }
        });

        rgShopHotSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {       //搜索热搜
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (rb.getId()==checkedId){
                        etSearchShop.setText(rb.getText().toString().trim());
                        searchShop();
                    }
                }
            }
        });
    }

    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Call<ResponseBody> call = shopInterface.getShopSearch(ParamForNet.put(new HashMap<String, Object>()));
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONArray array = new JSONArray((String) result);
                list.clear();
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.optString(i));
                }
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        final RadioButton button = new RadioButton(mContext);
                        button.setButtonDrawable(new StateListDrawable());
                        button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_14));
                        button.setTextColor(Color.parseColor("#999999"));
                        button.setTextSize(14);
                        button.setText(list.get(i));
                        button.setId(i);
                        int left = Utils.dip2px(mContext, 10);
                        int top = Utils.dip2px(mContext, 4);
                        button.setPadding(left, top, left, top);
                        rgShopHotSearch.addView(button);
                    }
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.img_search, R.id.img_call_person, R.id.tv_search_shop, R.id.img_shop_history_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_search:
                break;
            case R.id.img_call_person:
//                Utils.callPerson(this,"是否拨打电话0855-8552588","0855-8552588");
                Intent intent = new Intent();
                intent.setClass(mContext, UserAgreementActivity.class);
                intent.putExtra("name","在线客服");
                intent.putExtra("webContent","http://p.qiao.baidu.com/cps/chat?siteId=14492024&userId=29966678&cp=&cr=APP&cw=");
                startActivity(intent);
                break;
            case R.id.tv_search_shop:
                searchShop();
                break;
            case R.id.img_shop_history_delete:
                initSurePop();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSql();
    }

    private void initSql() {
        daoSession = ZHJLApplication.getInstance().getDaoSession();
//        List<SearchHistoryBean> list = daoSession.queryRaw(SearchHistoryBean.class, "id = (select max(id)", new String[]{"id"});
        QueryBuilder<SearchHistoryBean> builder = daoSession.queryBuilder(SearchHistoryBean.class);
        List<SearchHistoryBean> list = builder.list();
        rgShopHistory.removeAllViews();
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
                rgShopHistory.addView(button);
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
                rgShopHistory.addView(button);
            }
        }
    }

    private void searchShop() {
        if (etSearchShop.getText().toString().trim().equals("")) {
            Toast.makeText(mContext, "请输入商品名称！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(mContext, ShopSearchDetailActivity.class);
        intent.putExtra("shopName", etSearchShop.getText().toString().trim());
        SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
        searchHistoryBean.setName(etSearchShop.getText().toString().trim());
        daoSession.insertOrReplace(searchHistoryBean);
        startActivityForResult(intent, REQUEST);
        overridePendingTransition(0, 0);
        rgShopHistory.clearCheck();
        rgShopHotSearch.clearCheck();
    }

    private void initSurePop() {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.login_pop
                , null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView tvMessageTitle = (TextView) popView.findViewById(R.id.tv_message_title);
        TextView not = (TextView) popView.findViewById(R.id.no);
        tvMessageTitle.setText("确认要删除全部历史记录?");
        surePopWindow = new PopupWindow(popView, Utils.dip2px(mContext, 256), ViewGroup.LayoutParams.WRAP_CONTENT);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgShopHistory.removeAllViews();
                daoSession.deleteAll(SearchHistoryBean.class);
                surePopWindow.dismiss();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surePopWindow.dismiss();
            }
        });
        surePopWindow.setFocusable(true);
        surePopWindow.setOutsideTouchable(true);
        Utils.darkenBackground(0.8f, this);
        if (!surePopWindow.isShowing()) {
            surePopWindow.showAtLocation(this.findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }

        surePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, SearchShopActivity.this);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_CODE) {
            etSearchShop.getText().clear();
        }
    }
}
