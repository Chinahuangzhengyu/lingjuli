package com.zhjl.qihao.freshshop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.abrefactor.bean.MainViewPagerBean;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.adapter.ShopTypeListAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopTypeBean;
import com.zhjl.qihao.freshshop.view.AnyRoundImageView;
import com.zhjl.qihao.integration.activity.HelpNoticeActivity;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CannotScrollViewpager;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.freshshop.view.AnyRoundImageView.TYPE_ROUND;

public class FreshShopActivity extends VolleyBaseActivity {

    //    @BindView(R.id.tv_status_color)
//    TextView tvStatusColor;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rv_shop_list)
    RecyclerView rvShopList;
    @BindView(R.id.vp_shop)
    CannotScrollViewpager vpShop;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_call_person)
    ImageView imgCallPerson;
    @BindView(R.id.rg_vp_item)
    RadioGroup rgVpItem;
    private final MyHandler myHandler = new MyHandler(this);
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    private int count = 0;
    private Timer timer;
    private AnyRoundImageView imageView;
    private List<AnyRoundImageView> imageViewList;
    private List<MainViewPagerBean> viewpagerList = new ArrayList<>();
    private List<ShopTypeBean> list = new ArrayList<>();
    private ShopTypeListAdapter shopTypeListAdapter;
    private boolean isMove = false;

    private static class MyHandler extends Handler {
        private final WeakReference<FreshShopActivity> mActivity;
        private FreshShopActivity instance;

        public MyHandler(FreshShopActivity activity) {
            mActivity = new WeakReference<FreshShopActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            instance = mActivity.get();
            switch (msg.what) {
                case 1:
                    int currentItem = instance.vpShop.getCurrentItem();
                    currentItem++;
                    instance.vpShop.setCurrentItem(currentItem);
                    break;
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh_shop);
        ButterKnife.bind(this);
//        tvStatusColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCallPerson.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        rvShopList.setLayoutManager(new LinearLayoutManager(mContext));
        shopTypeListAdapter = new ShopTypeListAdapter(this, list);
        rvShopList.setAdapter(shopTypeListAdapter);
        imageViewList = new ArrayList<>();
        initData();
        initViewPager();
        vpShop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                //轮播图改变，圆点改变的监听
                if (viewpagerList.size() == 2) {
                    i = i % (imageViewList.size() / 2);
                    rgVpItem.check(rgVpItem.getChildAt(i).getId());
                } else {
                    i = i % imageViewList.size();
                    rgVpItem.check(rgVpItem.getChildAt(i).getId());
                }
                imageViewList.get(i).setOnTouchListener(new View.OnTouchListener() {
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
                                if (timer == null) {
                                    timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            int currentItem = vpShop.getCurrentItem();
                                            currentItem++;
                                            Message message = myHandler.obtainMessage();
                                            message.obj = currentItem;
                                            message.what = 1;
                                            myHandler.sendMessage(message);
                                        }
                                    }, 2000, 2000);
                                }
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                if (timer == null && !isMove) {
                                    timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            int currentItem = vpShop.getCurrentItem();
                                            currentItem++;
                                            Message message = myHandler.obtainMessage();
                                            message.obj = currentItem;
                                            message.what = 1;
                                            myHandler.sendMessage(message);
                                        }
                                    }, 2000, 2000);
                                }
                                break;
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                switch (i) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //点击、滑屏
                        isMove = true;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        //释放
                        isMove = false;
                        if (timer == null && !isMove) {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    int currentItem = vpShop.getCurrentItem();
                                    currentItem++;
                                    Message message = myHandler.obtainMessage();
                                    message.obj = currentItem;
                                    message.what = 1;
                                    myHandler.sendMessage(message);
                                }
                            }, 2000, 2000);
                        }
                        break;
                }
            }
        });
    }

    private void initViewPager() {
        MainApiInterfaces mainInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("position", "mall_index");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = mainInterfaces.getInfoImg(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONArray array = new JSONArray((String) result);
                Gson gson = new Gson();
                imageViewList.clear();
                for (int i = 0; i < array.length(); i++) {
                    MainViewPagerBean mainViewPagerBean = gson.fromJson(array.getJSONObject(i).toString(), MainViewPagerBean.class);
                    imageView = new AnyRoundImageView(mContext);
                    imageView.setBorderRadius(20);
                    imageView.setCorners_top_left(false);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setCorners_top_right(false);
                    imageView.setType(TYPE_ROUND);
                    PictureHelper.setImageView(mContext, mainViewPagerBean.getIamge(), imageView, R.drawable.img_loading);
                    imageViewList.add(imageView);
                    viewpagerList.add(mainViewPagerBean);
                }
                if (imageViewList.size() > 0) {
                    for (int i = 0; i < imageViewList.size(); i++) {
                        // 自定义显示下面的小点
                        RadioButton button = new RadioButton(mContext);
                        button.setButtonDrawable(new StateListDrawable());
                        button.setId(i);
                        button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.vp_choose_status));
                        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                Utils.dip2px(mContext, 6));
                        if (i != 0) {
                            layoutParams.setMargins(Utils.dip2px(mContext, 4), 0, 0, 0);
                        } else {
                            layoutParams.setMargins(0, 0, 0, 0);
                        }
                        rgVpItem.addView(button, layoutParams);
                    }
                    if (imageViewList.size() == 2) {
                        for (int i = 0; i < 2; i++) {
                            imageView = new AnyRoundImageView(mContext);
                            imageView.setBorderRadius(20);
                            imageView.setCorners_top_left(false);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setCorners_top_right(false);
                            imageView.setType(TYPE_ROUND);
                            PictureHelper.setImageView(mContext, viewpagerList.get(i).getIamge(), imageView, R.drawable.img_loading);
                            imageViewList.add(imageView);
                        }
                    }
                    rgVpItem.check(rgVpItem.getChildAt(0).getId());

                } else {
                    imageView = new AnyRoundImageView(mContext);
                    imageView.setBorderRadius(20);
                    imageView.setCorners_top_left(false);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setCorners_top_right(false);
                    imageView.setType(TYPE_ROUND);
                    imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_loading));
                    imageViewList.add(imageView);
                }
                vpShop.setAdapter(new MyPagerAdapter(imageViewList));
                if (viewpagerList.size() == 1) {
                    vpShop.setPagingCount(false);
                }
                if (imageViewList.size() > 0) {
                    vpShop.setCurrentItem(imageViewList.size() * 8);
                }
                setTimer(imageViewList.size());
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopType(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        String toString = object.toString();
                        Gson gson = new Gson();
                        ShopTypeBean typeBean = gson.fromJson(toString, ShopTypeBean.class);
                        list.add(typeBean);
                    }
                    shopTypeListAdapter.addData(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_search:
                intent.setClass(mContext, SearchShopActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_search:
                intent.setClass(mContext, SearchShopActivity.class);
                startActivity(intent);
                break;
            case R.id.img_call_person:
//                Utils.callPerson(this,"是否拨打电话0855-8552588","0855-8552588");
                intent.setClass(mContext, UserAgreementActivity.class);
                intent.putExtra("name", "在线客服");
                intent.putExtra("webContent", "http://p.qiao.baidu.com/cps/chat?siteId=14492024&userId=29966678&cp=&cr=APP&cw=");
                startActivity(intent);
                break;
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<AnyRoundImageView> imgs;

        public MyPagerAdapter(List<AnyRoundImageView> imgs) {
            this.imgs = imgs;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            position = position % imgs.size();
            AnyRoundImageView imageView = imgs.get(position);
            final int finalPosition;
            if (viewpagerList.size() == 2) {
                position = position % (imgs.size() / 2);
                finalPosition = position;
            } else {
                finalPosition = position;
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (viewpagerList.size() > 0) {
                        if (viewpagerList.get(finalPosition).getAdvertisement_type().equals("H5web")) {
                            intent.setClass(mContext, ShopDetailWebActivity.class);
                            intent.putExtra("webContent", viewpagerList.get(finalPosition).getUrl());
                            intent.putExtra("name", viewpagerList.get(finalPosition).getTitle());
                            startActivity(intent);
                        } else {
                            intent.setClass(mContext, ShopDetailActivity.class);
                            intent.putExtra("id", viewpagerList.get(finalPosition).getTo_id());
                            startActivity(intent);
                        }
                    }
                }
            });
            ViewGroup parent = (ViewGroup) imageView.getParent();
            if (parent != null) {
                parent.removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            if (imageViewList.size() == 1) {
                return 1;
            } else if (imageViewList.size() == 0) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimer(imageViewList.size());
    }

    public void setTimer(int size) {
        if (timer == null && size > 1) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    int currentItem = vpShop.getCurrentItem();
                    currentItem++;
                    Message message = myHandler.obtainMessage();
                    message.obj = currentItem;
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            }, 5000, 5000);
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

}
