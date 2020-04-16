package com.zhjl.qihao.abrefactor.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SmallCommunityListBean implements Parcelable {

    /**
     * code : 200
     * data : [{"areaId":3001,"id":1,"smallCommunityCode":"0044001","smallCommunityName":"博南小区"}]
     * message : 获取数据成功
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * areaId : 3001
         * id : 1
         * smallCommunityCode : 0044001
         * smallCommunityName : 博南小区
         */

        private int areaId;
        private int id;
        private String smallCommunityCode;
        private String smallCommunityName;

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSmallCommunityCode() {
            return smallCommunityCode;
        }

        public void setSmallCommunityCode(String smallCommunityCode) {
            this.smallCommunityCode = smallCommunityCode;
        }

        public String getSmallCommunityName() {
            return smallCommunityName;
        }

        public void setSmallCommunityName(String smallCommunityName) {
            this.smallCommunityName = smallCommunityName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.areaId);
            dest.writeInt(this.id);
            dest.writeString(this.smallCommunityCode);
            dest.writeString(this.smallCommunityName);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.areaId = in.readInt();
            this.id = in.readInt();
            this.smallCommunityCode = in.readString();
            this.smallCommunityName = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeList(this.data);
    }

    public SmallCommunityListBean() {
    }

    protected SmallCommunityListBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SmallCommunityListBean> CREATOR = new Parcelable.Creator<SmallCommunityListBean>() {
        @Override
        public SmallCommunityListBean createFromParcel(Parcel source) {
            return new SmallCommunityListBean(source);
        }

        @Override
        public SmallCommunityListBean[] newArray(int size) {
            return new SmallCommunityListBean[size];
        }
    };
}
