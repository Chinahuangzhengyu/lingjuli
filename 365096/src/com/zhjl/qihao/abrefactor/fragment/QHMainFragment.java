package com.zhjl.qihao.abrefactor.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.RequestListener;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.abmine.MyCollectionActivity;
import com.zhjl.qihao.abrefactor.NewWebViewActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.abrefactor.bean.ListBean;
import com.zhjl.qihao.abrefactor.bean.MainViewPagerBean;
import com.zhjl.qihao.abrefactor.bean.NearByTypeBean;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abrefactor.view.ViewStyleSetter;
import com.zhjl.qihao.abupapp.UpdateChecker;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.chooseCity.activty.ChoosePropertyActivity;
import com.zhjl.qihao.freshshop.activity.FreshShopActivity;
import com.zhjl.qihao.freshshop.activity.ShopDetailActivity;
import com.zhjl.qihao.freshshop.activity.ShopDetailWebActivity;
import com.zhjl.qihao.mymessage.activity.AllMessageCenterActivity;
import com.zhjl.qihao.mymessage.api.MessageInterface;
import com.zhjl.qihao.nearbyinteraction.activity.NearByInteractionActivity;
import com.zhjl.qihao.nearbyinteraction.activity.NearByListActivity;
import com.zhjl.qihao.propertyservicepay.activity.PropertyMainActivity;
import com.zhjl.qihao.push.ExampleUtil;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CannotScrollViewpager;
import com.zhjl.qihao.view.CustomGridView;
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
import java.util.concurrent.ScheduledExecutorService;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 作者： 黄郑宇
 * 时间： 2018/6/7
 * 类作用：最新的主页
 */

public class QHMainFragment extends VolleyBaseFragment implements RequestListener<JSONObject> {

    private View view;// 创建一个视图
    /**
     * 广告ImageView
     */
    private List<RoundImageView> mImageList;

    /**
     * 广告播放ViewPager
     */
    private CannotScrollViewpager mViewPager;
    /**
     * 点指示容器
     */
    private RadioGroup dot_group;


