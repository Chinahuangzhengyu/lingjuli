package com.zhjl.qihao.util;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zhjl.qihao.ZHJLApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Administrator on 2016-05-03.
 */
public class ImageGenerationUtil {

    public static String code2 = "";



    public static Bitmap createQRImage(String url) throws  Exception{
        code2 = AEC.Encrypt(url, "italklingjuli206");
        return createQRImage(code2, 600, 600,null);
    }

    public static Bitmap createQRImage(String url,String picName) throws  Exception{
        code2 = AEC.Encrypt(url, "italklingjuli206");
        return createQRImage(code2, 600, 600,picName);
    }


    /**生成二维码*/
    public static Bitmap createQRImage(String url, final int width, final int height,String code2Name){
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
          //  hints.put(EncodeHintType.MARGIN, 0);  //边距
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);

            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                       pixels[y * width + x] = 0xffffffff;
                       // pixels[y * width + x] = 0xffffff80;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            File file = new File("/sdcard/zhjl");
            if (!file.exists())
                file.mkdir();

            file = new File("/sdcard/zhjl/"+(code2Name == null ? "temp": code2Name)+".jpg".trim());
            String fileName = file.getName();
            String mName = fileName.substring(0, fileName.lastIndexOf("."));
            String sName = fileName.substring(fileName.lastIndexOf("."));

            // /sdcard/myFolder/temp_cropped.jpg
            String newFilePath = "/sdcard/zhjl" + "/" + mName + "_cropped" + sName;
            file = new File(newFilePath);
            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(ZHJLApplication.getContext(), "分享二维码需要写入权限", Toast.LENGTH_SHORT).show();
            }


            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public static void saveCroppedImage(Bitmap bmp) {
//        File file = new File("/sdcard/myFolder");
//        if (!file.exists())
//            file.mkdir();
//
//        file = new File("/sdcard/temp.jpg".trim());
//        String fileName = file.getName();
//        String mName = fileName.substring(0, fileName.lastIndexOf("."));
//        String sName = fileName.substring(fileName.lastIndexOf("."));
//
//        // /sdcard/myFolder/temp_cropped.jpg
//        String newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped" + sName;
//        file = new File(newFilePath);
//        ToastUtil.make("`````````111");
//        try {
//            file.createNewFile();
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }


}
