package com.zhjl.qihao.database;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zhjl.qihao.entity.CostType;
import com.zhjl.qihao.entity.RecMessage;
import com.zhjl.qihao.entity.ServiceTypeBean;
import com.zhjl.qihao.Constants;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    
	public DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
}
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try{
				Log.i(DatabaseHelper.class.getName(), "开始创建数据库");
				TableUtils.createTable(connectionSource,RecMessage.class);
				TableUtils.createTable(connectionSource,ServiceTypeBean.class);
				TableUtils.createTable(connectionSource,CostType.class);
				Log.e(DatabaseHelper.class.getName(), "创建数据库成功");
			}catch(SQLException e){
				Log.e(DatabaseHelper.class.getName(), "创建数据库失败", e);
			}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2,
			int arg3) {
		try{
			TableUtils.dropTable(connectionSource, RecMessage.class, true);
			TableUtils.dropTable(connectionSource, ServiceTypeBean.class, true);
			TableUtils.dropTable(connectionSource, CostType.class, true);
			onCreate(db, connectionSource);
			Log.e(DatabaseHelper.class.getName(), "更新数据库成功");
		}catch(SQLException e){
			Log.e(DatabaseHelper.class.getName(),"更新数据库失败",e);
		}
		
	}

	@Override
	public void close() {
		
		super.close();
	}

	public Dao<RecMessage, String> getRecImessageDao(){
	try {
		return DaoManager.createDao(connectionSource, RecMessage.class);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
		}
	}
	public Dao<ServiceTypeBean, String> getServiceTypeBeanDao(){
		try {
			return DaoManager.createDao(connectionSource, ServiceTypeBean.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public Dao<CostType, String> getCostTypeDao(){
		try {
			return DaoManager.createDao(connectionSource, CostType.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
    
}
