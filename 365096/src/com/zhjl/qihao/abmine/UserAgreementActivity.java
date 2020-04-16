package com.zhjl.qihao.abmine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.PictureUtil;
import com.zhjl.qihao.util.RequestPermissionUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.zhjl.qihao.util.RequestPermissionUtils.REQUEST_PERMISSION_SETTING;

public class UserAgreementActivity extends VolleyBaseActivity {
    @BindView(R.id.web_content)
    WebView webContent;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessage5;
    public static final int FILECHOOSER_RESULTCODE = 5173;
    public static final int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 5174;
    private static final int REQUEST_TAKE_PHOTO = 0x0; // 拍照
    public static final int CAMERA_CODE = 11112;    //相机
    public static final int READ_WHITE_SD = 11113;     //读写sd卡
    List<String> list = new ArrayList<String>();
    private WebChromeClient.FileChooserParams fileParams;
    private ValueCallback<Uri[]> callback;
    private Uri mOriginUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, getIntent().getStringExtra("name"), this);
        String webContent = getIntent().getStringExtra("webContent");
        WebSettings webSettings = this.webContent.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /**关闭webview中缓存**/
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSettings.setJavaScriptEnabled(true);
        //设置可以支持缩放
        webSettings.setSupportZoom(true);
        //设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBlockNetworkImage(false);
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true); //设置允许sd卡
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){//
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        this.webContent.loadUrl(webContent);
        this.webContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        this.webContent.setWebChromeClient(new MyWebViewClient());
    }


    private class MyWebViewClient extends WebChromeClient {

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            this.openFileChooser(uploadMsg, "*/*");
        }

        // For Android >= 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType) {
            this.openFileChooser(uploadMsg, acceptType, null);
        }

        // For Android >= 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            OpenUpLoadFolder();
        }

        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
