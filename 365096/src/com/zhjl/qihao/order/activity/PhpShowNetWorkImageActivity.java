package com.zhjl.qihao.order.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.image.ImageDownLoad;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;

import java.util.ArrayList;
import java.util.List;

public class PhpShowNetWorkImageActivity extends VolleyBaseActivity {
    private ViewPager image_pager;
    private LinearLayout viewGroup;
    private ImageView imageView = null;
    private ImageView[] imageViews = null;
    private List<View> Pics;
    private String[] urls;
    private ProgressBar process_loading;
    private long firstMillis = 0;//用于保存时间戳
    private long timeLong = 600;//时间戳
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setFlag(false);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show_networkimage);
        //第一次请求的时间
        long secondMillis = SystemClock.uptimeMillis();
        long value = secondMillis - firstMillis;
        if(value > timeLong){
            firstMillis = secondMillis;
            initViewPager();
        }
    }

    private void initViewPager() {
        // TODO Auto-generated method stub
        process_loading = (ProgressBar) findViewById(R.id.process_loading);
        urls = getIntent().getStringArrayExtra("urls");
        String nowImage = getIntent().getStringExtra("nowImage");
        image_pager = (ViewPager) findViewById(R.id.image_pager);
        viewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        Pics = new ArrayList<View>();
        int pos = 0;
        for (int i = 0; i < urls.length; i++) {
            if (nowImage != null && nowImage.equals(urls[i])) {
                pos = i;
            }
            ImageView photoView = new ImageView(mContext);
            Pics.add(photoView);
        }
        // 对imageviews进行填充
        imageViews = new ImageView[urls.length];
        // 小图标
        for (int i = 0; i < urls.length; i++) {
            imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    20, 20);
            layoutParams.setMargins(5, 10, 5, 10);
            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(5, 5, 5, 5);
            imageViews[i] = imageView;
            if (i == pos) {
                imageViews[i]
                        .setBackgroundResource(R.drawable.banner_focus);
            } else {
                imageViews[i]
                        .setBackgroundResource(R.drawable.banner_normal);
            }
            viewGroup.addView(imageViews[i]);
        }
        image_pager.setAdapter(new PhpShowNetWorkImageActivity.AdvAdapter(Pics));
        image_pager.setCurrentItem(pos);
        image_pager.addOnPageChangeListener(new PhpShowNetWorkImageActivity.GuidePageChangeListener());
    }

    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0].setBackgroundResource(R.drawable.banner_focus);
                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.drawable.banner_normal);
                }
            }
        }
    }

    private final class AdvAdapter extends PagerAdapter {
        private List<View> views = null;

        public AdvAdapter(List<View> views) {
            this.views = views;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView itemview = (ImageView) views.get(position);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    exitActivity();
                }
            });
            itemview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                    showDownloadDialog();
                    return true;
                }
            });
            PictureHelper.showPictureWithSquare(PhpShowNetWorkImageActivity.this,itemview,
                    urls[position].toString().replaceAll("\"", ""));
            container.addView(itemview, 0);
            return itemview;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    private void showDownloadDialog() {
        // TODO Auto-generated method stub
        final String[] mItems = { "保存到本地", "取消" };
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("图片操作");
        builder.setItems(mItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击后弹出窗口选择了第几项
                switch (which) {
                    case 0:
                        dialog.dismiss();

                        Toast.makeText(mContext, "开始下载...", Toast.LENGTH_SHORT)
                                .show();
                        new ImageDownLoad(mContext, urls[image_pager
                                .getCurrentItem()].toString().replaceAll("\"", ""),
                                "零距离", "保存图片到本地目录");

                        break;
                    case 1:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        image_pager = null;
        viewGroup = null;
        imageView = null;
        imageViews = null;
        Pics = null;
        System.gc();
    }
}
