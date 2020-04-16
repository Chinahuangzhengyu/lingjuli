package com.zhjl.qihao.order.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.zhjl.qihao.BuildConfig;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.activity.PhotoMultipleActivity;
import com.zhjl.qihao.activity.PhotoMultipleUploadActivity;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.util.HttpUtil;
import com.zhjl.qihao.util.PictureUtil;
import com.zhjl.qihao.util.ThreadPoolUtil;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.util.FileUtils.copyFile;

public class PhpPhotoMultipleUploadActivity extends VolleyBaseActivity {

    public static final String UPLOAD_PHOTO_PATH = "UPLOAD_PHOTO_PATH";
    private List<String> path = new ArrayList<String>();
    private List<File> mCurrentPhotoPath = new ArrayList<File>();
    private List<String> mBase64PhotoPath = new ArrayList<String>();
    private List<String> mSmallPathList = new ArrayList<String>();
    private List<String> mPathList = new ArrayList<String>();
    private List<String> mPathIdList = new ArrayList<String>();
    private OrderApiInterface orderInterface;
    private String postKey = "";
    private String form = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photo_view);
        path = (List<String>) getIntent().getExtras().getSerializable(
                UPLOAD_PHOTO_PATH);
        postKey = getIntent().getStringExtra("postKey");
        form = getIntent().getStringExtra("form");
        mCurrentPhotoPath.clear();
        orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        if (path.size() > 0) {
            initAlertDialog();
            // 提示加载，12秒后提示加载失败
            mProgressDialog = ProgressDialog.show(
                    this, "图片上传", "图片上传中..", true,
                    true);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // 设置RESULT_CANCELED
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < path.size(); i++) {
                    copyFile(path.get(i), getSDPath() + path.get(i).substring(
                            path.get(i).lastIndexOf("/")));
                    File file = new File(getSDPath()
                            + path.get(i).substring(path.get(i).lastIndexOf("/")));
                    mCurrentPhotoPath.add(file);
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
                    BufferedOutputStream bos = null;
                    try {
                        bos = new BufferedOutputStream(
                                new FileOutputStream(f));
                        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                        bos.flush();
                        bos.close();
                        byte[] files = fileToByByteArray(f);
                        byte[] encode = Base64.encode(files, Base64.DEFAULT);
                        String string = new String(encode);
                        mBase64PhotoPath.add("data:image/jpeg;base64," + string + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Map<String, Object> map = new HashMap<>();
                map.put("post_key", postKey);
                map.put("user_token", mSession.getmToken());
                RequestBody body = ParamForNet.putContainsArray(map, "images", mBase64PhotoPath);
                Call<ResponseBody> call = null;
                if (form.equals("shopEvaluate")) {
                    call = orderInterface.uploadShopEvaluateImg(body);
                } else {
                    call = orderInterface.upLoadServiceImg(body);
                }
                activityRequestPhpData(call, new RequestResult<Object>() {
                    @Override
                    public void success(Object result, String message) throws Exception {
                        JSONObject object = new JSONObject((String) result);
                        boolean status = object.optBoolean("status");
                        if (status) {
                            JSONArray images = object.optJSONArray("images");
                            for (int j = 0; j < images.length(); j++) {
                                JSONObject imgList = images.getJSONObject(j);
                                String original = imgList.optString("original");
                                String min = imgList.optString("min");
                                mPathIdList.add(imgList.optInt("id") + "");
                                mPathList.add(original);
                                mSmallPathList.add(min);
                            }
                            // 上传成功
                            Intent intent = new Intent(PhpPhotoMultipleUploadActivity.this,
                                    PhpPhotoMultipleActivity.class);
                            intent.putExtra("smallPath", (Serializable) mSmallPathList);
                            intent.putExtra("path", (Serializable) mPathList);
                            intent.putExtra("id", (Serializable) mPathIdList);
                            // 设置RESULT_OK
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Utils.phpIsLogin(PhpPhotoMultipleUploadActivity.this, object.optInt("type"), object.optString("message"));
                        }
                    }

                    @Override
                    public void fail() {
                        Toast.makeText(mContext, "图片不存在，请重新上传！", Toast.LENGTH_SHORT).show();
                        mProgressDialog.cancel();
                        return;
                    }
                });


            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private byte[] fileToByByteArray(File file) {
        byte[] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            data = bos.toByteArray();

            fis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
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

    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/qihao");
            if (!sdDir.exists()) {
                boolean mkdir = sdDir.mkdir();
            }
        }
        return sdDir;
    }

    private void initAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("图片上传");
        alertDialog.setMessage("图片上传失败！");
        alertDialog.setPositiveButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mProgressDialog.show();
                Map<String, Object> map = new HashMap<>();
                map.put("post_key", postKey);
                map.put("user_token", mSession.getmToken());
                RequestBody body = ParamForNet.putContainsArray(map, "images", mBase64PhotoPath);
                Call<ResponseBody> call = null;
                if (form.equals("shopEvaluate")) {
                    call = orderInterface.uploadShopEvaluateImg(body);
                } else {
                    call = orderInterface.upLoadServiceImg(body);
                }
                activityRequestPhpData(call, new RequestResult<Object>() {
                    @Override
                    public void success(Object result, String message) throws Exception {
                        JSONObject object = new JSONObject((String) result);
                        boolean status = object.optBoolean("status");
                        if (status) {
                            JSONArray images = object.optJSONArray("images");
                            for (int j = 0; j < images.length(); j++) {
                                JSONObject imgList = images.getJSONObject(j);
                                String original = imgList.optString("original");
                                String min = imgList.optString("min");
                                mPathList.add(original);
                                mSmallPathList.add(min);
                                mPathIdList.add(imgList.optInt("id") + "");
                            }
                            // 上传成功
                            Intent intent = new Intent(PhpPhotoMultipleUploadActivity.this,
                                    PhpPhotoMultipleActivity.class);
                            intent.putExtra("smallPath", (Serializable) mSmallPathList);
                            intent.putExtra("path", (Serializable) mPathList);
                            intent.putExtra("id", (Serializable) mPathIdList);
                            // 设置RESULT_OK
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Utils.phpIsLogin(PhpPhotoMultipleUploadActivity.this, object.optInt("type"), object.optString("message"));
                        }
                    }

                    @Override
                    public void fail() {
                        Toast.makeText(mContext, "图片不存在，请重新上传！", Toast.LENGTH_SHORT).show();
                        mProgressDialog.cancel();
                        return;
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
}
