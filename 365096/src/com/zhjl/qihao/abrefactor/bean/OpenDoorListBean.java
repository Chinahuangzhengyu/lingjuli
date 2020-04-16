package com.zhjl.qihao.abrefactor.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OpenDoorListBean implements Parcelable {


    /**
     * code : 200
     * data : [{"areaId":12628,"groupId":1,"groupName":"测试门禁组","id":603,"smallCommunityName":"博南小区"}]
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
         * areaId : 12628
         * groupId : 1
         * groupName : 测试门禁组
         * id : 603
         * smallCommunityName : 博南小区
         */

        private int areaId;
        private int groupId;
        private String groupName;
        private int id;
        private String smallCommunityName;
        private String doorAccessId;

        public String getDoorAccessId() {
            return doorAccessId;
        }

        public void setDoorAccessId(String doorAccessId) {
            this.doorAccessId = doorAccessId;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
            dest.writeInt(this.groupId);
            dest.writeString(this.groupName);
            dest.writeInt(this.id);
            dest.writeString(this.smallCommunityName);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.areaId = in.readInt();
            this.groupId = in.readInt();
            this.groupName = in.readString();
            this.id = in.readInt();
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

    public OpenDoorListBean() {
    }

    protected OpenDoorListBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<OpenDoorListBean> CREATOR = new Parcelable.Creator<OpenDoorListBean>() {
        @Override
        public OpenDoorListBean createFromParcel(Parcel source) {
            return new OpenDoorListBean(source);
        }

        @Override
        public OpenDoorListBean[] newArray(int size) {
            return new OpenDoorListBean[size];
        }
    };
}
