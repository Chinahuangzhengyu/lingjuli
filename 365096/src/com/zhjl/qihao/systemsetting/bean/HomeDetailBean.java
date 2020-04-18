package com.zhjl.qihao.systemsetting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class HomeDetailBean implements Parcelable {

    /**
     * code : 200
     * data : {"mobile":"18201137023","pictures":[],"residentName":"大黄","residentType":1,"roomInfo":{"roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202"},"smallCommunityInfo":{"smallCommunityCode":"0044001","smallCommunityName":"博南小区"}}
     * message : 处理成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean implements Parcelable {
        /**
         * mobile : 18201137023
         * pictures : []
         * residentName : 大黄
         * residentType : 1
         * roomInfo : {"roomId":"1575104681203e5136803a694f71b80c","roomName":"H33栋3单元0202"}
         * smallCommunityInfo : {"smallCommunityCode":"0044001","smallCommunityName":"博南小区"}
         */

        private String mobile;
        private String residentName;
        private int residentType;
        private RoomInfoBean roomInfo;
        private SmallCommunityInfoBean smallCommunityInfo;
        private ArrayList<PicturesBean> pictures;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getResidentName() {
            return residentName;
        }

        public void setResidentName(String residentName) {
            this.residentName = residentName;
        }

        public int getResidentType() {
            return residentType;
        }

        public void setResidentType(int residentType) {
            this.residentType = residentType;
        }

        public RoomInfoBean getRoomInfo() {
            return roomInfo;
        }

        public void setRoomInfo(RoomInfoBean roomInfo) {
            this.roomInfo = roomInfo;
        }

        public SmallCommunityInfoBean getSmallCommunityInfo() {
            return smallCommunityInfo;
        }

        public void setSmallCommunityInfo(SmallCommunityInfoBean smallCommunityInfo) {
            this.smallCommunityInfo = smallCommunityInfo;
        }

        public ArrayList<PicturesBean> getPictures() {
            return pictures;
        }

        public void setPictures(ArrayList<PicturesBean> pictures) {
            this.pictures = pictures;
        }

        public static class PicturesBean implements Parcelable {
            private int fileSize;
            private String filename;
            private String height;
            private String width;
            private Long ossId;

            public Long getOssId() {
                return ossId;
            }

            public void setOssId(Long ossId) {
                this.ossId = ossId;
            }

            public static Creator<PicturesBean> getCREATOR() {
                return CREATOR;
            }

            public int getFileSize() {
                return fileSize;
            }

            public void setFileSize(int fileSize) {
                this.fileSize = fileSize;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public PicturesBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.fileSize);
                dest.writeString(this.filename);
                dest.writeString(this.height);
                dest.writeString(this.width);
                dest.writeValue(this.ossId);
            }

            protected PicturesBean(Parcel in) {
                this.fileSize = in.readInt();
                this.filename = in.readString();
                this.height = in.readString();
                this.width = in.readString();
                this.ossId = (Long) in.readValue(Long.class.getClassLoader());
            }

            public static final Creator<PicturesBean> CREATOR = new Creator<PicturesBean>() {
                @Override
                public PicturesBean createFromParcel(Parcel source) {
                    return new PicturesBean(source);
                }

                @Override
                public PicturesBean[] newArray(int size) {
                    return new PicturesBean[size];
                }
            };
        }
        public static class RoomInfoBean implements Parcelable {
            /**
             * roomId : 1575104681203e5136803a694f71b80c
             * roomName : H33栋3单元0202
             */

            private String roomId;
            private String roomName;

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.roomId);
                dest.writeString(this.roomName);
            }

            public RoomInfoBean() {
            }

            protected RoomInfoBean(Parcel in) {
                this.roomId = in.readString();
                this.roomName = in.readString();
            }

            public static final Creator<RoomInfoBean> CREATOR = new Creator<RoomInfoBean>() {
                @Override
                public RoomInfoBean createFromParcel(Parcel source) {
                    return new RoomInfoBean(source);
                }

                @Override
                public RoomInfoBean[] newArray(int size) {
                    return new RoomInfoBean[size];
                }
            };
        }

        public static class SmallCommunityInfoBean implements Parcelable {
            /**
             * smallCommunityCode : 0044001
             * smallCommunityName : 博南小区
             */

            private String smallCommunityCode;
            private String smallCommunityName;

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
                dest.writeString(this.smallCommunityCode);
                dest.writeString(this.smallCommunityName);
            }

            public SmallCommunityInfoBean() {
            }

            protected SmallCommunityInfoBean(Parcel in) {
                this.smallCommunityCode = in.readString();
                this.smallCommunityName = in.readString();
            }

            public static final Creator<SmallCommunityInfoBean> CREATOR = new Creator<SmallCommunityInfoBean>() {
                @Override
                public SmallCommunityInfoBean createFromParcel(Parcel source) {
                    return new SmallCommunityInfoBean(source);
                }

                @Override
                public SmallCommunityInfoBean[] newArray(int size) {
                    return new SmallCommunityInfoBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mobile);
            dest.writeString(this.residentName);
            dest.writeInt(this.residentType);
            dest.writeParcelable(this.roomInfo, flags);
            dest.writeParcelable(this.smallCommunityInfo, flags);
            dest.writeTypedList(this.pictures);
            dest.writeInt(this.status);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.mobile = in.readString();
            this.residentName = in.readString();
            this.residentType = in.readInt();
            this.roomInfo = in.readParcelable(RoomInfoBean.class.getClassLoader());
            this.smallCommunityInfo = in.readParcelable(SmallCommunityInfoBean.class.getClassLoader());
            this.pictures = in.createTypedArrayList(PicturesBean.CREATOR);
            this.status = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    public HomeDetailBean() {
    }

    protected HomeDetailBean(Parcel in) {
        this.code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<HomeDetailBean> CREATOR = new Parcelable.Creator<HomeDetailBean>() {
        @Override
        public HomeDetailBean createFromParcel(Parcel source) {
            return new HomeDetailBean(source);
        }

        @Override
        public HomeDetailBean[] newArray(int size) {
            return new HomeDetailBean[size];
        }
    };
}
