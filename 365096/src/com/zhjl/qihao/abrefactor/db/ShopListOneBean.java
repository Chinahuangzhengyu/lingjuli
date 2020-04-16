package com.zhjl.qihao.abrefactor.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 首页商品1
 */
@Entity
public class ShopListOneBean implements Parcelable{
    @Id
    private String id;
    private String pic;
    private String name;
    private String subhead;
    private String market_price;
    private String price;
    private String clicks;
    private String sales;
    private String limit_status;
    private String is_new;
    private String create_time;
    private String uptime;
    private String stock;
    private String arrive;
    private String pro_type;
    private String group_end_time;
    private String group_start_time;
    private String group_price;
    private String url;
    private int star;
    @Generated(hash = 892048849)
    public ShopListOneBean(String id, String pic, String name, String subhead,
            String market_price, String price, String clicks, String sales,
            String limit_status, String is_new, String create_time, String uptime,
            String stock, String arrive, String pro_type, String group_end_time,
            String group_start_time, String group_price, String url, int star) {
        this.id = id;
        this.pic = pic;
        this.name = name;
        this.subhead = subhead;
        this.market_price = market_price;
        this.price = price;
        this.clicks = clicks;
        this.sales = sales;
        this.limit_status = limit_status;
        this.is_new = is_new;
        this.create_time = create_time;
        this.uptime = uptime;
        this.stock = stock;
        this.arrive = arrive;
        this.pro_type = pro_type;
        this.group_end_time = group_end_time;
        this.group_start_time = group_start_time;
        this.group_price = group_price;
        this.url = url;
        this.star = star;
    }
    @Generated(hash = 1846574382)
    public ShopListOneBean() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSubhead() {
        return this.subhead;
    }
    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }
    public String getMarket_price() {
        return this.market_price;
    }
    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getClicks() {
        return this.clicks;
    }
    public void setClicks(String clicks) {
        this.clicks = clicks;
    }
    public String getSales() {
        return this.sales;
    }
    public void setSales(String sales) {
        this.sales = sales;
    }
    public String getLimit_status() {
        return this.limit_status;
    }
    public void setLimit_status(String limit_status) {
        this.limit_status = limit_status;
    }
    public String getIs_new() {
        return this.is_new;
    }
    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }
    public String getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getUptime() {
        return this.uptime;
    }
    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
    public String getStock() {
        return this.stock;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }
    public String getArrive() {
        return this.arrive;
    }
    public void setArrive(String arrive) {
        this.arrive = arrive;
    }
    public String getPro_type() {
        return this.pro_type;
    }
    public void setPro_type(String pro_type) {
        this.pro_type = pro_type;
    }
    public String getGroup_end_time() {
        return this.group_end_time;
    }
    public void setGroup_end_time(String group_end_time) {
        this.group_end_time = group_end_time;
    }
    public String getGroup_start_time() {
        return this.group_start_time;
    }
    public void setGroup_start_time(String group_start_time) {
        this.group_start_time = group_start_time;
    }
    public String getGroup_price() {
        return this.group_price;
    }
    public void setGroup_price(String group_price) {
        this.group_price = group_price;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getStar() {
        return this.star;
    }
    public void setStar(int star) {
        this.star = star;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pic);
        dest.writeString(this.name);
        dest.writeString(this.subhead);
        dest.writeString(this.market_price);
        dest.writeString(this.price);
        dest.writeString(this.clicks);
        dest.writeString(this.sales);
        dest.writeString(this.limit_status);
        dest.writeString(this.is_new);
        dest.writeString(this.create_time);
        dest.writeString(this.uptime);
        dest.writeString(this.stock);
        dest.writeString(this.arrive);
        dest.writeString(this.pro_type);
        dest.writeString(this.group_end_time);
        dest.writeString(this.group_start_time);
        dest.writeString(this.group_price);
        dest.writeString(this.url);
        dest.writeInt(this.star);
    }

    protected ShopListOneBean(Parcel in) {
        this.id = in.readString();
        this.pic = in.readString();
        this.name = in.readString();
        this.subhead = in.readString();
        this.market_price = in.readString();
        this.price = in.readString();
        this.clicks = in.readString();
        this.sales = in.readString();
        this.limit_status = in.readString();
        this.is_new = in.readString();
        this.create_time = in.readString();
        this.uptime = in.readString();
        this.stock = in.readString();
        this.arrive = in.readString();
        this.pro_type = in.readString();
        this.group_end_time = in.readString();
        this.group_start_time = in.readString();
        this.group_price = in.readString();
        this.url = in.readString();
        this.star = in.readInt();
    }

    public static final Creator<ShopListOneBean> CREATOR = new Creator<ShopListOneBean>() {
        @Override
        public ShopListOneBean createFromParcel(Parcel source) {
            return new ShopListOneBean(source);
        }

        @Override
        public ShopListOneBean[] newArray(int size) {
            return new ShopListOneBean[size];
        }
    };
}
