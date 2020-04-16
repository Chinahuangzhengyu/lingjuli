package com.zhjl.qihao.propertyservicepay.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CarBean implements Parcelable {
    private String key;
    private String value;

    public CarBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    protected CarBean(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<CarBean> CREATOR = new Parcelable.Creator<CarBean>() {
        @Override
        public CarBean createFromParcel(Parcel source) {
            return new CarBean(source);
        }

        @Override
        public CarBean[] newArray(int size) {
            return new CarBean[size];
        }
    };
}