//                if (mUploadMessage5 != null) {
//                    mUploadMessage5.onReceiveValue(null);
//                    mUploadMessage5 = null;
//                }
//                mUploadMessage5 = filePathCallback;
//                Intent intent = fileChooserParams.createIntent();
//                try {
//                    startActivityForResult(intent,
//                            FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
//                } catch (ActivityNotFoundException e) {
//                    mUploadMessage5 = null;
//                    return false;
//                }
            mUploadMessage5 = filePathCallback;
            fileParams = fileChooserParams;
            OpenUpLoadFolder();
            return true;
        }
    }

    /**
     * 拍照按钮事件
     */
    public void onPhoto() {
        if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                // 指定存放拍摄照片的位置
                File f = createImageFile();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {    //判断版本是否大于7.0
                    mOriginUri = FileProvider.getUriForFile(this.getApplication(), "com.zhjl.qihao.FileProvider", f);
                } else {
                    mOriginUri = Uri.fromFile(f);
                }
                takePictureIntent.putExtra("output", mOriginUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            RequestPermissionUtils.requestPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_CODE);
        }
    }

    /**
     * 把程序拍摄的照片放到 SD卡的 Pictures目录中 sheguantong 文件夹中
     * 照片的命名规则为：sheqing_20130125_173729.jpg
     *
     * @return
     * @throws IOException
     */
    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = format.format(new Date());
        String imageFileName = "activity_" + timeStamp + ".jpg";

        File image = new File(PictureUtil.getAlbumDir(), imageFileName);
        String mCurrentPhotoPath = image.getAbsolutePath();
        list.add(mCurrentPhotoPath);
        LogUtils.e("Photo", list.get(0));
        return image;
    }

    private void OpenUpLoadFolder() {
        final CharSequence[] items = {"拍照", "相册", "取消"};
        AlertDialog dlg = new AlertDialog.Builder(UserAgreementActivity.this)
                .setTitle("选择图片").setCancelable(false)
                .setItems(items, (dialog, item) -> {
                    // 这里item是根据选择的方式，
                    if (item == 0) {
                        onPhoto();
                    } else if (item == 1) {
                        if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                            createCameraIntent();
                        } else {
                            RequestPermissionUtils.requestPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_WHITE_SD);
                        }
                    } else if (item == 2) {
                        if (mUploadMessage5 != null) {
                            mUploadMessage5.onReceiveValue(null);
                            mUploadMessage5 = null;
                        }
                        if (mUploadMessage != null) {
                            mUploadMessage.onReceiveValue(null);
                            mUploadMessage = null;
                        }
                    }
                }).create();
        dlg.show();
    }

    private void createCameraIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (mUploadMessage5 != null) {
//                mUploadMessage5.onReceiveValue(null);
//                mUploadMessage5 = null;
//            }
//            mUploadMessage5 = callback;
            Intent intent = fileParams.createIntent();
            try {
                startActivityForResult(intent,
                        FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
            } catch (ActivityNotFoundException e) {
                mUploadMessage5 = null;
                return;
            }
        } else {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }
        return;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessage5) {
                return;
            }
            mUploadMessage5.onReceiveValue(WebChromeClient.FileChooserParams
                    .parseResult(resultCode, intent));
            mUploadMessage5 = null;
        } else if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                Toast.makeText(mContext, "本地相册权限已开启！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "设置本地相册权限失败！", Toast.LENGTH_SHORT).show();
            }
            if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                Toast.makeText(mContext, "拍照权限已开启！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "设置拍照权限失败！", Toast.LENGTH_SHORT).show();
            }
        } else {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    // 拍照返回结果
                    if (resultCode != Activity.RESULT_OK) {
                        if (mUploadMessage != null) {   //设置为null不然没反应
                            mUploadMessage.onReceiveValue(null);
                        }
                        if (mUploadMessage5 != null) {         // for android 5.0+
                            mUploadMessage5.onReceiveValue(null);
                        }
                        return;
                    } else {
                        Uri result = intent == null ?
                                mOriginUri : intent.getData();
                        if (mUploadMessage != null) {   //设置为null不然没反应
                            mUploadMessage.onReceiveValue(result);
                            mUploadMessage = null;
                        }
                        if (mUploadMessage5 != null) {         // for android 5.0+
                            if (result != null) {
                                mUploadMessage5.onReceiveValue(new Uri[]{result});
                            }else {
                                mUploadMessage5.onReceiveValue(null);
                            }
                            mUploadMessage5 = null;
                        }
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webContent != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webContent.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webContent);
            }

            webContent.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webContent.getSettings().setJavaScriptEnabled(false);
            webContent.clearHistory();
            webContent.clearView();
            webContent.removeAllViews();
            webContent.destroy();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CODE) {
            boolean isAllGranted = true;
            for (int i = 0; i < grantResults.length; i++) {
                //判断权限的结果，如果点击不在提示
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false;
                    if (permissions[i].equals(Manifest.permission.CAMERA)) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            RequestPermissionUtils.showShortCut(this, "开启相机需要相机权限！", findViewById(android.R.id.content).getRootView());
                        }
                    } else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            RequestPermissionUtils.showShortCut2(this, "图片存储需要存储空间权限！", findViewById(android.R.id.content).getRootView());
                        }
                    }
                }
            }
            if (isAllGranted) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    // 指定存放拍摄照片的位置
                    File f = createImageFile();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {    //判断版本是否大于7.0
                        mOriginUri = FileProvider.getUriForFile(this.getApplication(), "com.zhjl.qihao.FileProvider", f);
                    } else {
                        mOriginUri = Uri.fromFile(f);
                    }
                    takePictureIntent.putExtra("output", mOriginUri);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (!RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.CAMERA})) {
                    Toast.makeText(mContext, "开启相机需要相机权限！", Toast.LENGTH_SHORT).show();
                } else if (!RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                    Toast.makeText(mContext, "图片上传需要存储空间权限！", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == READ_WHITE_SD) {
            boolean isAllGranted = true;
            for (int i = 0; i < grantResults.length; i++) {

                //判断权限的结果，如果点击不在提示
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        RequestPermissionUtils.showShortCut(this, "选取照片需要读写SD卡权限！", findViewById(android.R.id.content).getRootView());
                    }
                }
            }
            if (isAllGranted) {
                createCameraIntent();
            } else {
                Toast.makeText(mContext, "读取照片需要存储空间权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
