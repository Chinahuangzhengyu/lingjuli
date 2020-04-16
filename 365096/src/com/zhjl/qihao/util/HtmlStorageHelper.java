package com.zhjl.qihao.util;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
public class HtmlStorageHelper {
 private AQuery aq;
 private String mDownloadPath;
 private URL mainUrl;
 public HtmlStorageHelper(Context context,String mainPath) {
  aq = new AQuery(context);
  File dir_file = new File(mainPath +"/");
  mDownloadPath=mainPath +"/";
  if(!dir_file.exists())
   dir_file.mkdir();
 }

 public void saveHtml(final String id, final String title,String URL) {
  if(isHtmlSaved(id))
   return;
  try {
	mainUrl=new URL(URL);
  } catch (MalformedURLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	return ;
  }
  aq.ajax(URL, String.class, new AjaxCallback<String>() {
   @Override
   public void callback(String url, String html, AjaxStatus status) {
    File dir_file = new File(mDownloadPath + id+"/");
    if(!dir_file.exists())
     dir_file.mkdir();

    Pattern pattern = Pattern.compile("(?<=src=\")[^\"]+(?=\")");
    Matcher matcher = pattern.matcher(html);
    StringBuffer sb = new StringBuffer(); 
    while(matcher.find()){
     downloadPic(id, matcher.group(0));
     matcher.appendReplacement(sb, formatPath(matcher.group(0))); 
    }
    matcher.appendTail(sb);
    html = sb.toString();

    writeHtml(id, title, html);
   }
  });
 }

 private void downloadPic(String id, String url) {
  File pic_file = new File(mDownloadPath + id + "/" + formatPath(url));
  try {
	  URL suburl = new URL(url);
  	} catch (MalformedURLException e) {
  		e.printStackTrace();
  		url=mainUrl.getProtocol()+"://"+mainUrl.getHost()+":"+mainUrl.getPort()+url;
  		Log.e("InformationService", "newurl="+url);
  	} 
  aq.download(url, pic_file, new AjaxCallback<File>() {
   @Override
   public void callback(String url, final File file, AjaxStatus status) {
   }
  });
 }

 private void writeHtml(String id, String title, String html) {
  File html_file = new File(mDownloadPath + id + "/index.html");
  FileOutputStream fos = null;
  try {
   fos=new FileOutputStream(html_file);
            fos.write(html.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

 }

 public boolean isHtmlSaved(String id) {
  File file = new File(mDownloadPath + id);
  if(file.exists()) {
   file = new File(mDownloadPath + id + "/index.html");
   if(file.exists())
    return true;
  }
  deleteHtml(id);
  return false;
 }

 public String getTitle(String id) {
   return null;
 }


 public void deleteHtml(String id) {
  File dir_file = new File(mDownloadPath + id);
  deleteFile(dir_file);
 }
 private void deleteFile(File file) {
  if (file.exists()) { // 判断文件是否存在
   if (file.isFile()) { // 判断是否是文件
    file.delete(); // delete()方法 你应该知道 是删除的意思;
   } else if (file.isDirectory()) { // 否则如果它是一个目录
    File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
     this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
    }
   }
   file.delete();
  } else {
   //
  }
 }

 private String formatPath(String path) {
        if (path != null && path.length() > 0) {
            path = path.replace("\\", "_");
            path = path.replace("/", "_");
            path = path.replace(":", "_");
            path = path.replace("*", "_");
            path = path.replace("?", "_");
            path = path.replace("\"", "_");
            path = path.replace("<", "_");
            path = path.replace("|", "_");
            path = path.replace(">", "_");
        }
        return path;
    }
}