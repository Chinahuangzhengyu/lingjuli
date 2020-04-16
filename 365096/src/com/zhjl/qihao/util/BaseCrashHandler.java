package com.zhjl.qihao.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.zhjl.qihao.Constants;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;

/**
 * 程序异常处理
 * 
 * @author south
 * 
 */
public class BaseCrashHandler implements UncaughtExceptionHandler {
	private static BaseCrashHandler mHandAcceptCrashHandler = null;
	private Context mContext;
	private Thread.UncaughtExceptionHandler defaultHandler = Thread
			.getDefaultUncaughtExceptionHandler();
	/**
	 * 是否按照默认方式处理异常 false 表示按照自己的方式处�?true 表示按照系统的处理方�?
	 */
	private static final boolean IS_DEFAULT_ERROR = true;

	private BaseCrashHandler(Context context) {
		this.mContext = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public static BaseCrashHandler getInstance(Context context) {
		if (null == mHandAcceptCrashHandler) {
			mHandAcceptCrashHandler = new BaseCrashHandler(context);
		}
		return mHandAcceptCrashHandler;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		/**
		 * 代码发布前，将所有出现异常的情况抛出 减少正式发布的时候异常出现的次数
		 */
		handleUserDefinedException(ex);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 否则按照系统默认的处理进行处�?
		defaultHandler.uncaughtException(thread, ex);

	}

	/**
	 * 自定义错误处�?收集错误信息 发�?错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private void handleUserDefinedException(final Throwable ex) {
		if (ex == null) {
			return;
		}
		ex.printStackTrace();
		ZHJLApplication.getInstance().finishAll();

		// Util.notCloseExit(mContext);
		// Runnable mRunnable = new Runnable() {
		//
		// @Override
		// public void run() {
		// // 保存程序奔溃信息
		// saveCrashInfoToFile(ex);
		// }
		// };
		// ThreadPoolUtil.getInstant().execute(mRunnable);
	}
	
	/**
	 * 判断是否存在SD卡
	 */
	public static boolean isExistSDCard()
	{
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	/**
	 * 获取SD卡剩余空间
	 * 
	 * @return
	 */
	public static long getSDFreeSize()
	{
		// 取得SD卡文件路�?
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数�?
		long freeBlocks = sf.getAvailableBlocks();
		return (freeBlocks * blockSize); // 单位MB
	}

	/**
	 * 日志文件大小
	 */
	private static final int LOG_MAX_SIZE = 1024 * 1024;// 1M/**
	/**
	 * 日志文件夹名称
	 */
	public static final String LOG__DIRECTORY = "logDirectory";
	/**
	 * 日志名称
	 */
	public static final String LOG_NAME = "error_log.txt";
	/**
	 * 项目相关文件
	 */
	
	
	/**
	 * 返回日志文件目录
	 * 
	 * @return
	 */
	public static String getLogDirPath()
	{
		return Constants.APPSDPATH + File.separator + LOG__DIRECTORY;
	}
	
	/**
	 * 将异常信息保存到SD卡文本中
	 * 
	 * @param ex
	 * @return
	 */
	public static void saveCrashInfoToFile(Throwable ex)
	{
		if (!isExistSDCard())
		{// 如果没有sdcard，则不存�?
			return;
		}
		if (getSDFreeSize() < LOG_MAX_SIZE)
		{
			return;
		}
		Writer writer = null;
		PrintWriter printWriter = null;
		String stackTrace = "";
		try
		{
			writer = new StringWriter();
			printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			Throwable cause = ex.getCause();
			while (cause != null)
			{
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			stackTrace = writer.toString();
		} catch (Exception e)
		{
		} finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (printWriter != null)
			{
				printWriter.close();
			}
		}
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String timestamp = sdf.format(date);
		sb.append("=====error log start====\n");
		sb.append(System.getProperty("line.separator"));
		sb.append(timestamp);
		sb.append(System.getProperty("line.separator"));
		sb.append(stackTrace);
		sb.append(System.getProperty("line.separator"));
		sb.append("=====error log end======\n");
		sb.append(System.getProperty("line.separator"));
		BufferedWriter mBufferedWriter = null;
		try
		{
			String path = getLogDirPath() + File.separator + LOG_NAME;
			File mFile = new File(path);
			File pFile = mFile.getParentFile();
			if (!pFile.exists())
			{
				pFile.mkdirs();
			}
			if (mFile.length() > LOG_MAX_SIZE)
			{
				mFile.delete();
				mFile.createNewFile();
			}
			mBufferedWriter = new BufferedWriter(new FileWriter(mFile, true));
			mBufferedWriter.append(sb.toString());
			mBufferedWriter.flush();
			mBufferedWriter.close();
		} catch (IOException e)
		{
		} finally
		{
			if (mBufferedWriter != null)
			{
				try
				{
					mBufferedWriter.close();
				} catch (IOException e)
				{
				}
			}
		}
	}
}
