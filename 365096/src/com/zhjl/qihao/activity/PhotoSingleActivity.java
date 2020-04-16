/**
 *
 */
package com.zhjl.qihao.activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.uploadimg.Utils;
import com.zhjl.qihao.util.PictureUtil;
import com.zhjl.qihao.util.RequestPermissionUtils;

import static com.zhjl.qihao.activity.PhotoMultipleActivity.CAMERA_CODE;
import static com.zhjl.qihao.activity.PhotoMultipleActivity.READ_WHITE_SD;
import static com.zhjl.qihao.util.RequestPermissionUtils.REQUEST_PERMISSION_SETTING;

/**
 * 相册或拍照
 * 单张上传拍照管理类
 */
public class PhotoSingleActivity extends VolleyBaseActivity {
    private static String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 0x0;    //拍照
    private static final int REQUEST_UPLOAD_PHOTO = 0x1;    //上传图片
    private static final int REQUEST_SELECT_LOCAL_PHOTO = 0x2;    //选择本地图片
    private static final int REQUEST_CUT_PHOTO = 0x3;    //剪切图片
    private static String imageId;    //图片id
    private LinearLayout llPopWindow;

    public static String getImageId() {
        return imageId;
    }

    public static void setImageId(String imageId) {
        PhotoSingleActivity.imageId = imageId;
    }

    public static String getSamllPath() {
        return samllPath;
    }

    public static void setSamllPath(String samllPath) {
        PhotoSingleActivity.samllPath = samllPath;
    }


    private static String samllPath;    //图片路径
    private Uri mPhotoUri;    //获取到的图片URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFlag(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main_photo);
        llPopWindow = findViewById(R.id.ll_popWindow);
    }

    /**
     * 拍照按钮事件
     */
    public void onPhotographClick(View arg0) {

        if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                // 指定存放拍摄照片的位置
                File f = createImageFile();
                Uri mOriginUri;
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
     * 选择本地相册
     */
    public void onLocalClick(View arg0) {

        if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(intent, REQUEST_SELECT_LOCAL_PHOTO);
        } else {
            RequestPermissionUtils.requestPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_WHITE_SD);
        }
    }

    public void onCancleClick(View arg0) {
        finish();
    }

    private static final int CODE_RESULT_CANCEL = -0x2;      //取消

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i("test", "requestCode:" + requestCode);
        if (resultCode == CODE_RESULT_CANCEL) {
            //返回结果异常
            finish();
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
                    //拍照返回结果
                    if (resultCode == Activity.RESULT_OK) {
                        startUploadActivity();
                    }
                    break;
                case REQUEST_SELECT_LOCAL_PHOTO://已选择图片,开启上传图片
                    //选择图片返回结果
                    if (intent != null && intent.getData() != null) {
                        mPhotoUri = intent.getData();
                        if (Build.VERSION.SDK_INT >= 24) {
                            mCurrentPhotoPath = getFilePathFromURI(this,mPhotoUri);
                        } else {
                            mCurrentPhotoPath = Utils.getRealPathFromUri(this,mPhotoUri);
                        }
                        startUploadActivity();
                    }
                    break;
                case REQUEST_CUT_PHOTO:
                    //没有调用剪切图片？？？
                    //剪切图片返回结果
                    startUploadActivity();
                    break;
                case REQUEST_UPLOAD_PHOTO:
                    //上传图片返回结果
                    if (intent != null && RESULT_OK == resultCode) {
                        imageId = intent.getStringExtra("imageId");
                        setImageId(imageId);
                        if (imageId != null) {
                            //上传图片成功，返回图片路径
                            samllPath = intent.getStringExtra("samllPath");
                            setSamllPath(samllPath);
                            Intent take = new Intent();
                            take.putExtra("imageId", imageId);
                            take.putExtra("samllPath", samllPath);
                            setResult(0x1, take);
                            finish();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private String getFilePathFromURI(Context context, Uri contentUri) {
        File rootDataDir = context.getFilesDir();
        String fileName = getFileName(contentUri);
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(rootDataDir + File.separator + fileName + ".jpg");
            copyFile(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    private String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return System.currentTimeMillis() + fileName;
    }
    private void copyFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int copyStream(InputStream input, OutputStream output) throws Exception, IOException {
        final int BUFFER_SIZE = 1024 * 2;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return count;
    }

    /**
     * 开启上传图片activity
     */
    private void startUploadActivity() {
        Intent take = new Intent(PhotoSingleActivity.this,
                PhotoSingleUploadActivity.class);
        take.putExtra(PhotoSingleUploadActivity.UPLOAD_PHOTO_PATH, mCurrentPhotoPath);
        startActivityForResult(take, REQUEST_UPLOAD_PHOTO);
        overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
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
                            RequestPermissionUtils.showShortCut(this, "开启相机需要相机权限！", llPopWindow);
                        }
                    } else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            RequestPermissionUtils.showShortCut2(this, "图片上传需要存储空间权限！", llPopWindow);
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
                    Uri mOriginUri;
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
                        RequestPermissionUtils.showShortCut(this, "读取照片需要存储空间权限！", llPopWindow);
                    }
                }
            }
            if (isAllGranted) {
                PhotoMultipleUploadActivity.getSDPath();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.GET_CONTENT");
                startActivityForResult(intent, REQUEST_SELECT_LOCAL_PHOTO);
            } else {
                Toast.makeText(mContext, "读取照片需要存储空间权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
