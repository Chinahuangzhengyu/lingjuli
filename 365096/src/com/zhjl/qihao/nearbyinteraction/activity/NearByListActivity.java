package com.zhjl.qihao.nearbyinteraction.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.nearbyinteraction.bean.NearByItemBean;
import com.zhjl.qihao.nearbyinteraction.fragment.NearbyListFragment;
import com.zhjl.qihao.zq.ApiHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity.RESULT_NOTE_DATA;
import static com.zhjl.qihao.nearbyinteraction.activity.NearBySendNoteActivity.RESULT_SEND_NOTE;
import static com.zhjl.qihao.nearbyinteraction.adapter.NearByListAdapter.REQUEST_DELETE_NOTE;
import static com.zhjl.qihao.nearbyinteraction.adapter.NearByNoteContentAdapter.RESULT_DELETE_NOTE;

public class NearByListActivity extends VolleyBaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.tab_nearby_list)
    TabLayout tabNearbyList;
    @BindView(R.id.vp_nearby_list)
    ViewPager vpNearbyList;
    private ArrayList<NearByItemBean.DataBean> title = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private String nearbyId;
    private int pageIndex = 1;
    public static final int REQUEST_SEND_NOTE = 0x633;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_list);
        ButterKnife.bind(this);
        String nearbyName = getIntent().getStringExtra("nearbyName");
        nearbyId = getIntent().getStringExtra("nearbyId");
        initData();
    }

    private void initData() {
        NearByInterfaces apiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        HashMap<String, Object> map = new HashMap();
        map.put("forumLabelTypeId", nearbyId);
        Call<ResponseBody> call = apiInterfaces.nearbyTypeList(map);
        activityRequestData(call, NearByItemBean.class, new RequestResult<NearByItemBean>() {
            @Override
            public void success(NearByItemBean result, String message) throws Exception {
                title.clear();
                title.addAll(result.getData());
                fragmentList.clear();
                for (int i = 0; i < title.size(); i++) {
                    fragmentList.add(NearbyListFragment.getInstance(nearbyId, title.get(i)));
                }
                vpNearbyList.setAdapter(new NearByListFragmentAdapter(getSupportFragmentManager()));
                tabNearbyList.setupWithViewPager(vpNearbyList);
                customTab();
            }

            @Override
            public void fail() {

            }
        });

    }

    /**
     * 自定义样式
     */
    private void customTab() {
        for (int i = 0; i < tabNearbyList.getTabCount(); i++) {
            View view = View.inflate(mContext, R.layout.nearby_tab_view, null);
            LinearLayout llTabBg = view.findViewById(R.id.ll_tab_bg);
            TextView tabTitle = view.findViewById(R.id.tv_tab_title);
            tabTitle.setText(title.get(i).getTopicName());
            float measureText = tabTitle.getPaint().measureText(tabTitle.getText().toString());
            ViewGroup.LayoutParams lp = llTabBg.getLayoutParams();
            lp.width = (int) measureText;
            llTabBg.setLayoutParams(lp);
            if (i == 0) {
                tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                tabTitle.setTypeface(Typeface.DEFAULT_BOLD);
                llTabBg.setVisibility(View.VISIBLE);
            } else {
                tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.ff999999));
                tabTitle.setTypeface(Typeface.DEFAULT);
                llTabBg.setVisibility(View.GONE);
            }
            TabLayout.Tab tab = tabNearbyList.getTabAt(i);
            tab.setCustomView(view);
        }
        tabNearbyList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    View view = tab.getCustomView();
                    LinearLayout llTabBg = view.findViewById(R.id.ll_tab_bg);
                    TextView tabTitle = view.findViewById(R.id.tv_tab_title);
                    tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                    tabTitle.setTypeface(Typeface.DEFAULT_BOLD);
                    llTabBg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    View view = tab.getCustomView();
                    LinearLayout llTabBg = view.findViewById(R.id.ll_tab_bg);
                    TextView tabTitle = view.findViewById(R.id.tv_tab_title);
                    tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.ff999999));
                    tabTitle.setTypeface(Typeface.DEFAULT);
                    llTabBg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_send_note, R.id.ll_search_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_send_note:
                Intent intent = new Intent(mContext, NearBySendNoteActivity.class);
                intent.putExtra("labelTypeId", nearbyId);
                intent.putParcelableArrayListExtra("title", title);
                startActivityForResult(intent, REQUEST_SEND_NOTE);
                break;
            case R.id.ll_search_note:
                Intent intentSearch = new Intent(mContext, SearchNoteActivity.class);
                intentSearch.putExtra("labelTypeId", nearbyId);
                startActivity(intentSearch);
                break;
        }
    }

    class NearByListFragmentAdapter extends FragmentPagerAdapter {

        public NearByListFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position).getTopicName();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DELETE_NOTE && resultCode == RESULT_NOTE_DATA) {
            fragmentList.get(vpNearbyList.getCurrentItem() > fragmentList.size() ? 0 : vpNearbyList.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }else {
            if (fragmentList.size() > 0) {
                if (vpNearbyList != null) {
                    if (data!=null){
                        NearByItemBean.DataBean dataBean =  data.getParcelableExtra("title");
                        if (dataBean!=null){
                            for (int i = 0; i < title.size(); i++) {
                                if (dataBean.getTopicName().equals(title.get(i).getTopicName())){
                                    vpNearbyList.setCurrentItem(i,false);
                                    fragmentList.get(vpNearbyList.getCurrentItem() > fragmentList.size() ? 0 : vpNearbyList.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
                                }
                            }
                        }else {
                            fragmentList.get(vpNearbyList.getCurrentItem() > fragmentList.size() ? 0 : vpNearbyList.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
                        }
                    }else {
                        fragmentList.get(vpNearbyList.getCurrentItem() > fragmentList.size() ? 0 : vpNearbyList.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
        }

    }
}
