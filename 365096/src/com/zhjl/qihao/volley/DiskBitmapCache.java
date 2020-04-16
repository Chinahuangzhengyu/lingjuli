package com.zhjl.qihao.volley;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;
/***
 * @description SD卡缓存，缓存的不是真实的图片，而是编码后的文件
 * @version 1.0
 * @author 黄南榆
 * @date 2014-9-7
 */
public class DiskBitmapCache extends DiskBasedCache implements ImageCache {

	public DiskBitmapCache(File rootDirectory, int maxCacheSizeInBytes) {
		super(rootDirectory, maxCacheSizeInBytes);
	}
	public DiskBitmapCache(File cacheDir) {
		super(cacheDir);
	}

	@Override
	public Bitmap getBitmap(String url) {
		final Entry requestedItem = get(url);

		if (requestedItem == null)
			return null;

		return BitmapFactory.decodeByteArray(requestedItem.data, 0,
				requestedItem.data.length);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		final Entry entry = new Entry();
		entry.data = BitmapUtil.convertBitmapToBytes(bitmap);
		put(url, entry);
	}

}
