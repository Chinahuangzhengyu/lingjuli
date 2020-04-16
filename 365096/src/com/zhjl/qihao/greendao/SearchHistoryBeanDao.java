package com.zhjl.qihao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zhjl.qihao.freshshop.bean.SearchHistoryBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SEARCH_HISTORY_BEAN".
*/
public class SearchHistoryBeanDao extends AbstractDao<SearchHistoryBean, String> {

    public static final String TABLENAME = "SEARCH_HISTORY_BEAN";

    /**
     * Properties of entity SearchHistoryBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", true, "NAME");
    }


    public SearchHistoryBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SearchHistoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SEARCH_HISTORY_BEAN\" (" + //
                "\"NAME\" TEXT PRIMARY KEY NOT NULL );"); // 0: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SEARCH_HISTORY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SearchHistoryBean entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getName());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SearchHistoryBean entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getName());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    @Override
    public SearchHistoryBean readEntity(Cursor cursor, int offset) {
        SearchHistoryBean entity = new SearchHistoryBean( //
            cursor.getString(offset + 0) // name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SearchHistoryBean entity, int offset) {
        entity.setName(cursor.getString(offset + 0));
     }
    
    @Override
    protected final String updateKeyAfterInsert(SearchHistoryBean entity, long rowId) {
        return entity.getName();
    }
    
    @Override
    public String getKey(SearchHistoryBean entity) {
        if(entity != null) {
            return entity.getName();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SearchHistoryBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}