package com.zhjl.qihao.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.database.DatabaseHelper;
import com.zhjl.qihao.entity.CostType;
import com.zhjl.qihao.entity.ServiceTypeBean;
/***
 * SharedPreferences 工具类
 * @author south
 *
 */
public class Tools {

	public SharedPreferences sp;
	public SharedPreferences.Editor editor;

	Context context;
	private static Tools mInstance;
	
	public static Tools getInstance(Context context){
		context = ZHJLApplication.getContext();
		if(mInstance == null){
			mInstance = new Tools(context, Constants.NEARBYSETTING);
		}
		return mInstance;
	}

	public Tools(Context c, String name) {
		context = c;
		sp = context.getSharedPreferences(name, 0);
		editor = sp.edit();
	}

	public void putValue(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void putValue(String key, int value) {
		editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public void putValue(String key, boolean value) {
		editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void putValue(String key,float value){
		editor = sp.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public String getValue(String key) {
		return sp.getString(key, null);
	}

	public String getValue(String key,String defaulValuet) {
		return sp.getString(key, defaulValuet);
	}

	public int getValue_int(String key) {
		return sp.getInt(key, -1);
	}

	public int getValue_int(String key,int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public float getValue_float(String key,float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	public boolean getValue_boolean(String key,boolean defaultValue){
		return sp.getBoolean(key, defaultValue);
	}


	public void removeValue(String key) {
		editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 去除重复路线
	 * 
	 * @param busesString
	 * @return
	 */
	public static String duplicateBuses(String busesString) {
		List<String> buseslist = new ArrayList<String>();
		for (String bus : busesString.split(";")) {
			if (!buseslist.contains(bus)) {
				buseslist.add(bus);
			}
		}
		StringBuffer sBusesList = new StringBuffer("");
		if (buseslist.size() != 0) {
			for (String bus : buseslist) {
				sBusesList.append(bus + ";");
			}
		}
		return sBusesList.toString();
	}

	public static void decodeType(String json, DatabaseHelper databaseHelper)
			throws JSONException {
		List<ServiceTypeBean> servicelist = new ArrayList<ServiceTypeBean>();
		List<CostType> costlist = new ArrayList<CostType>();
		JSONObject oj = new JSONObject(json);
		if (oj.getString("result") != null
				&& "success_send".equals(oj.getString("result"))) {
			JSONArray list = oj.getJSONArray("serviceTypeInfo");
			int listsize = list.length();
			for (int i = 0; i < listsize; i++) {
				JSONObject serviceType = (JSONObject) list.get(i);
				ServiceTypeBean stb = new ServiceTypeBean();
				if (serviceType.getString("serviceCode") != null)
					stb.setServiceCode(serviceType.getString("serviceCode"));
				if (serviceType.getString("serviceId") != null)
					stb.setServiceId(serviceType.getString("serviceId"));
				if (serviceType.getString("serviceName") != null)
					stb.setServiceName(serviceType.getString("serviceName"));
				if (serviceType.getString("serviceType") != null)
					stb.setServiceType(serviceType.getString("serviceType"));
				if (serviceType.getString("smallCode") != null)
					stb.setSmallCode(serviceType.getString("smallCode"));
				if (serviceType.get("costType") != null) {
					JSONArray costTypelist = (JSONArray) serviceType
							.get("costType");
					int costTypelistsize = costTypelist.length();
					for (int k = 0; k < costTypelistsize; k++) {
						JSONObject costType = (JSONObject) costTypelist.get(k);
						CostType ct = new CostType();
						ct.setServiceId_fk(stb.getServiceId());
						if (costType.getString("costId") != null)
							ct.setCostId(costType.getString("costId"));
						if (costType.getString("costName") != null)
							ct.setCostName(costType.getString("costName"));
						if (costType.getString("costStandard") != null)
							ct.setCostStandard(costType
									.getString("costStandard"));
						costlist.add(ct);
					}
				}
				servicelist.add(stb);
			}
		}
		Dao<ServiceTypeBean, String> serviceBanDao = databaseHelper
				.getServiceTypeBeanDao();
		Dao<CostType, String> costTypeDao = databaseHelper.getCostTypeDao();
		DatabaseConnection dbconnect = null;
		try {
			dbconnect = databaseHelper.getConnectionSource()
					.getReadWriteConnection();
			serviceBanDao.setAutoCommit(dbconnect, false);
			costTypeDao.setAutoCommit(dbconnect, false);
			for (ServiceTypeBean serviceTypeBean : servicelist) {
				serviceBanDao.createOrUpdate(serviceTypeBean);
			}
			for (CostType costType : costlist) {
				costTypeDao.createOrUpdate(costType);

			}
			try {
				serviceBanDao.commit(dbconnect);
				costTypeDao.commit(dbconnect);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("tools", e.toString());
		}

	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 下载文件,未使用
	 * @param fileUrl  下载的Url
	 * @param savePath  保存的路径
	 * @param saveName  下载文件名
	 * @return
	 */
	public static boolean saveUrlAs(String fileUrl, String savePath,
			String saveName)/* fileUrl网络资源地址 */
	{

		try {
			URL url = new URL(fileUrl);/* 将网络资源地址传给,即赋值给url */
			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			DataInputStream in = new DataInputStream(
					connection.getInputStream());
			/* 此处也可用BufferedInputStream与BufferedOutputStream */
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					savePath + saveName));
			/* 将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址 */
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */
			{
				out.write(buffer, 0, count);
			}
			out.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
			in.close();
			connection.disconnect();
			return true;/* 网络资源结束并存储本地成功返回true */

		} catch (Exception e) {
			return false;
		}
	}
}