    private long firstMillis = 0;// 用于保存时间戳
    private long timeLong = 600;// 时间戳
    private Activity mContext;
    private NestedScrollView scrollView;
    private ImageView iv_notification;
    private TextView tv_title;
    private LinearLayout ll_title;
    private CannotScrollViewpager mCover;
    private View tv_notice_not_read;
    private ScheduledExecutorService scheduledExecutorService;
    private List<MainViewPagerBean> viewpagerList = new ArrayList<>();
    private List<RoundImageView> imgList = new ArrayList<>();
    private List<MainViewPagerBean> viewpagerProductList = new ArrayList<>();
    private List<View> imgProductList = new ArrayList<>();
    private ViewPagerAdapter adapter;
    private GridView gvList;
    private List<ListBean> list;
    private TextView tvSelectMore;
    private RelativeLayout rlLoading;
    private TextView statusBarColor;
    private CustomGridView neighborhoodInteraction;
    private TextView tvTwoSelectMore;
    public NeighborhoodAdapter neighborhoodAdapter;
    private ArrayList<NearByTypeBean.DataBean> data = new ArrayList<>();
    private Timer timer;
    public static boolean isForeground = false;
    private SwipeRefreshLayout swRefresh;
    private ProductViewPagerAdapter productViewPagerAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (RefactorMainActivity) context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_qhmain, null);
        }
        registerMessageReceiver();
        initUI();
        initNearByType();
        initMainViewPager();
        initMain2ViewPager();
        return view;
    }


    private void initMain2ViewPager() {
        MainApiInterfaces mainInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("position", "recommend_item");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> infoImg = mainInterfaces.getInfoImg(body);
        fragmentRequestPhpData(infoImg, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONArray array = new JSONArray((String) result);
                Gson gson = new Gson();
                viewpagerProductList.clear();
                imgProductList.clear();
                for (int i = 0; i < array.length(); i++) {
                    MainViewPagerBean mainViewPagerBean = gson.fromJson(array.getJSONObject(i).toString(), MainViewPagerBean.class);
                    viewpagerProductList.add(mainViewPagerBean);
                    View view = View.inflate(mContext, R.layout.main_product_viewpager, null);
                    ImageView imageView = view.findViewById(R.id.img_shop);
                    TextView textView = view.findViewById(R.id.tv_shop_name);
                    textView.setText(mainViewPagerBean.getTitle());
                    PictureHelper.setImageView(mContext, mainViewPagerBean.getIamge(), imageView, R.drawable.img_loading);
                    imgProductList.add(view);
                }

                if (viewpagerProductList.size() > 0) {
                    if (imgProductList.size() > 2) {
                        mCover.setPagingCount(true);
                    } else {
                        mCover.setPagingCount(false);
                    }
                    productViewPagerAdapter = new ProductViewPagerAdapter(imgProductList);
                    mCover.setAdapter(productViewPagerAdapter);
                    mCover.setPageMargin(Utils.dip2px(mContext, 10));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initMainViewPager() {
        MainApiInterfaces mainInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("position", "index");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> infoImg = mainInterfaces.getInfoImg(body);
        fragmentRequestPhpData(infoImg, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONArray array = new JSONArray((String) result);
                Gson gson = new Gson();
                viewpagerList.clear();
                imgList.clear();
                for (int i = 0; i < array.length(); i++) {
                    MainViewPagerBean mainViewPagerBean = gson.fromJson(array.getJSONObject(i).toString(), MainViewPagerBean.class);
                    viewpagerList.add(mainViewPagerBean);
                    RoundImageView roundImageView = new RoundImageView(mContext);
                    roundImageView.setRoundImg(mContext, 6);
                    roundImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    PictureHelper.setImageView(mContext, mainViewPagerBean.getIamge(), roundImageView, R.drawable.img_loading);
                    imgList.add(roundImageView);

                }

                if (viewpagerList.size() > 0) {
                    initData(viewpagerList);
                    initViewPager(viewpagerList);
                    setTimer(viewpagerList.size());
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    class ProductViewPagerAdapter extends PagerAdapter {
        private List<View> imgList;


        public ProductViewPagerAdapter(List<View> imgList) {
            this.imgList = imgList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (imgList.size() == 1) {
                return 1;
            } else if (imgList.size() == 0) {
                return 0;
            }
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public float getPageWidth(int position) {
            return 0.35f;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = imgList.get(position);
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (viewpagerProductList.size() > 0) {
                        if (viewpagerProductList.get(position).getAdvertisement_type().equals("H5web")) {
                            intent.setClass(mContext, ShopDetailWebActivity.class);
                            intent.putExtra("webContent", viewpagerProductList.get(position).getUrl());
                            intent.putExtra("name", viewpagerProductList.get(position).getTitle());
                            startActivity(intent);
                        } else {
                            intent.setClass(mContext, ShopDetailActivity.class);
                            intent.putExtra("id", viewpagerProductList.get(position).getTo_id());
                            startActivity(intent);
                        }
                    }
                }
            });
            container.addView(view);
            return view;
        }
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, filter);
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
//                    JSONObject object = new JSONObject(extras);
//                    object.optString("messageId");
//                    String type = object.optString("type");
//                    if (!type.equals("1")){
//                        String url = object.optString("url");
//                    }
                    initNoReadMessage();
                }
            } catch (Exception e) {
            }
        }
    }

    private void initNoReadMessage() {
        MessageInterface messageInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(MessageInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = messageInterface.newReadMessage(body);
        fragmentRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    JSONObject data = object.optJSONObject("data");
                    int noReadNum = data.optInt("noReadNum");
                    if (noReadNum > 0) {
                        tv_notice_not_read.setVisibility(View.VISIBLE);
                    } else {
                        tv_notice_not_read.setVisibility(View.GONE);
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

    private void initUI() {
        statusBarColor = view.findViewById(R.id.status_bar_color);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        swRefresh = view.findViewById(R.id.sw_refresh);

        //刷新
        swRefresh.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.new_theme_color), ContextCompat.getColor(mContext, R.color.new_theme_color2));
        swRefresh.setOnRefreshListener(() -> {
//            timer.cancel();
//            timer = null;
            initNearByType();
            initMainViewPager();
            initMain2ViewPager();
        });

        rlLoading = (RelativeLayout) view.findViewById(R.id.rl_loading);
        rlLoading.setVisibility(View.VISIBLE);
        tvTwoSelectMore = view.findViewById(R.id.tv_two_select_more);
        tvTwoSelectMore.setOnClickListener(this);
        tvSelectMore = (TextView) view.findViewById(R.id.tv_select_more);
        tvSelectMore.setOnClickListener(this);
        gvList = (GridView) view.findViewById(R.id.gv_list);
        list = new ArrayList<>();
        list.add(new ListBean("物业服务", R.drawable.wyfw));
        list.add(new ListBean("7好鲜生", R.drawable.qhxs));
        list.add(new ListBean("便民服务", R.drawable.bmfw));
        list.add(new ListBean("牛牛优选", R.drawable.nnyx));
        list.add(new ListBean("同城快送", R.drawable.tcks));
        gvList.setAdapter(new MyBaseAdapter(list));

        mCover = view.findViewById(R.id.cover);


        iv_notification = (ImageView) this.view.findViewById(R.id.iv_notification);
        iv_notification.setOnClickListener(this);//消息
        //邻里互动
        neighborhoodInteraction = view.findViewById(R.id.gv_neighborhood_interaction);
        neighborhoodAdapter = new NeighborhoodAdapter();
        neighborhoodInteraction.setAdapter(neighborhoodAdapter);
        TextView tvTwoSelectMore = view.findViewById(R.id.tv_two_select_more);
        tvTwoSelectMore.setOnClickListener(this);

        tv_title = (TextView) this.view.findViewById(R.id.tv_title);
        tv_title.setText(mSession.getSmallCommunityName());//设置小区名称
        ll_title = (LinearLayout) this.view.findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);//切换小区

        tv_notice_not_read = (View) view.findViewById(R.id.tv_notice_not_read);//未读消息

        mViewPager = (CannotScrollViewpager) this.view.findViewById(R.id.vp_main_head);
        ViewStyleSetter viewStyleSetter = new ViewStyleSetter(mViewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewStyleSetter.setRound(Utils.dip2px(mContext, 12));//实现圆角效果
        }
        dot_group = (RadioGroup) this.view.findViewById(R.id.dot_group);
        scrollView = (NestedScrollView) this.view.findViewById(R.id.scrollview);
        scrollView.setVisibility(View.GONE);

        neighborhoodInteraction.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), NearByListActivity.class);
            intent.putExtra("nearbyName", data.get(position).getLabelName());
            intent.putExtra("nearbyId", data.get(position).getId());
            startActivity(intent);
        });

        gvList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            switch (position) {
                case 0:              //物业服务
                    // && Integer.parseInt(mSession.getUserType()) > 0
                    if (islogined() && mSession.getUserType() != null) {
                        intent.setClass(mContext, PropertyMainActivity.class);
                        startActivity(intent);
                    } else {
                        showtips();
                    }
                    break;
                case 1:               //7好鲜生
                    if (islogined()) {
                        intent.setClass(mContext, FreshShopActivity.class);
                        startActivity(intent);
                    } else {
                        showtips();
                    }
                    break;
                case 2:                 //便民服务
                    intent.setClass(mContext, MyCollectionActivity.class);
                    intent.putExtra("name", "便民服务");
                    startActivity(intent);
                    break;
                case 3:                 //牛牛优选
                    intent.setClass(mContext, MyCollectionActivity.class);
                    intent.putExtra("name", "牛牛优选");
                    startActivity(intent);
                    break;
                case 4:                 //同城快送
                    intent.setClass(mContext, MyCollectionActivity.class);
                    intent.putExtra("name", "同城快送");
                    startActivity(intent);
                    break;
                case 5:                 //周边旺铺
//                        intent.setClass(mContext, NewWebViewActivity.class);
//                        intent.putExtra("name", list.get(position).getStr());
//                        intent.putExtra("webUrl", webUrl[3]);
//                        startActivity(intent);
                    break;
            }
        });
    }

    //轮播图数据初始化
    public void initData(List<MainViewPagerBean> list) {
        RoundImageView mImageView;
        LinearLayout.LayoutParams params;
        mImageList = new ArrayList<RoundImageView>();//存放图片
        dot_group.removeAllViewsInLayout();
        if (list.size() == 2) {
            for (int i = 0; i < list.size() * 2; i++) {
                // 代码设置图片并显示
                mImageView = new RoundImageView(mContext);
                mImageView.setRoundImg(mContext, 12);
                PictureHelper.setImageView(mContext, list.get(i % 2).getIamge(), mImageView, R.drawable.img_loading);
                mImageList.add(mImageView);
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                // 代码设置图片并显示
                mImageView = new RoundImageView(mContext);
                mImageView.setRoundImg(mContext, 12);
                PictureHelper.setImageView(mContext, list.get(i).getIamge(), mImageView, R.drawable.img_loading);
                mImageList.add(mImageView);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            // 自定义显示下面的小点
            // 创建RadioButton
            RadioButton button = new RadioButton(mContext);
            // 把RadioButton加入到RadioGroup中

            button.setButtonDrawable(new StateListDrawable());
            button.setId(i);
            button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.binner_status));
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Utils.dip2px(mContext, 6));
            if (i != 0) {
                layoutParams.setMargins(Utils.dip2px(mContext, 6), 0, 0, 0);
            } else {
                layoutParams.setMargins(0, 0, 0, 0);
            }
            dot_group.addView(button, layoutParams);
        }
        dot_group.clearCheck();
        dot_group.check(dot_group.getChildAt(0).getId());
    }

    public void setTimer(int size) {
        if (timer == null && size > 1) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    int currentItem = mViewPager.getCurrentItem();
                    currentItem++;
                    Message message = myHandler.obtainMessage();
                    message.obj = currentItem;
                    message.what = 11;
                    myHandler.sendMessage(message);
                }
            }, 5000, 5000);
        }

    }

    //初始化ViewPager
    @SuppressLint("ClickableViewAccessibility")
    public void initViewPager(final List<MainViewPagerBean> viewpagerList) {
        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        if (viewpagerList.size() <= 1) {
            mViewPager.setPagingCount(false);
        } else {
            mViewPager.setPagingCount(true);
        }
        mViewPager.setCurrentItem(viewpagerList.size() * 8);
        mViewPager.setOnTouchListener((v, event) -> {
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
                    setTimer(viewpagerList.size());
                    break;
            }
            return false;
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                //轮播图改变，圆点改变的监听
                if (viewpagerList.size() == 2) {
                    position = position % (mImageList.size() / 2);
                    dot_group.check(dot_group.getChildAt(position).getId());
                } else {
                    position = position % mImageList.size();
                    dot_group.check(dot_group.getChildAt(position).getId());
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    /**
     * 自定义Adapter
     */
    private class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (viewpagerList.size() == 1) {
                return 1;
            } else if (viewpagerList.size() == 0) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            //super.destroyItem(container, position, object);
//            view.removeView((View) object);
        }

        //设置初始化和点击监听
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            position = position % mImageList.size();
            RoundImageView imageView = mImageList.get(position);
            ViewGroup parent = (ViewGroup) imageView.getParent();
            if (parent != null) {
                parent.removeView(imageView);
            }
            final int finalPosition;
            if (viewpagerList.size() == 2) {
                position = position % (mImageList.size() / 2);
                finalPosition = position;
            } else {
                finalPosition = position;
            }
            imageView.setOnClickListener(v -> {
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
            });
            view.addView(imageView);
            return imageView;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isForeground = false;
        myHandler.removeCallbacks(null);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<QHMainFragment> mFragment;
        private QHMainFragment instance;

        public MyHandler(QHMainFragment newMainFragment) {
            mFragment = new WeakReference<QHMainFragment>(newMainFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            instance = mFragment.get();
            int currentItem = instance.mViewPager.getCurrentItem();
            currentItem += 1;
            instance.mViewPager.setCurrentItem(currentItem);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            NewStatusBarUtils.fullScreen(getActivity());
            if (!getActivity().getIntent().getBooleanExtra("Tourist", false))
                UpdateChecker.checkIsShowMainDialog((VolleyBaseActivity) getActivity());
        }
    }

    /**
     * 播放广告图片
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        isForeground = true;
        //防止网络请求频繁
        long secondMillis = SystemClock.uptimeMillis();//开机到现在的毫秒数，不包含待机时间
        long value = secondMillis - firstMillis;
        if (value > timeLong) {
            firstMillis = secondMillis;
        }
        try {
            if (Integer.parseInt(mSession.getUserType()) == -1) {
                return;
            }
            initNoReadMessage();
            setTimer(viewpagerList.size());
            tv_title.setText(mSession.getSmallCommunityName());//设置小区名称
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();//节约intent的构造
        switch (v.getId()) {
            case R.id.iv_notification://消息页面跳转
                if (islogined()) {
                    intent.setClass(getActivity(), AllMessageCenterActivity.class);
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.tips_please_login),
                            Toast.LENGTH_SHORT).show();
                    intent.setClass(mContext, UserLoginActivity.class);
                    intent.putExtra("Visitor", "visitor");
                }
                startActivity(intent);
                break;

            case R.id.ll_title://切换小区
                intent.setClass(mContext, ChoosePropertyActivity.class);
                intent.putExtra(ChoosePropertyActivity.PROPERTY_MODE, ChoosePropertyActivity.PROPERTY_SWITCH);
                startActivity(intent);
                break;

            case R.id.rl_property://跳转物业页面
                if (islogined()) {
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.tips_please_login), Toast.LENGTH_SHORT).show();
                    intent.setClass(mContext, UserLoginActivity.class);
                    intent.putExtra("Visitor", "visitor");
                    startActivity(intent);
                }
                break;

            case R.id.rl_nearby://热门周边
                //intent.setClass(getActivity(),BillConfirmPayActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_select_more:
                intent.setClass(mContext, NewWebViewActivity.class);
                intent.putExtra("name", "更多");
                startActivity(intent);
                break;
            case R.id.tv_two_select_more:
                intent.setClass(mContext, NearByInteractionActivity.class);
                intent.putParcelableArrayListExtra("data", data);
                startActivity(intent);
                break;
        }

    }

    private void initNearByType() {
        MainApiInterfaces apiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", 1);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = apiInterfaces.nearbyType(body);
        fragmentRequestData(call, NearByTypeBean.class, new RequestResult<NearByTypeBean>() {
            @Override
            public void success(NearByTypeBean result, String message) {
                data.clear();
                if (result.getData().size() != 0) {
                    data = (ArrayList<NearByTypeBean.DataBean>) result.getData();
                    neighborhoodAdapter.addData(result.getData());
                } else {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                rlLoading.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                swRefresh.setRefreshing(false);
            }

            @Override
            public void fail() {
                rlLoading.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                swRefresh.setRefreshing(false);
                Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyBaseAdapter extends BaseAdapter {
        private List<ListBean> listBeans;

        public MyBaseAdapter(List<ListBean> list) {
            listBeans = list;
        }

        @Override
        public int getCount() {
            return listBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return listBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.main_gv_item, null);
            ImageView img_main = (ImageView) convertView
                    .findViewById(R.id.img_main);
            img_main.setImageResource(listBeans.get(position).getRefences());
            TextView tv_main = (TextView) convertView.findViewById(R.id.tv_main);
            tv_main.setText(listBeans.get(position).getStr());
            return convertView;
        }
    }

    public class NeighborhoodAdapter extends BaseAdapter {
        private List<NearByTypeBean.DataBean> listBeans = new ArrayList<>();

        public void addData(List<NearByTypeBean.DataBean> list) {
//            listBeans.clear();
//            listBeans.addAll(list);
//            for (int i = 0; i < listBeans.size(); i++) {
//                if (listBeans.get(i).getLabelName().equals("兴趣爱好")){
//                    listBeans.remove(i);
//                }else if (listBeans.get(i).getLabelName().equals("心灵鸡汤")){
//                    listBeans.remove(i);
//                }
//            }
            listBeans = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
//            if (listBeans.size() != 0 && listBeans.size() > 6) {
//                return 6;
//            }
            return listBeans.size() == 0 ? 0 : listBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return listBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.neighborhood_interaction_item, null);
            }
            TextView tvNeighborhoodName = convertView.findViewById(R.id.tv_neighborhood_name);
            RoundImageView imgNeighborhood = convertView.findViewById(R.id.img_neighborhood);
            tvNeighborhoodName.setText(listBeans.get(position).getLabelName());
//            PictureHelper.setImageView(mContext,listBeans.get(position).getHomeIcon(),imgNeighborhood,R.drawable.img_loading);
            switch (listBeans.get(position).getLabelName()) {
                case "随手美拍":
                    imgNeighborhood.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_main_ssmp));
                    break;
                case "生活杂谈":
                    imgNeighborhood.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_main_ahzt));
                    break;
                case "兴趣爱好":
                    imgNeighborhood.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_main_xqah));
                    break;
                case "二手闲置":
                    imgNeighborhood.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_main_esxz));
                    break;
                case "心灵鸡汤":
                    imgNeighborhood.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_main_xljt));
                    break;
                case "互利互助":
                    imgNeighborhood.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_main_hlhd));
                    break;
            }
            return convertView;
        }
    }
}
