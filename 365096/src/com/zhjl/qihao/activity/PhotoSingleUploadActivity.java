/**
 *
 */
package com.zhjl.qihao.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.zhjl.qihao.BuildConfig;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.systemsetting.api.SettingInterface;
import com.zhjl.qihao.util.HttpUtil;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.util.PictureUtil;
import com.zhjl.qihao.util.UrlChangeUtils;
import com.zhjl.qihao.zq.ApiHelper;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

/**
 * 单张图片上传
 */
public class PhotoSingleUploadActivity extends VolleyBaseActivity {
    private static final String TAG = "PhotoUploadActivity";
    public static final String UPLOAD_PHOTO_PATH = "UPLOAD_PHOTO_PATH";
    private String mCurrentPhotoPath;
    private String mImageId;
    private String mSamllPath;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private boolean isDestroy;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFlag(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photo_view);
        isDestroy = false;
        Intent intent = getIntent();
        mCurrentPhotoPath = intent.getStringExtra(UPLOAD_PHOTO_PATH);
        Log.e(TAG, "initAlertDialog");
        if (mCurrentPhotoPath != null) {
            initAlertDialog();
            // 提示加载，12秒后提示加载失败
            mProgressDialog = ProgressDialog.show(mContext, "图片上传", "图片上传中..", true, true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // 设置RESULT_CANCELED
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
            try {
                copyFile(mCurrentPhotoPath, getSDPath() + mCurrentPhotoPath.substring(
                        mCurrentPhotoPath.lastIndexOf("/"),
                        mCurrentPhotoPath.length()));
                mCurrentPhotoPath = getSDPath() + mCurrentPhotoPath.substring(
                        mCurrentPhotoPath.lastIndexOf("/"),
                        mCurrentPhotoPath.length());
                File f = new File(mCurrentPhotoPath);
                Bitmap bm = PictureUtil.getSmallBitmap(mCurrentPhotoPath);
                if (bm == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "图片已损坏！", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                            mAlertDialog.dismiss();
                            finish();
                        }
                    });
                    return;
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                UploadThread uploadThread = new UploadThread(mCurrentPhotoPath);
                uploadThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    /**
     * 复制单个文件
     *
     * @param oldPath
     *            String 原文件路径 如：c:/fqf.txt
     * @param newPath
     *            String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @description 获取内存卡路径，设置图片的本地缓存路径
     * @version 1.0
     * @author 黄南榆
     * @date 2014年9月15日 下午4:20:48
     * @return
     * @return File
     * @throws
     */
    private static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/zhjl/");
        }
        return sdDir;
    }

    /**
     * 上传图片线程
     */
    class UploadThread extends Thread {
        private String mFileName = "";

        public UploadThread(String fileName) {
            this.mFileName = fileName;
        }

        @Override
        public void run() {
//            boolean success = false;
            try {
//                String url = "";
                SettingInterface service = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
                File file = new File(mFileName);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part picture = MultipartBody.Part.createFormData("uploadify", file.getName(), fileBody);
                RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "3.0");
                Call<ResponseBody> call = service.upLoadFile(body,picture);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String string = response.body().string();
                                JSONObject object = new JSONObject(string);
                                String resultString = object.optString("result");
                                String imageId = object.optString("imageId");
                                String samllPath = object.optString("samllPath");
                                object.optString("samllHeadPicPath");
                                if (resultString != null && "success_send".equals(resultString)) {
                                    mImageId = imageId;
                                    mSamllPath = samllPath;
                                    mHandler.sendEmptyMessage(TAG_UPLOAD_SUCCESS);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                mHandler.sendEmptyMessage(TAG_UPLOAD_FAILD);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mHandler.sendEmptyMessage(TAG_UPLOAD_FAILD);
                    }
                });
//                if (!mSession.isTest()) {
//				 url = "https://psms.qihaolingjuli.com"
//                         +  Constants.UPLOAD_SINGLE_FILE_URL;
//                }else {
//                    url = "https://tj.qihaolingjuli.com"
//                            + Constants.UPLOAD_SINGLE_FILE_URL;
//                }
//                HttpUtil httpUtil = new HttpUtil(url, 60000); // 5秒超时
//                String result = httpUtil.executePost(new File(mFileName));
//                LogUtils.d("upload pic result is " + result);
//                if (result != null && !"".equals(result)) {
//                    JSONObject js;
//                    js = new JSONObject(result);
//                    String resultString = js.getString("result");
//                    String resultimageId = js.getString("imageId");
//                    String resultsamllPath = js.getString("samllPath");
//                    // String resultsamllHeadPicPath =
//                    // js.getString("samllHeadPicPath");
//                    if (resultString != null
//                            && "success_send".equals(resultString)) {
//                        mImageId = resultimageId;
//                        mSamllPath = resultsamllPath;
//                        success = true;
//                    }
//                }
            } catch (Exception e) {
                LogUtils.d("upload pic result is exception ");
                e.printStackTrace();

            }
//            LogUtils.d("upload pic finish ");
//            if (success) {
//                mHandler.sendEmptyMessage(TAG_UPLOAD_SUCCESS);
//                File f = new File(mFileName);
//                if (f.exists()) {
//                    //      f.delete();
//                }
//            } else {
//                mHandler.sendEmptyMessage(TAG_UPLOAD_FAILD);
//            }
        }
    }

    private static final int TAG_UPLOAD_SUCCESS = 0x01;
    private static final int TAG_UPLOAD_FAILD = 0x02;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "isDestroy  " + isDestroy);
            if (isDestroy) {
                return;
            }
            mProgressDialog.dismiss();
            if (msg.what == TAG_UPLOAD_SUCCESS) {
                // 上传成功
                Intent aintent = new Intent(PhotoSingleUploadActivity.this,
                        PhotoSingleActivity.class);
                aintent.putExtra("imageId", mImageId);
                aintent.putExtra("samllPath", mSamllPath);
                // 设置RESULT_OK
                setResult(RESULT_OK, aintent);
                finish();
            } else if (msg.what == TAG_UPLOAD_FAILD) {
                showUploadFaildDialog();
            }
        }
    };

    /**
     * 上传图片失败
     */
    private void showUploadFaildDialog() {
        mAlertDialog.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 屏蔽返回键
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

    private void initAlertDialog() {
        Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("图片上传");
        alertDialog.setMessage("图片上传失败！");
        alertDialog.setPositiveButton("重试",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProgressDialog.show();
                        UploadThread t = new UploadThread(mCurrentPhotoPath);
                        t.start();
                    }
                });
        alertDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setCancelable(false);
        mAlertDialog = alertDialog.create();
    }
}
