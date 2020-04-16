package com.zhjl.qihao.freshshop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.abrefactor.bean.MainViewPagerBean;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopMoreTypeBean;
import com.zhjl.qihao.freshshop.fragment.ShopContentFragment;
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
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;
import retrofit2.Call;

public class ShopTypeListDetailActivity extends VolleyBaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_call_person)
    ImageView imgCallPerson;
    @BindView(R.id.vp_shop_title)
    CannotScrollViewpager vpShopTitle;
    @BindView(R.id.vtab_shop_type)
    VerticalTabLayout vtabShopType;
    @BindView(R.id.vp_shop_content)
    CannotScrollViewpager vpShopContent;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_shop_cart_sum)
    TextView tvShopCartSum;
    @BindView(R.id.rl_img_shop_car)
    RelativeLayout rlImgShopCar;
    @BindView(R.id.img_shop_car)
    ImageView imgShopCar;
    @BindView(R.id.rl_shop_car)
    RelativeLayout rlShopCar;
    private Timer timer;
    private String[] title;
    private PopupWindow shopTypePop;
    private List<RoundImageView> imageViewList;
    private List<Fragment> fragments = new ArrayList<>();
    private int typeId;
    private List<ShopMoreTypeBean> list = new ArrayList<>();
    private List<MainViewPagerBean> viewpagerList = new ArrayList<>();
    private String name;
    private int position;
    private final MyHandler myHandler = new MyHandler(this);
    private int isMove = 0;
    private long number;

    private static class MyHandler extends Handler {
        private final WeakReference<ShopTypeListDetailActivity> mActivity;
        private ShopTypeListDetailActivity instance;

        public MyHandler(ShopTypeListDetailActivity activity) {
            mActivity = new WeakReference<ShopTypeListDetailActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            instance = mActivity.get();
            switch (msg.what) {
                case 1:
                    int currentItem = instance.vpShopTitle.getCurrentItem();
                    currentItem++;
                    instance.vpShopTitle.setCurrentItem(currentItem);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_type_list_detail);
        ButterKnife.bind(this);
        typeId = getIntent().getIntExtra("typeId", 0);
        name = getIntent().getStringExtra("name");
        imageViewList = new ArrayList<>();
        initData();
        initViewPager();


        vtabShopType.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                vpShopContent.setCurrentItem(position, false);
                for (int i = 0; i < vtabShopType.getTabCount(); i++) {
                    if (position == i) {
                        vtabShopType.getTabAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                    } else {
                        vtabShopType.getTabAt(i).setBackgroundColor(Color.parseColor("#F5F6FA"));
                        ShopContentFragment fragment = (ShopContentFragment) fragments.get(i);
                        fragment.goneView();
                    }
                }
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    private void initShopSum() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getShopCarNum(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                number = object.optLong("number");
                if (number == 0) {
                    tvShopCartSum.setVisibility(View.INVISIBLE);
                } else {
                    tvShopCartSum.setText(number + "");
                    tvShopCartSum.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void fail() {

            }
        });
    }


    private void initViewPager() {
        MainApiInterfaces mainInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("position", "mall_category");
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
                    RoundImageView imageView = new RoundImageView(mContext);
                    imageView.setRoundImg(mContext, 6);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (!isFinishing()){
                        PictureHelper.setImageView(mContext, mainViewPagerBean.getIamge(), imageView, R.drawable.img_loading);
                    }
                    imageViewList.add(imageView);
                    viewpagerList.add(mainViewPagerBean);
                }
                if (imageViewList.size() <= 1) {
                    vpShopTitle.setPagingCount(false);
                } else {
                    vpShopTitle.setPagingCount(true);
                }
                if (imageViewList.size() == 2) {
                    for (int i = 0; i < 2; i++) {
                        RoundImageView imageView = new RoundImageView(mContext);
                        imageView.setRoundImg(mContext, 6);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        if (!isFinishing()){
                            PictureHelper.setImageView(mContext, viewpagerList.get(i).getIamge(), imageView, R.drawable.img_loading);
                        }
                        imageViewList.add(imageView);
                    }
                }
                initTitleViewPager(viewpagerList);
                setTimer(viewpagerList.size());
            }

            @Override
            public void fail() {

            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTitleViewPager(final List<MainViewPagerBean> viewpagerList) {
        vpShopTitle.setAdapter(new TitlePageAdapter(imageViewList));
        if (viewpagerList.size() > 0) {
            vpShopTitle.setCurrentItem(viewpagerList.size() * 8);
        }
        vpShopTitle.setOnTouchListener(new View.OnTouchListener() {
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
                        setTimer(viewpagerList.size());
                        break;
                }
                return false;
            }
        });
    }

    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", typeId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopTypeMore(body);
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
                        ShopMoreTypeBean typeBean = gson.fromJson(toString, ShopMoreTypeBean.class);
                        list.add(typeBean);
                    }
                    String[] strings = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        strings[i] = list.get(i).getName();
                        fragments.add(ShopContentFragment.getInstance(list.get(i).getChildren()));
                        if (name.equals(list.get(i).getName())) {
                            position = i;
                        }
                    }
                    vpShopContent.setPagingCount(false);
                    vpShopContent.setAdapter(new ShopContentFragmentAdapter(getSupportFragmentManager()));
