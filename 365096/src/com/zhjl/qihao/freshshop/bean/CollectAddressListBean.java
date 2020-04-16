package com.zhjl.qihao.freshshop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CollectAddressListBean implements Parcelable {

    /**
     * status : true
     * collectes : [{"id":1,"community_name":"博南小区","address":"7好鲜生便利店","name":"","tel":""}]
     */

    private String status;
    private List<CollectesBean> collectes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CollectesBean> getCollectes() {
        return collectes;
    }

    public void setCollectes(List<CollectesBean> collectes) {
        this.collectes = collectes;
    }

    public static class CollectesBean implements Parcelable {
        /**
         * id : 1
         * community_name : 博南小区
         * address : 7好鲜生便利店
         * name :
         * tel :
         */

        private int id;
        private String community_name;
        private String address;
        private String name;
        private String tel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.community_name);
            dest.writeString(this.address);
            dest.writeString(this.name);
            dest.writeString(this.tel);
        }

        public CollectesBean() {
        }

        protected CollectesBean(Parcel in) {
            this.id = in.readInt();
            this.community_name = in.readString();
            this.address = in.readString();
            this.name = in.readString();
            this.tel = in.readString();
        }

        public static final Creator<CollectesBean> CREATOR = new Creator<CollectesBean>() {
            @Override
            public CollectesBean createFromParcel(Parcel source) {
                return new CollectesBean(source);
            }

            @Override
            public CollectesBean[] newArray(int size) {
                return new CollectesBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeList(this.collectes);
    }

    public CollectAddressListBean() {
    }

    protected CollectAddressListBean(Parcel in) {
        this.status = in.readString();
        this.collectes = new ArrayList<CollectesBean>();
        in.readList(this.collectes, CollectesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CollectAddressListBean> CREATOR = new Parcelable.Creator<CollectAddressListBean>() {
        @Override
        public CollectAddressListBean createFromParcel(Parcel source) {
            return new CollectAddressListBean(source);
        }

        @Override
        public CollectAddressListBean[] newArray(int size) {
            return new CollectAddressListBean[size];
        }
    };
}
