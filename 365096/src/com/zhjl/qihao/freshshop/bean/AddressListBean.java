package com.zhjl.qihao.freshshop.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressListBean implements Parcelable {


    /**
     * id : 1
     * zone_name :
     * address :
     * name : 贵州栖好网络科技有限公司修改3
     * longitude : 26.570795
     * latitude : 107.994669
     */

    private int id;
    private String zone_name;
    private String address;
    private String name;
    private String longitude;
    private String latitude;
    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public AddressListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.zone_name);
        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.tel);
    }

    protected AddressListBean(Parcel in) {
        this.id = in.readInt();
        this.zone_name = in.readString();
        this.address = in.readString();
        this.name = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.tel = in.readString();
    }

    public static final Creator<AddressListBean> CREATOR = new Creator<AddressListBean>() {
        @Override
        public AddressListBean createFromParcel(Parcel source) {
            return new AddressListBean(source);
        }

        @Override
        public AddressListBean[] newArray(int size) {
            return new AddressListBean[size];
        }
    };
}
