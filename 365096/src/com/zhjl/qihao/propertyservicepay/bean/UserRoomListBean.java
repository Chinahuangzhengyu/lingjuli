package com.zhjl.qihao.propertyservicepay.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserRoomListBean implements Parcelable {


    /**
     * code : 200
     * data : [{"residentId":"152205700521e5be13c152fd41d281b7","residentType":"1","roomCode":"00020010010126002","roomId":"15012319586070b12ee7bf774221a997","roomName":"H31单元2602","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":55}]
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
         * residentId : 152205700521e5be13c152fd41d281b7
         * residentType : 1
         * roomCode : 00020010010126002
         * roomId : 15012319586070b12ee7bf774221a997
         * roomName : H31单元2602
         * smallCommunityCode : 0002001
         * smallCommunityName : 北京百花KASO创意公园
         * usableArea : 55
         */

        private String residentId;
        private String residentType;
        private String roomCode;
        private String roomId;
        private String roomName;
        private String mobile;
        private String name;
        private String smallCommunityCode;
        private String smallCommunityName;
        private String structureArea;
        private int status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getResidentId() {
            return residentId;
        }

        public void setResidentId(String residentId) {
            this.residentId = residentId;
        }

        public String getResidentType() {
            return residentType;
        }

        public void setResidentType(String residentType) {
            this.residentType = residentType;
        }

        public String getRoomCode() {
            return roomCode;
        }

        public void setRoomCode(String roomCode) {
            this.roomCode = roomCode;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
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

        public String getUsableArea() {
            return structureArea;
        }

        public void setUsableArea(String structureArea) {
            this.structureArea = structureArea;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.residentId);
            dest.writeString(this.residentType);
            dest.writeString(this.roomCode);
            dest.writeString(this.roomId);
            dest.writeString(this.roomName);
            dest.writeString(this.mobile);
            dest.writeString(this.name);
            dest.writeString(this.smallCommunityCode);
            dest.writeString(this.smallCommunityName);
            dest.writeString(this.structureArea);
            dest.writeInt(this.status);
        }

        protected DataBean(Parcel in) {
            this.residentId = in.readString();
            this.residentType = in.readString();
            this.roomCode = in.readString();
            this.roomId = in.readString();
            this.roomName = in.readString();
            this.mobile = in.readString();
            this.name = in.readString();
            this.smallCommunityCode = in.readString();
            this.smallCommunityName = in.readString();
            this.structureArea = in.readString();
            this.status = in.readInt();
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

    public UserRoomListBean() {
    }

    protected UserRoomListBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserRoomListBean> CREATOR = new Parcelable.Creator<UserRoomListBean>() {
        @Override
        public UserRoomListBean createFromParcel(Parcel source) {
            return new UserRoomListBean(source);
        }

        @Override
        public UserRoomListBean[] newArray(int size) {
            return new UserRoomListBean[size];
        }
    };
}
