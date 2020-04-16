package com.zhjl.qihao.freshshop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.adapter.ShopRecommendAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopDetailBean;
import com.zhjl.qihao.freshshop.bean.ShopProductBean;
import com.zhjl.qihao.freshshop.fragment.BuyShopPopFragment;
import com.zhjl.qihao.freshshop.utils.ActivityUtil;
import com.zhjl.qihao.freshshop.view.IdeaScrollView;
import com.zhjl.qihao.freshshop.view.IdeaViewPager;
import com.zhjl.qihao.freshshop.view.MarginDecoration;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CircleImageView;
import com.zhjl.qihao.view.ListViewForScrollView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.view.KeyEvent.KEYCODE_BACK;

public class ShopDetailActivity2 extends VolleyBaseActivity {
    @BindView(R.id.img_shop_more)
    ImageView imgShopMore;
    @BindView(R.id.img_shop_car)
    ImageView imgShopCar;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.vp_shop_detail)
    IdeaViewPager vpShopDetail;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    //    @BindView(R.id.v_share_line)
//    View vShareLine;
//    @BindView(R.id.tv_share)
//    TextView tvShare;
    @BindView(R.id.ll_shop_title)
    LinearLayout llShopTitle;
    @BindView(R.id.tv_shop_content)
    TextView tvShopContent;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.tv_month_sum)
    TextView tvMonthSum;
    //    @BindView(R.id.tv_distribution_price)