//                    vpShopContent.setOffscreenPageLimit(fragments.size());
                    vtabShopType.setupWithViewPager(vpShopContent);
                    vpShopContent.setCurrentItem(position);
                    vtabShopType.setTabAdapter(new MyTabAdapter());
                    vtabShopType.getTabAt(0).setBackgroundColor(Color.parseColor("#ffffff"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    class MyTabAdapter implements TabAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public ITabView.TabBadge getBadge(int position) {
            return null;
        }

        @Override
        public ITabView.TabIcon getIcon(int position) {
            return null;
        }

        @Override
        public ITabView.TabTitle getTitle(int position) {
            int paddingHeight = Utils.dip2px(mContext, 10);
            ITabView.TabTitle build = new ITabView.TabTitle.Builder().setContent(list.get(position).getName()).setTextColor(Color.parseColor("#1F1F1F"), Color.parseColor("#999999")).setPadding(paddingHeight).build();
            return build;
        }

        @Override
        public int getBackground(int position) {
            return 0;
        }
    }

    class ShopContentFragmentAdapter extends FragmentPagerAdapter {

        public ShopContentFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getName();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    class TitlePageAdapter extends PagerAdapter {

        private List<RoundImageView> imageViewList;

        public TitlePageAdapter(List<RoundImageView> imageViewList) {
            this.imageViewList = imageViewList;
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

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView(imageViewList.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            position = position % imageViewList.size();
            RoundImageView imageView = imageViewList.get(position);
            ViewGroup parent = (ViewGroup) imageView.getParent();
            if (parent != null) {
                parent.removeView(imageView);
            }
            final int finalPosition;
            if (viewpagerList.size() == 2) {
                position = position % (imageViewList.size() / 2);
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
            container.addView(imageView);
            return imageView;
        }
    }


    @OnClick({R.id.ll_search, R.id.img_call_person, R.id.img_back, R.id.rl_shop_car})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_search:
                intent.setClass(mContext,SearchShopActivity.class);
                startActivity(intent);
                break;
            case R.id.img_call_person:
//                Utils.callPerson(this,"是否拨打电话0855-8552588","0855-8552588");
                intent.setClass(mContext, UserAgreementActivity.class);
                intent.putExtra("name","在线客服");
                intent.putExtra("webContent","http://p.qiao.baidu.com/cps/chat?siteId=14492024&userId=29966678&cp=&cr=APP&cw=");
                startActivity(intent);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_shop_car:
                Intent shopIntent = new Intent(mContext, ShopCarActivity.class);
                startActivity(shopIntent);
                break;

        }
    }

    public void setTimer(int size) {
        if (timer == null && size > 1) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    int currentItem = vpShopTitle.getCurrentItem();
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
    protected void onResume() {
        super.onResume();
        initShopSum();
        setTimer(viewpagerList.size());
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
