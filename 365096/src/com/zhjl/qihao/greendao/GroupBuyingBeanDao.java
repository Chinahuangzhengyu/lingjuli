package com.zhjl.qihao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zhjl.qihao.abrefactor.db.GroupBuyingBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GROUP_BUYING_BEAN".
*/
public class GroupBuyingBeanDao extends AbstractDao<GroupBuyingBean, Long> {

    public static final String TABLENAME = "GROUP_BUYING_BEAN";

    /**
     * Properties of entity GroupBuyingBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Url_name = new Property(1, String.class, "url_name", false, "URL_NAME");
        public final static Property Url = new Property(2, String.class, "url", false, "URL");
        public final static Property Logo = new Property(3, String.class, "logo", false, "LOGO");
        public final static Property Note = new Property(4, String.class, "note", false, "NOTE");
        public final static Property Service_cell = new Property(5, String.class, "service_cell", false, "SERVICE_CELL");
    }


    public GroupBuyingBeanDao(DaoConfig config) {
        super(config);
    }
    
    public GroupBuyingBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GROUP_BUYING_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"URL_NAME\" TEXT," + // 1: url_name
                "\"URL\" TEXT," + // 2: url
                "\"LOGO\" TEXT," + // 3: logo
                "\"NOTE\" TEXT," + // 4: note
                "\"SERVICE_CELL\" TEXT);"); // 5: service_cell
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GROUP_BUYING_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GroupBuyingBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url_name = entity.getUrl_name();
        if (url_name != null) {
            stmt.bindString(2, url_name);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
 
        String logo = entity.getLogo();
        if (logo != null) {
            stmt.bindString(4, logo);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(5, note);
        }
 
        String service_cell = entity.getService_cell();
        if (service_cell != null) {
            stmt.bindString(6, service_cell);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GroupBuyingBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url_name = entity.getUrl_name();
        if (url_name != null) {
            stmt.bindString(2, url_name);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
 
        String logo = entity.getLogo();
        if (logo != null) {
            stmt.bindString(4, logo);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(5, note);
        }
 
        String service_cell = entity.getService_cell();
        if (service_cell != null) {
            stmt.bindString(6, service_cell);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GroupBuyingBean readEntity(Cursor cursor, int offset) {
        GroupBuyingBean entity = new GroupBuyingBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // url
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // logo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // note
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // service_cell
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GroupBuyingBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUrl_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLogo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNote(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setService_cell(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GroupBuyingBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GroupBuyingBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GroupBuyingBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
