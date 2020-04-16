package com.zhjl.qihao.freshshop.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.view.CustomRadioGroup;
import com.zhjl.qihao.freshshop.adapter.ShopSearchListAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.SearchHistoryBean;
import com.zhjl.qihao.freshshop.bean.SearchShopListBean;
import com.zhjl.qihao.freshshop.view.MarginDecoration;
import com.zhjl.qihao.greendao.DaoSession;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShopSearchDetailActivity extends VolleyBaseActivity {
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
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.rg_shop_title)
    CustomRadioGroup rgShopTitle;
    @BindView(R.id.xrv_shop_detail)
    XRecyclerView xrvShopDetail;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    @BindView(R.id.img_not_data)
    ImageView imgNotData;
    @BindView(R.id.tv_not_data)
    TextView tvNotData;
    @BindView(R.id.not_data)
    LinearLayout notData;
    @BindView(R.id.rb_sales)
    RadioButton rbSales;
    @BindView(R.id.rb_price)
    RadioButton rbPrice;
    @BindView(R.id.rb_new)
    RadioButton rbNew;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private String shopName;
    private String type = "sale";
    private int pageIndex = 1;
    private int pageSize = 10;
    private ShopSearchListAdapter shopSearchListAdapter;
    public static final int RESULT_CODE = 0x334;
    private boolean isAsc = false; //升序
    private boolean isSort = false; //是否排序
    private DaoSession daoSession;
    private boolean isLoadMore;
    private int totalPage;
    private List<SearchShopListBean.ProductsBean> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search_detail);
        ButterKnife.bind(this);
        rlTitle.setVisibility(View.GONE);
        notData.setVisibility(View.GONE);
        rlLoading.setVisibility(View.VISIBLE);
        daoSession = ZHJLApplication.getInstance().getDaoSession();
        xrvShopDetail.setPullRefreshEnabled(false);
        xrvShopDetail.setLayoutManager(new GridLayoutManager(mContext, 2));
        shopName = getIntent().getStringExtra("shopName");
        etSearchShop.setText(shopName);
        shopSearchListAdapter = new ShopSearchListAdapter(this, products);
        xrvShopDetail.addItemDecoration(new MarginDecoration(mContext));
        xrvShopDetail.setAdapter(shopSearchListAdapter);
        xrvShopDetail.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                if (totalPage > pageIndex) {
                    pageIndex++;
                    initData(isAsc);
                }
            }
        });
        initData(isSort);
        etSearchShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearchShop.getText().toString().trim().length() == 0) {
                    Intent intent = getIntent();
                    setResult(RESULT_CODE, intent);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rgShopTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbSales.getId()) {
                    type = "sale";
                    pageIndex = 1;
                    isLoadMore = false;
                    isAsc = false;
                    isSort = false;
                    initData(isSort);
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_shop_price_sort);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rbPrice.setCompoundDrawables(null, null, drawable, null);
                } else if (checkedId == rbNew.getId()) {
                    type = "created";
                    pageIndex = 1;
                    isLoadMore = false;
                    isAsc = false;
                    isSort = false;
                    initData(isSort);
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_shop_price_sort);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rbPrice.setCompoundDrawables(null, null, drawable, null);
                }
            }
        });
        rbPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "price";
                isAsc = !isAsc;
                if (!isAsc) {
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_shop_asc);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rbPrice.setCompoundDrawables(null, null, drawable, null);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_shop_desc);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rbPrice.setCompoundDrawables(null, null, drawable, null);
                }
                isSort = true;
                isLoadMore = false;
                pageIndex = 1;
                initData(isSort);
            }
        });
    }

    private void initData(boolean isSort) {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", etSearchShop.getText().toString().trim());
        map.put("order", type);
        map.put("page", pageIndex);
        map.put("limit", pageSize);
        if (isSort) {
            if (isAsc) {
                map.put("sort", "asc");
            } else {
                map.put("sort", "desc");
            }
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getShopSearchList(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                Gson gson = new Gson();
                SearchShopListBean searchShopListBean = gson.fromJson((String) result, SearchShopListBean.class);
                SearchShopListBean.InfoBean info = searchShopListBean.getInfo();
                totalPage = info.getTotal_page();
                int currentPage = info.getCurrent_page();
                if (!isLoadMore) {
                    products.clear();
                }
                products.addAll(searchShopListBean.getProducts());
                shopSearchListAdapter.addData(products);
                if (products.size() == 0) {
                    imgNotData.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.search_no_content));
                    tvNotData.setText("没有搜索到相关商品换个关键词试试吧~");
                    rlTitle.setVisibility(View.GONE);
                    notData.setVisibility(View.VISIBLE);
                } else {
                    rlTitle.setVisibility(View.VISIBLE);
                    notData.setVisibility(View.GONE);
                }
                rlLoading.setVisibility(View.GONE);
                xrvShopDetail.loadMoreComplete();
                if (totalPage==currentPage){
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvShopDetail.getFootView().setPadding(0, top, 0, bottom);
                    xrvShopDetail.setNoMore(true);
                }else {
                    xrvShopDetail.setNoMore(false);
                }
            }

            @Override
            public void fail() {
                rlLoading.setVisibility(View.GONE);
                xrvShopDetail.loadMoreComplete();
            }
        });
    }

    @OnClick({R.id.img_back, R.id.img_search, R.id.et_search_shop, R.id.img_call_person, R.id.tv_search_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = getIntent();
                setResult(RESULT_CODE, intent);
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.img_search:
                break;
            case R.id.et_search_shop:
                break;
            case R.id.img_call_person:
//                Utils.callPerson(this, "是否拨打电话0855-8552588", "0855-8552588");
                Intent intent1 = new Intent();
                intent1.setClass(mContext, UserAgreementActivity.class);
                intent1.putExtra("name","在线客服");
                intent1.putExtra("webContent","http://p.qiao.baidu.com/cps/chat?siteId=14492024&userId=29966678&cp=&cr=APP&cw=");
                startActivity(intent1);
                break;
            case R.id.tv_search_shop:
                SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
                searchHistoryBean.setName(etSearchShop.getText().toString().trim());
                daoSession.insertOrReplace(searchHistoryBean);
                pageIndex = 1;
                isLoadMore = false;
                initData(isSort);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            setResult(RESULT_CODE, intent);
            finish();
            overridePendingTransition(0, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
