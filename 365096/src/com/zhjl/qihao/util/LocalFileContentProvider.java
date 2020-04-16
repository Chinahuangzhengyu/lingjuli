/**
 * 
 */
package com.zhjl.qihao.util;

/**
 * @author south 2014-4-21
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class LocalFileContentProvider extends ContentProvider {
	private static final String URI_PREFIX = "content://com.zhjl.qihao.activity";

	@Override
	public ParcelFileDescriptor openFile(Uri uri, String mode)
			throws FileNotFoundException {
		Log.e("path1:", uri.getPath());
		File file = new File(uri.getPath());
		ParcelFileDescriptor parcel = ParcelFileDescriptor.open(file,
				ParcelFileDescriptor.MODE_READ_ONLY);
		return parcel;
	}

	@Override
	public AssetFileDescriptor openAssetFile(Uri uri, String mode)
			throws FileNotFoundException {
		AssetManager am = getContext().getAssets();
		String path = uri.getPath().substring(1);
		Log.e("path:", path);
		String tpath="";
		// C盘有没有
		tpath = "/data/data/com.zhjl.qihao/" + path;
		File file = new File(tpath);
		if (file.exists()) {
			Log.e("path2:", tpath);
			Uri turi = Uri.parse(URI_PREFIX + tpath);
			return super.openAssetFile(turi, mode);
		}

		try {
			AssetFileDescriptor afd = am.openFd(path);
			Log.i("AssetFileDescriptor", afd.toString());
			return afd;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.openAssetFile(uri, mode);
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public int delete(Uri uri, String s, String[] as) {
		throw new UnsupportedOperationException(
				"Not supported by this provider");
	}

	@Override
	public String getType(Uri uri) {
		throw new UnsupportedOperationException(
				"Not supported by this provider");
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentvalues) {
		throw new UnsupportedOperationException(
				"Not supported by this provider");
	}

	@Override
	public Cursor query(Uri uri, String[] as, String s, String[] as1, String s1) {
		throw new UnsupportedOperationException(
				"Not supported by this provider");
	}

	@Override
	public int update(Uri uri, ContentValues contentvalues, String s,
			String[] as) {
		throw new UnsupportedOperationException(
				"Not supported by this provider");
	}
}
