package com.zhjl.qihao.volley;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
/**
 * @description 
 * @version 1.0
 * @author 黄南榆
 * @date 2014-9-7
 */
public class BitmapUtil {
	 

	public static Bitmap downSizeBitmap(Bitmap bitmap,int reqSize)  {
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		 
		float scaleWidth = ((float) reqSize) / width;
		float scaleHeight = ((float) reqSize) / height;
		 
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		 
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
		return resizedBitmap;
		 
		/*if(bitmap.getWidth() < reqSize) {
			return bitmap;
		} else {
			return Bitmap.createScaledBitmap(bitmap, reqSize, reqSize, false);
		} */
	}
	
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static byte[] convertBitmapToBytes(Bitmap bitmap) {
    	ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
      try {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				
		        bitmap.copyPixelsToBuffer(buffer);
		        
		        return buffer.array();
		        
		  } else {
			  	ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			  	bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		        byte[] data = baos.toByteArray();
		        return data;
		  }
	} catch (OutOfMemoryError e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
		//bitmap.recycle();
		System.gc();
		return null;
	}finally{
		closeInputStream(buffer);
		
	}
    }
    private static void closeInputStream(ByteBuffer buffer) {  
    	        if (null != buffer) {  
    	            buffer.clear();  
    	        }    
    }
    }
