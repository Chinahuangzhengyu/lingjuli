package com.zhjl.qihao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zhjl.qihao.abrefactor.db.ShopListTwoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SHOP_LIST_TWO_BEAN".
*/
public class ShopListTwoBeanDao extends AbstractDao<ShopListTwoBean, String> {

    public static final String TABLENAME = "SHOP_LIST_TWO_BEAN";

    /**
     * Properties of entity ShopListTwoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Pic = new Property(1, String.class, "pic", false, "PIC");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Subhead = new Property(3, String.class, "subhead", false, "SUBHEAD");
        public final static Property Market_price = new Property(4, String.class, "market_price", false, "MARKET_PRICE");
        public final static Property Price = new Property(5, String.class, "price", false, "PRICE");
        public final static Property Clicks = new Property(6, String.class, "clicks", false, "CLICKS");
        public final static Property Sales = new Property(7, String.class, "sales", false, "SALES");
        public final static Property Limit_status = new Property(8, String.class, "limit_status", false, "LIMIT_STATUS");
        public final static Property Is_new = new Property(9, String.class, "is_new", false, "IS_NEW");
        public final static Property Create_time = new Property(10, String.class, "create_time", false, "CREATE_TIME");
        public final static Property Uptime = new Property(11, String.class, "uptime", false, "UPTIME");
        public final static Property Stock = new Property(12, String.class, "stock", false, "STOCK");
        public final static Property Arrive = new Property(13, String.class, "arrive", false, "ARRIVE");
        public final static Property Pro_type = new Property(14, String.class, "pro_type", false, "PRO_TYPE");
        public final static Property Group_end_time = new Property(15, String.class, "group_end_time", false, "GROUP_END_TIME");
        public final static Property Group_start_time = new Property(16, String.class, "group_start_time", false, "GROUP_START_TIME");
        public final static Property Group_price = new Property(17, String.class, "group_price", false, "GROUP_PRICE");
        public final static Property Url = new Property(18, String.class, "url", false, "URL");
        public final static Property Star = new Property(19, int.class, "star", false, "STAR");
    }


    public ShopListTwoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ShopListTwoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHOP_LIST_TWO_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"PIC\" TEXT," + // 1: pic
                "\"NAME\" TEXT," + // 2: name
                "\"SUBHEAD\" TEXT," + // 3: subhead
                "\"MARKET_PRICE\" TEXT," + // 4: market_price
                "\"PRICE\" TEXT," + // 5: price
                "\"CLICKS\" TEXT," + // 6: clicks
                "\"SALES\" TEXT," + // 7: sales
                "\"LIMIT_STATUS\" TEXT," + // 8: limit_status
                "\"IS_NEW\" TEXT," + // 9: is_new
                "\"CREATE_TIME\" TEXT," + // 10: create_time
                "\"UPTIME\" TEXT," + // 11: uptime
                "\"STOCK\" TEXT," + // 12: stock
                "\"ARRIVE\" TEXT," + // 13: arrive
                "\"PRO_TYPE\" TEXT," + // 14: pro_type
                "\"GROUP_END_TIME\" TEXT," + // 15: group_end_time
                "\"GROUP_START_TIME\" TEXT," + // 16: group_start_time
                "\"GROUP_PRICE\" TEXT," + // 17: group_price
                "\"URL\" TEXT," + // 18: url
                "\"STAR\" INTEGER NOT NULL );"); // 19: star
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHOP_LIST_TWO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShopListTwoBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(2, pic);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String subhead = entity.getSubhead();
        if (subhead != null) {
            stmt.bindString(4, subhead);
        }
 
        String market_price = entity.getMarket_price();
        if (market_price != null) {
            stmt.bindString(5, market_price);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(6, price);
        }
 
        String clicks = entity.getClicks();
        if (clicks != null) {
            stmt.bindString(7, clicks);
        }
 
        String sales = entity.getSales();
        if (sales != null) {
            stmt.bindString(8, sales);
        }
 
        String limit_status = entity.getLimit_status();
        if (limit_status != null) {
            stmt.bindString(9, limit_status);
        }
 
        String is_new = entity.getIs_new();
        if (is_new != null) {
            stmt.bindString(10, is_new);
        }
 
        String create_time = entity.getCreate_time();
        if (create_time != null) {
            stmt.bindString(11, create_time);
        }
 
        String uptime = entity.getUptime();
        if (uptime != null) {
            stmt.bindString(12, uptime);
        }
 
        String stock = entity.getStock();
        if (stock != null) {
            stmt.bindString(13, stock);
        }
 
        String arrive = entity.getArrive();
        if (arrive != null) {
            stmt.bindString(14, arrive);
        }
 
        String pro_type = entity.getPro_type();
        if (pro_type != null) {
            stmt.bindString(15, pro_type);
        }
 
        String group_end_time = entity.getGroup_end_time();
        if (group_end_time != null) {
            stmt.bindString(16, group_end_time);
        }
 
        String group_start_time = entity.getGroup_start_time();
        if (group_start_time != null) {
            stmt.bindString(17, group_start_time);
        }
 
        String group_price = entity.getGroup_price();
        if (group_price != null) {
            stmt.bindString(18, group_price);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(19, url);
        }
        stmt.bindLong(20, entity.getStar());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShopListTwoBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(2, pic);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String subhead = entity.getSubhead();
        if (subhead != null) {
            stmt.bindString(4, subhead);
        }
 
        String market_price = entity.getMarket_price();
        if (market_price != null) {
            stmt.bindString(5, market_price);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(6, price);
        }
 
        String clicks = entity.getClicks();
        if (clicks != null) {
            stmt.bindString(7, clicks);
        }
 
        String sales = entity.getSales();
        if (sales != null) {
            stmt.bindString(8, sales);
        }
 
        String limit_status = entity.getLimit_status();
        if (limit_status != null) {
            stmt.bindString(9, limit_status);
        }
 
        String is_new = entity.getIs_new();
        if (is_new != null) {
            stmt.bindString(10, is_new);
        }
 
        String create_time = entity.getCreate_time();
        if (create_time != null) {
            stmt.bindString(11, create_time);
        }
 
        String uptime = entity.getUptime();
        if (uptime != null) {
            stmt.bindString(12, uptime);
        }
 
        String stock = entity.getStock();
        if (stock != null) {
            stmt.bindString(13, stock);
        }
 
        String arrive = entity.getArrive();
        if (arrive != null) {
            stmt.bindString(14, arrive);
        }
 
        String pro_type = entity.getPro_type();
        if (pro_type != null) {
            stmt.bindString(15, pro_type);
        }
 
        String group_end_time = entity.getGroup_end_time();
        if (group_end_time != null) {
            stmt.bindString(16, group_end_time);
        }
 
        String group_start_time = entity.getGroup_start_time();
        if (group_start_time != null) {
            stmt.bindString(17, group_start_time);
        }
 
        String group_price = entity.getGroup_price();
        if (group_price != null) {
            stmt.bindString(18, group_price);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(19, url);
        }
        stmt.bindLong(20, entity.getStar());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ShopListTwoBean readEntity(Cursor cursor, int offset) {
        ShopListTwoBean entity = new ShopListTwoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // pic
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // subhead
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // market_price
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // price
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // clicks
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // sales
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // limit_status
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // is_new
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // create_time
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // uptime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // stock
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // arrive
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // pro_type
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // group_end_time
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // group_start_time
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // group_price
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // url
            cursor.getInt(offset + 19) // star
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ShopListTwoBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPic(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSubhead(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMarket_price(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPrice(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setClicks(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSales(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLimit_status(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIs_new(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCreate_time(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUptime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setStock(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setArrive(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setPro_type(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setGroup_end_time(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setGroup_start_time(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setGroup_price(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setUrl(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setStar(cursor.getInt(offset + 19));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ShopListTwoBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(ShopListTwoBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShopListTwoBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
