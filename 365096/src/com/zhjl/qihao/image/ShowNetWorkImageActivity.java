package com.zhjl.qihao.image;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author 黄南榆
 * @version 1.0
 * @description 显示一组网络图片(目前网页JS用到了)
 * @date 2014年9月16日
 */
public class ShowNetWorkImageActivity extends VolleyBaseActivity {
    private ViewPager image_pager;
    private LinearLayout viewGroup;
    private ImageView imageView = null;
    private ImageView[] imageViews = null;
    private List<View> Pics;
    private String[] urls;
    private ProgressBar process_loading;
    private long firstMillis = 0;//用于保存时间戳
    private long timeLong = 600;//时间戳
    private boolean isLocal;
    private int[] localPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
//		setFlag(false);
        setFullScreen(true);
        super.onCreate(savedInstanceState);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show_networkimage);
        //第一次请求的时间
        long secondMillis = SystemClock.uptimeMillis();
        long value = secondMillis - firstMillis;
        if (value > timeLong) {
            firstMillis = secondMillis;
            initViewPager();
        }
    }

    private void initViewPager() {
        // TODO Auto-generated method stub
        process_loading = (ProgressBar) findViewById(R.id.process_loading);
        urls = getIntent().getStringArrayExtra("urls");
        String nowImage = getIntent().getStringExtra("nowImage");
        int index = getIntent().getIntExtra("index", -1);
        isLocal = getIntent().getBooleanExtra("isLocal", false);
        if (isLocal) {
            localPics = getIntent().getIntArrayExtra("localPic");
        }
        image_pager = (ViewPager) findViewById(R.id.image_pager);
        viewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        Pics = new ArrayList<View>();
        int pos = 0;
        for (int i = 0; i < urls.length; i++) {
            if (nowImage != null && nowImage.equals(urls[i])) {
                pos = i;
            }
            PhotoView photoView = new PhotoView(mContext);
            Pics.add(photoView);
        }
        if (index != -1) {
            pos = index;
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
        image_pager.setAdapter(new AdvAdapter(Pics));
        image_pager.setCurrentItem(pos);
        image_pager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    private final class GuidePageChangeListener implements OnPageChangeListener {
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
            container.removeView(views.get(position));

        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView itemview = (PhotoView) views.get(position);
//            itemview.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//                @Override
//                public void onPhotoTap(View view, float x, float y) {
//                    exitActivity();
//                }
//
//                @Override
//                public void onOutsidePhotoTap() {
//
//                }
//            });
            itemview.setOnViewTapListener((view, x, y) -> exitActivity());
            itemview.setOnLongClickListener(v -> {
                // TODO Auto-generated method stub
                showDownloadDialog();
                return true;
            });
            if (localPics != null && localPics.length > position ) {
                PictureHelper.setImageView(ShowNetWorkImageActivity.this, localPics[position], itemview, R.drawable.img_loading);
            } else {
                PictureHelper.showPictureWithSquare(ShowNetWorkImageActivity.this, itemview,
                        urls[position].replaceAll("\"", ""));
            }
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

        @Override
        public void startUpdate(View arg0) {
        }
    }

    private void showDownloadDialog() {
        // TODO Auto-generated method stub
        final String[] mItems = {"保存到本地", "取消"};
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
