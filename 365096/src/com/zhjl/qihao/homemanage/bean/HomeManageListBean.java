package com.zhjl.qihao.homemanage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HomeManageListBean {

    /**
     * code : 200
     * data : [{"mobile":"18230735242","name":"煌林","residentId":"157525188364534c81c8fa2c4f8baaf6","residentType":"1","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"18230735241","name":"煌煌","residentId":"15752524669348609333eb8744379522","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"18201137025","name":"大黄","residentId":"1575255128356c63f8857c28474ba257","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"18988901364","name":"是","residentId":"157525882112c3c7f5288ff2470397eb","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"18386620997","name":"罗","residentId":"157612648482445d306383d04a0fbceb","residentType":"3","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"15808550445","name":"杨美春","residentId":"1576480562274a743d7956ea42f9a802","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"15208559588","name":"甘典颐","residentId":"157675329126157b80e5f3244f57b10a","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"13765591015","name":"yay","residentId":"1576829225619bea4aa561bd4ee7ad02","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"18585185861","name":"杨奎","residentId":"157682961786a2f01366c84c4637a114","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"},{"mobile":"18928836196","name":"陈伟光","residentId":"15770760537565c8ba504ba34031868a","residentType":"2","roomCode":"00440010010102002","roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202","smallCommunityCode":"0044001","smallCommunityName":"博南小区","status":0,"structureArea":"120.0"}]
     * message : 获取数据列表成功
     * total : 0
     * totalPage : 4
     */

    private int code;
    private String message;
    private int total;
    private int totalPage;
    private List<DataBean> data;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * mobile : 18230735242
         * name : 煌林
         * residentId : 157525188364534c81c8fa2c4f8baaf6
         * residentType : 1
         * roomCode : 00440010010102002
         * roomId : 1575104681203e5136803a694f71b80c
         * roomName : H33栋3单元0202
         * smallCommunityCode : 0044001
         * smallCommunityName : 博南小区
         * status : 0
         * structureArea : 120.0
         */

        private String mobile;
        private String name;
        private String residentId;
        private String residentType;
        private String roomCode;
        private String roomId;
        private String roomName;
        private String smallCommunityCode;
        private String smallCommunityName;
        private int status;
        private String structureArea;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStructureArea() {
            return structureArea;
        }

        public void setStructureArea(String structureArea) {
            this.structureArea = structureArea;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mobile);
            dest.writeString(this.name);
            dest.writeString(this.residentId);
            dest.writeString(this.residentType);
            dest.writeString(this.roomCode);
            dest.writeString(this.roomId);
            dest.writeString(this.roomName);
            dest.writeString(this.smallCommunityCode);
            dest.writeString(this.smallCommunityName);
            dest.writeInt(this.status);
            dest.writeString(this.structureArea);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.mobile = in.readString();
            this.name = in.readString();
            this.residentId = in.readString();
            this.residentType = in.readString();
            this.roomCode = in.readString();
            this.roomId = in.readString();
            this.roomName = in.readString();
            this.smallCommunityCode = in.readString();
            this.smallCommunityName = in.readString();
            this.status = in.readInt();
            this.structureArea = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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
}