//    TextView tvDistributionPrice;
//    @BindView(R.id.tv_address)
//    TextView tvAddress;
    @BindView(R.id.tv_shop_evaluate)
    TextView tvShopEvaluate;
    @BindView(R.id.web_shop_detail)
    WebView webShopDetail;
    @BindView(R.id.xrv_shop_recommend)
    XRecyclerView xrvShopRecommend;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_call_person)
    TextView tvCallPerson;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_add_car)
    TextView tvAddCar;
    @BindView(R.id.tv_now_buy)
    TextView tvNowBuy;
    @BindView(R.id.tv_vp_num)
    TextView tvVpNum;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.one)
    LinearLayout one;
    @BindView(R.id.two)
    LinearLayout two;
    @BindView(R.id.three)
    LinearLayout three;
    @BindView(R.id.four)
    LinearLayout four;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.layer)
    View layer;
    @BindView(R.id.ideaScrollView)
    IdeaScrollView ideaScrollView;
    @BindView(R.id.headerParent)
    LinearLayout headerParent;
    @BindView(R.id.tv_lose_money)
    TextView tvLoseMoney;
    @BindView(R.id.lv_shop_evaluate)
    ListViewForScrollView lvShopEvaluate;
    @BindView(R.id.btn_one)
    RadioButton btnOne;
    @BindView(R.id.btn_two)
    RadioButton btnTwo;
    @BindView(R.id.btn_three)
    RadioButton btnThree;
    @BindView(R.id.btn_four)
    RadioButton btnFour;
    @BindView(R.id.tv_shop_evaluate_count)
    TextView tvShopEvaluateCount;
    private PopupWindow nearBySortPop;
    private boolean isCollection = false;
    private List<ImageView> listImgs = new ArrayList<>();
    private final MyHandler myHandler = new MyHandler(this);
    private List<ShopProductBean> shopProductBeanList = new ArrayList<>();
    private Timer timer;
    private TextView tvShopSum;
    private PopupWindow shopDetailPop;
    private String url = "https://www.baidu.com";
    private float currentPercentage = 0;
    private int webHeight = 0;
    ArrayList<Integer> araryDistance = new ArrayList<>();
    private boolean isFirst = true;


    private RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(currentPercentage) : getRadioAlphaColor(currentPercentage));
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.green_view_line);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                radioButton.setCompoundDrawables(null, null, null, radioButton.isChecked() ? drawable : null);
                if (radioButton.isChecked() && isNeedScrollTo) {
                    ideaScrollView.setPosition(i);
                }
            }
        }
    };
    private boolean isNeedScrollTo = true;
    private BuyShopPopFragment fragment;
    private int id;
    private ShopDetailBean shopDetailBean;
    private String content;
    private ShopImageAdapter shopImageAdapter;
    private SpannableString string;
    private StrikethroughSpan span;
    private ShopRecommendAdapter shopRecommendAdapter;
    private List<String> midImages;


    private static class MyHandler extends Handler {
        private final WeakReference<ShopDetailActivity2> mActivity;
        private ShopDetailActivity2 instance;

        public MyHandler(ShopDetailActivity2 activity) {
            mActivity = new WeakReference<ShopDetailActivity2>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            instance = mActivity.get();
            switch (msg.what) {
                case 11:
                    int currentItem = instance.vpShopDetail.getCurrentItem();
                    currentItem += 1;
                    instance.vpShopDetail.setCurrentItem(currentItem);
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myHandler.removeCallbacks(null);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        myHandler.removeCallbacks(null);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacks(null);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        ActivityUtil.removeActivity(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        initData();
        initProduct();
        xrvShopRecommend.setLayoutManager(new GridLayoutManager(mContext, 2));
        shopRecommendAdapter = new ShopRecommendAdapter(this, shopProductBeanList);
        xrvShopRecommend.setAdapter(shopRecommendAdapter);
        xrvShopRecommend.addItemDecoration(new MarginDecoration(mContext));
        xrvShopRecommend.setPullRefreshEnabled(false);
        vpShopDetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        setTimer();
                        break;
                }
                return false;
            }
        });
        vpShopDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                //轮播图改变，圆点改变的监听
                if (midImages.size() == 2) {
                    position = position % (listImgs.size() / 2);
                    tvVpNum.setText(position + 1 + "/" + (listImgs.size() - 2));
                } else {
                    position = position % listImgs.size();
                    tvVpNum.setText(position + 1 + "/" + listImgs.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                switch (i) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //点击、滑屏
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        //释放
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        break;
                }
            }
        });

        WebSettings webSettings = webShopDetail.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /**关闭webview中缓存**/
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBlockNetworkImage(false);

        webShopDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(content);
                return true;
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        getHeight();
                    }

                }, 500);//时间很关键
            }
        });


        ideaScrollView.setOnScrollChangedColorListener(new IdeaScrollView.OnScrollChangedColorListener() {
            @Override
            public void onChanged(float percentage, int y) {
                int color = getAlphaColor(percentage > 0.9f ? 1.0f : percentage);
                radioGroup.setBackground(new ColorDrawable(color));
                radioGroup.setAlpha((percentage > 0.9f ? 1.0f : percentage) * 255);
                RadioButton rb = (RadioButton) radioGroup.getChildAt(0);
                Drawable[] drawables = rb.getCompoundDrawables();
                for (int i = 0; i < drawables.length; i++) {
                    Drawable drawable = drawables[drawables.length - 1];
                    if (drawable != null) {
                        drawable.setAlpha((int) ((percentage > 0.9f ? 1.0f : percentage) * 255));
                    }
                }
                setRadioButtonTextColor(percentage);
                if (y > Utils.dip2px(mContext, 44)) {
                    enableRadioGroup(radioGroup);
                } else {
                    disableRadioGroup(radioGroup);
                }
            }

            @Override
            public void onChangedFirstColor(float percentage) {

            }

            @Override
            public void onChangedSecondColor(float percentage) {

            }
        });

        ideaScrollView.setOnSelectedIndicateChangedListener(new IdeaScrollView.OnSelectedIndicateChangedListener() {
            @Override
            public void onSelectedChanged(int position) {
                isNeedScrollTo = false;
                radioGroup.check(radioGroup.getChildAt(position).getId());
                isNeedScrollTo = true;
            }
        });

        radioGroup.setOnCheckedChangeListener(radioGroupListener);
    }

    private void initProduct() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getProductShop(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONArray array = new JSONArray((String) result);
                Gson gson = new Gson();
                for (int i = 0; i < array.length(); i++) {
                    String string = array.getJSONObject(i).toString();
                    ShopProductBean shopProductBean = gson.fromJson(string, ShopProductBean.class);
                    shopProductBeanList.add(shopProductBean);
                }
                shopRecommendAdapter.addData(shopProductBeanList);
            }

            @Override
            public void fail() {

            }
        });
    }

    public void getHeight() {
        final int height = measureListViewWrongHeight(lvShopEvaluate);
        araryDistance.add(0);
        one.post(new Runnable() {
            @Override
            public void run() {
                araryDistance.add(one.getHeight() - Utils.dip2px(mContext, 44));
                araryDistance.add(one.getHeight() + getMeasureHeight(two) + height - getMeasureHeight(headerParent));
                araryDistance.add(one.getHeight() + getMeasureHeight(two) + height + getMeasureHeight(three) - getMeasureHeight(headerParent));
                ideaScrollView.setArrayDistance(araryDistance);
                Rect rectangle = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
                ideaScrollView.setViewPager(vpShopDetail, getMeasureHeight(headerParent) - rectangle.top);
                radioGroup.setAlpha(0);
                radioGroup.check(radioGroup.getChildAt(0).getId());
            }
        });
    }

    // 动态改变listView的高度
    public int measureListViewWrongHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // 减掉底部分割线的高度
        int historyHeight = totalHeight
                + (listView.getDividerHeight() * listAdapter.getCount() - 1);
        return historyHeight;
    }


    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopDetail(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                Gson gson = new Gson();
                shopDetailBean = gson.fromJson(string, ShopDetailBean.class);
                //网站内容
                content = shopDetailBean.getProduct().getDetail_url();
                webShopDetail.loadUrl(content);
                midImages = shopDetailBean.getProduct().getMidImages();
                for (int i = 0; i < midImages.size(); i++) {
                    ImageView view = new ImageView(mContext);
                    PictureHelper.setImageView(mContext, midImages.get(i), view, R.drawable.img_err);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    listImgs.add(view);
                }
                if (shopImageAdapter != null) {
                    vpShopDetail.setAdapter(shopImageAdapter);
                } else {
                    shopImageAdapter = new ShopImageAdapter(listImgs);
                    vpShopDetail.setAdapter(shopImageAdapter);
                }
                tvVpNum.setText(1 + "/" + listImgs.size());
                if (listImgs.size() == 2) {
                    for (int i = 0; i < 2; i++) {
                        RoundImageView imageView = new RoundImageView(mContext);
                        imageView.setRoundImg(mContext, 6);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        PictureHelper.setImageView(mContext, midImages.get(i), imageView, R.drawable.img_loading);
                        listImgs.add(imageView);
                    }
                }
                //详情轮播图
                vpShopDetail.setCurrentItem(listImgs.size() * 8);
                setTimer();
                setData();
            }

            @Override
            public void fail() {

            }
        });
    }

    public void setTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    int currentItem = vpShopDetail.getCurrentItem();
                    currentItem++;
                    Message message = myHandler.obtainMessage();
                    message.obj = currentItem;
                    message.what = 11;
                    myHandler.sendMessage(message);
                }
            }, 2000, 2000);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (shopImageAdapter != null) {
            vpShopDetail.setAdapter(shopImageAdapter);
        } else {
            shopImageAdapter = new ShopImageAdapter(listImgs);
            vpShopDetail.setAdapter(shopImageAdapter);
        }
        setTimer();
    }

    private void setData() {

        tvShopName.setText(shopDetailBean.getProduct().getName());
        tvShopContent.setText(shopDetailBean.getProduct().getSummary());
        if (shopDetailBean.getProduct().getPromotion_price().equals("0.00")) {
            tvOldPrice.setVisibility(View.GONE);
            tvPrice.setText(shopDetailBean.getProduct().getPrice());
        } else {
            tvOldPrice.setVisibility(View.VISIBLE);
            tvPrice.setText(shopDetailBean.getProduct().getPromotion_price());
            tvOldPrice.setText("¥" + shopDetailBean.getProduct().getPrice());
        }
        string = new SpannableString(tvOldPrice.getText().toString().trim());
        span = new StrikethroughSpan();
        string.setSpan(span, 0, tvOldPrice.getText().toString().trim().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvOldPrice.setText(string);
        tvMonthSum.setText("月销" + shopDetailBean.getProduct().getMonthly_sales());
        tvLoseMoney.setText("N币抵扣最大额度¥" + shopDetailBean.getProduct().getPoints_price() + "元");
        lvShopEvaluate.setAdapter(new ShopEvaluateAdapter(shopDetailBean.getComments()));
        tvShopEvaluateCount.setText("商品评价（" + shopDetailBean.getComment_total() + "）");
    }

    class ShopEvaluateAdapter extends BaseAdapter {

        private List<ShopDetailBean.CommentsBean> comments;

        public ShopEvaluateAdapter(List<ShopDetailBean.CommentsBean> comments) {
            this.comments = comments;
        }

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.shop_evaluate_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                PictureHelper.setImageView(mContext, comments.get(position).getUser_picture(), holder.imgEvaluate, R.drawable.ic_head);
                holder.tvEvaluateName.setText(comments.get(position).getNick_name());
                holder.tvEvaluateDate.setText(comments.get(position).getCreate_date());
                holder.tvEvaluateContent.setText(comments.get(position).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.img_evaluate)
            CircleImageView imgEvaluate;
            @BindView(R.id.tv_evaluate_name)
            TextView tvEvaluateName;
            @BindView(R.id.tv_evaluate_date)
            TextView tvEvaluateDate;
            @BindView(R.id.tv_evaluate_content)
            TextView tvEvaluateContent;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public void disableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(true);
        }
    }

    public void setRadioButtonTextColor(float percentage) {
        if (Math.abs(percentage - currentPercentage) >= 0.1f) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(percentage) : getRadioAlphaColor(percentage));
            }
            this.currentPercentage = percentage;
        }
    }

    public int getMeasureHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    public int getAlphaColor(float f) {
        return Color.argb((int) (f * 255), 255, 255, 255);
    }

    public int getLayerAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x09, 0xc1, 0xf4);
    }

    public int getRadioCheckedAlphaColor(float f) {
        return Color.argb((int) (f * 255), 35, 172, 56);
    }

    public int getRadioAlphaColor(float f) {
        return Color.argb((int) (f * 255), 31, 31, 31);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && webShopDetail.canGoBack()) {
            webShopDetail.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ShopImageAdapter extends PagerAdapter {

        private List<ImageView> listImgs;

        public ShopImageAdapter(List<ImageView> listImgs) {
            this.listImgs = listImgs;
        }

        @Override
        public int getCount() {
            if (listImgs.size() == 1) {
                return 1;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
//            container.removeView(listImgs.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % listImgs.size();
            ImageView view = listImgs.get(position);
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            container.addView(view);
            return view;
        }
    }


    @OnClick({R.id.img_back, R.id.img_shop_more, R.id.img_shop_car, R.id.tv_shop_evaluate, R.id.tv_call_person, R.id.tv_collection, R.id.tv_add_car, R.id.tv_now_buy})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_shop_more:
                showMorePop();  //显示分享的pop
                break;
            case R.id.img_shop_car:
                intent.setClass(mContext, ShopCarActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_shop_evaluate:
                intent.setClass(mContext, ShopEvaluateActivity.class);
                intent.putExtra("pid", id);
                startActivity(intent);
                break;
            case R.id.tv_call_person:
                break;
            case R.id.tv_collection:
                Drawable drawable = null;
                isCollection = !isCollection;
                if (isCollection) {
                    drawable = ContextCompat.getDrawable(mContext, R.drawable.img_collection_red);
                } else {
                    drawable = ContextCompat.getDrawable(mContext, R.drawable.img_collection_black);
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvCollection.setCompoundDrawables(null, drawable, null, null);
                break;
            case R.id.tv_add_car:
                initShopAttrsPop(true);
                break;
            case R.id.tv_now_buy:
                initShopAttrsPop(false);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_shop_add:
                break;
            case R.id.img_shop_reduce:
                break;
            case R.id.img_cancel:
                shopDetailPop.dismiss();
                break;
        }
    }

    //商品属性的popWindow
    private void initShopAttrsPop(boolean isBuyCar) {
        fragment = new BuyShopPopFragment(this, shopDetailBean);
        fragment.setBuyCar(isBuyCar);
        fragment.show(getSupportFragmentManager(), "1");
    }

    private void showMorePop() {

        View popView = LayoutInflater.from(this).inflate(R.layout.nearby_sort_popwindow, null);
        TextView tvSortHots = popView.findViewById(R.id.tv_sort_hots);
        tvSortHots.setText("首页");
        tvSortHots.setTextColor(Color.parseColor("#1f1f1f"));
        TextView tvSortTime = popView.findViewById(R.id.tv_sort_time);
        tvSortTime.setVisibility(View.GONE);
        tvSortTime.setText("分享");
        tvSortTime.setTextColor(Color.parseColor("#1f1f1f"));
        tvSortHots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearBySortPop.dismiss();
                Intent intent = new Intent(mContext, RefactorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        tvSortTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearBySortPop.dismiss();
            }
        });
        int with = Utils.dip2px(mContext, 64);
        int height = Utils.dip2px(mContext, 38);
        nearBySortPop = new PopupWindow(popView, with, height);
        nearBySortPop.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_share_white));
        nearBySortPop.setFocusable(true);
        nearBySortPop.setOutsideTouchable(true);
        nearBySortPop.setAnimationStyle(R.style.AnimationPopupCenter);
        nearBySortPop.showAsDropDown(imgShopMore, -Utils.dip2px(mContext, 40), Utils.dip2px(mContext, 4));

    }
}
