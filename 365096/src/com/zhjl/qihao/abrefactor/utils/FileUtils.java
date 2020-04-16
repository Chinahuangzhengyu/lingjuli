package com.zhjl.qihao.abrefactor.utils;

import android.os.Environment;

import java.io.File;

public class FileUtils {
    //判断是否安装SDCard
    public static boolean isSdOk(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    //创建一个文件夹，用来存放下载的文件
    public static File getRootFile(){
        File sd = Environment.getExternalStorageDirectory();
        File rootFile = new File(sd,"qihao");
        if (!rootFile.exists()){
            rootFile.mkdirs();
        }
        return rootFile;
    }
}
