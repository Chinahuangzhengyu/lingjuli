/**
 *
 */
package com.zhjl.qihao.activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.zhjl.qihao.util.PictureUtil;
import com.zhjl.qihao.util.ThreadPoolUtil;
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
 * 多张图片上传
 */
public class PhotoMultipleUploadActivity extends VolleyBaseActivity {
    private static final String TAG = "PhotoUploadActivity";
    public static final String UPLOAD_PHOTO_PATH = "UPLOAD_PHOTO_PATH";
    private List<File> mCurrentPhotoPath = new ArrayList<File>();
    private List<String> Path = new ArrayList<String>();
    List<Map<String, String>> mImageList = new ArrayList<Map<String, String>>();
    private String mImageId;
    private String mSamllPath;
    List<String> mImageIdList = new ArrayList<String>();
    List<String> mSamllPathList = new ArrayList<String>();
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private boolean isDestroy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFlag(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photo_view);
        isDestroy = false;
        Path = (List<String>) getIntent().getExtras().getSerializable(
                UPLOAD_PHOTO_PATH);
        mCurrentPhotoPath.clear();
        if (Path != null) {
            initAlertDialog();
            // 提示加载，12秒后提示加载失败
            mProgressDialog = ProgressDialog.show(
                    PhotoMultipleUploadActivity.this, "图片上传", "图片上传中..", true,
                    true);
            mProgressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // 设置RESULT_CANCELED
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
            ThreadPoolUtil.getInstant().execute(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        for (int i = 0; i < Path.size(); i++) {
                            copyFile(Path.get(i), getSDPath() + Path.get(i).substring(
                                    Path.get(i).lastIndexOf("/"), Path.get(i).length()));

                            mCurrentPhotoPath.add(new File(getSDPath()
                                    + Path.get(i).substring(Path.get(i).lastIndexOf("/"),
                                    Path.get(i).length())));

                            File f = mCurrentPhotoPath.get(i);
                            Bitmap bm = PictureUtil.getSmallBitmap(mCurrentPhotoPath.get(i).toString());
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
                            BufferedOutputStream bos = new BufferedOutputStream(
                                    new FileOutputStream(f));
                            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                            bos.flush();
                            bos.close();
                        }
                        upLoadPhotos();
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "图片不存在，请重新上传！", Toast.LENGTH_SHORT).show();
                                mProgressDialog.cancel();
                                return;
                            }
                        });
                    }
                }
            });

        } else {
            Toast.makeText(mContext, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = -1;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
//				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
//				FileOutputStream fs = new FileOutputStream(newPath);
                //要拷贝的图片
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(oldPath)));
                //目标的地址
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(newPath)));
                byte[] buffer = new byte[1024];
                while ((byteread = bis.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    bos.write(buffer, 0, byteread);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return File
     * @throws
     * @description 获取内存卡路径，设置图片的本地缓存路径
     * @version 1.0
     * @author 黄南榆
     * @date 2014年9月15日
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/zhjl");
            if (!sdDir.exists()) {
                boolean mkdir = sdDir.mkdir();
                Log.e("+++++++++++", "====" + mkdir);
            }
        }
        return sdDir;
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
                Intent aintent = new Intent(PhotoMultipleUploadActivity.this,
                        PhotoMultipleActivity.class);
                aintent.putExtra("imageId", (Serializable) mImageIdList);
                aintent.putExtra("samllPath", (Serializable) mSamllPathList);
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
                        ThreadPoolUtil.getInstant().execute(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                try {
                                    upLoadPhotos();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    mHandler.sendEmptyMessage(TAG_UPLOAD_FAILD);
                                }
                            }
                        });
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

    private void upLoadPhotos() {
        SettingInterface service = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < mCurrentPhotoPath.size(); i++) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), mCurrentPhotoPath.get(i));
            MultipartBody.Part picture = MultipartBody.Part.createFormData("fileArray", mCurrentPhotoPath.get(i).getName(), fileBody);
            parts.add(picture);
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "3.0");
        Call<ResponseBody> call = service.upLoadFiles(body, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        JSONObject object = new JSONObject(string);
                        JSONArray jsonArr = object.getJSONArray("upFileList");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            final Map<String, String> map = new HashMap<String, String>();
                            Iterator<String> it = jsonObj.keys();
                            while (it.hasNext()) {
                                String key = it.next();
                                String value = jsonObj.getString(key);
                                map.put(key, value);
                            }
                            mImageList.add(map);
                        }
                        for (int i = 0; i < mImageList.size(); i++) {
                            String resultString = mImageList.get(i).get("result");
                            if (resultString != null && "success_send".equals(resultString)) {
                                mImageIdList.add(mImageList.get(i).get("imageId"));
                                mSamllPathList.add(mImageList.get(i).get("samllPath"));
                            }
                        }
                        if (mSamllPathList.size() > 0) {
                            mHandler.sendEmptyMessage(TAG_UPLOAD_SUCCESS);
                            for (int i = 0; i < mCurrentPhotoPath
                                    .size(); i++) {
                                File f = new File(mCurrentPhotoPath
                                        .get(i).getAbsolutePath());
                                if (f.exists()) {
                                    f.delete();
                                }
                            }
                        } else {
                            mHandler.sendEmptyMessage(TAG_UPLOAD_FAILD);
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
    }
}
