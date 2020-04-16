package com.zhjl.qihao.abrefactor.convenient.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.util.NewHeaderBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConvenientServiceActivity extends VolleyBaseActivity {

    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.vp_convenient)
    ViewPager vpConvenient;
    @BindView(R.id.gv_convenient)
    GridView gvConvenient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenient_service);
        ButterKnife.bind(this);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        NewHeaderBar.createCommomBack(this, "便民服务", this);
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        List<ImageView> list = new ArrayList<>();
        list.add(imageView);
        vpConvenient.setAdapter(new MyVpAdapter(list));

    }


    class MyVpAdapter extends PagerAdapter {

        private List<ImageView> vpLists;

        public MyVpAdapter(List<ImageView> vpLists) {
            this.vpLists = vpLists;
        }

        @Override
        public int getCount() {
            return vpLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(vpLists.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (vpLists.size() > 0) {
                ImageView view = vpLists.get(position);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "引导图", Toast.LENGTH_SHORT).show();

                    }
                });
                container.addView(view);
                return view;
            }
            return null;
        }
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
