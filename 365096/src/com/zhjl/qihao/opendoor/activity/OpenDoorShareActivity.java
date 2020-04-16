package com.zhjl.qihao.opendoor.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenDoorShareActivity extends VolleyBaseActivity {
    @BindView(R.id.img_code)
    ImageView imgCode;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_wx_share)
    TextView tvWxShare;
    @BindView(R.id.tv_qq_share)
    TextView tvQqShare;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    private byte[] codes;
    private boolean isAllGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_door_share);
        ButterKnife.bind(this);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        codes = getIntent().getByteArrayExtra("code");
        if (codes != null && codes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(codes, 0, codes.length);
            imgCode.setImageBitmap(bitmap);
        }
        tvTitle.setText(mSession.getNick());
        tvContent.setText(mSession.getSign());
        PictureHelper.setHeadImageView(mContext, mSession.getSmallHeadPicPath(), imgHead, R.drawable.ic_head);
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    @OnClick({R.id.tv_wx_share, R.id.tv_qq_share, R.id.img_back})
    public void onViewClicked(View view) {
        Bitmap bitmap = view2Bitmap(rlContent);
        switch (view.getId()) {
            case R.id.tv_wx_share:
                showShareWX(bitmap);
                break;
            case R.id.tv_qq_share:
                showShareQQ(bitmap);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 将view转化为bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 123) {
            isAllGranted = true;
            for (int i = 0; i < grantResults.length; i++) {

                //判断权限的结果，如果点击不在提示
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        RequestPermissionUtils.showShortCut2(this, "开启分享需要该权限！", findViewById(android.R.id.content).getRootView());
                    }
                }
            }
            if (isAllGranted) {

            } else {
                Toast.makeText(mContext, "开启分享需要该权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
