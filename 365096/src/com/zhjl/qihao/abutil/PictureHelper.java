package com.zhjl.qihao.abutil;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.view.GlideRoundTransform;

import java.io.File;

/**
 * @author 黄南榆
 * @version 1.0
 * @description 图片帮助类，负责加载图片
 * @date 2015年2月17日
 */
public class PictureHelper {
    private static RequestOptions options;
    private static RequestOptions noRoundOptions;

    public static RequestOptions getRequstOptions() {
        if (options == null) {
            options = new RequestOptions();
        }
        return options;
    }

    public static RequestOptions getRequstNoRoundOptions() {
        if (noRoundOptions == null) {
            noRoundOptions = new RequestOptions();
        }
        return noRoundOptions;
    }

    /**
     * @param iv
     * @return void
     * @throws
     * @description 正方形默认图的图片加载方法
     * @version 1.0
     */
    public static void showPictureWithSquare(Context context, ImageView iv, String uri) {
        Glide.with(context).load(uri).apply(PictureHelper.getRequstOptions().skipMemoryCache(false).placeholder(R.drawable.img_loading)).into(iv);
    }

    public static void setImageView(final Activity activity, Object url, ImageView iv, int res) {
        if (!activity.isFinishing()){
            Glide.with(activity).load(url).apply(PictureHelper.getRequstOptions().skipMemoryCache(false).placeholder(R.drawable.img_loading).error(R.drawable.img_loading)).into(iv);
        }
    }

    public static void setReplaceImageView(final Activity activity, Object url, ImageView iv) {
        String updateTime = String.valueOf(System.currentTimeMillis()); // 在需要重新获取更新的图片时调用
        Glide.with(activity).load(url).apply(PictureHelper.getRequstOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).signature(new ObjectKey(updateTime))).into(iv);

    }

    //不是圆角的
    public static void setImageView(final Context context, Object url, ImageView iv, int res) {
        Glide.with(context).load(url).apply(PictureHelper.getRequstNoRoundOptions().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).placeholder(R.drawable.img_loading).error(R.drawable.img_loading)).into(iv);

    }

    //头像
    public static void setHeadImageView(final Context context, Object url, ImageView iv, int res) {
        Glide.with(context).load(url).apply(PictureHelper.getRequstNoRoundOptions().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).placeholder(res).error(res)).into(iv);

    }

    public static void setPlaceholderImageView(final Context context, Object url, ImageView iv, int res) {
        Glide.with(context).load(url).apply(PictureHelper.getRequstNoRoundOptions().skipMemoryCache(false).placeholder(R.drawable.img_loading).error(R.drawable.img_loading)).into(iv);
    }

    public static void setOptionsGlide(final Context context, final int round, Object url, final ImageView iv, int res) {
//		.transform(new GlideRoundTransform(context,round))
        Glide.with(context).load(url).apply(PictureHelper.getRequstOptions().skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.img_loading).error(R.drawable.img_loading)).into(iv);
    }

    public static void setImageGlide(final Context context, final int round, Object url, final ImageView iv, int res) {
        Glide.with(context).load(url).apply(PictureHelper.getRequstOptions().skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.img_loading).error(res)).into(iv);
    }


    /**
     * @param iv
     * @param uri
     * @return void
     * @throws
     * @description 长方形默认图的图片加载方法
     * @version 1.0
     */

    public static void showPictureWithRectangular(Context context, ImageView iv, Object uri) {
        Glide.with(context).load(uri).apply(PictureHelper.getRequstOptions().placeholder(R.drawable.ic_head)).into(iv);
    }


    /**
     * @param iv
     * @param uri
     * @return void
     * @throws
     * @description 自定义默认图的图片加载方法
     * @version 1.0
     */

    public static void showPictureWithCustom(Context context, ImageView iv, String uri, int res) {
        Glide.with(context).load(uri).apply(PictureHelper.getRequstNoRoundOptions().placeholder(R.drawable.ic_head).error(res)).into(iv);
    }


    // 递归求取目录文件个数
    public static long getlist(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }

    /***
     * @description 获取内存卡路径，设置图片的本地缓存路径
     * @version 1.0
     * @return
     * @return File
     * @throws
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = new File(Constants.APPSDPATH + Constants.IMAGE_CACHE_DIR);
        }
        return sdDir;
    }

    /**
     * @return 内置SDCard剩余存储空间MB数
     */
    private static float getAvailableExternalMemorySize(String path) {
        StatFs stat = new StatFs(path);
        return calculateSizeInMB(stat);
    }

    /**
     * @param stat 文件StatFs对象
     * @return 剩余存储空间的MB数
     */
    private static float calculateSizeInMB(StatFs stat) {
        if (stat != null) {
            return stat.getAvailableBlocksLong()
                    * (stat.getBlockSizeLong() / (1024f * 1024f));
        }
        return 0.0f;
    }

    public static void clearMemoryCache() {
    }

    public static void clearDiskCache() {

    }
}
