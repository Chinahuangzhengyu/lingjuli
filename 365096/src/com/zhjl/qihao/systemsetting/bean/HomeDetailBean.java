package com.zhjl.qihao.systemsetting.bean;

import java.util.List;

public class HomeDetailBean {

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

    public static class DataBean {
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
        private List<PicturesBean> pictures;

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

        public List<PicturesBean> getPictures() {
            return pictures;
        }

        public void setPictures(List<PicturesBean> pictures) {
            this.pictures = pictures;
        }

        public static class PicturesBean{
            private int fileSize;
            private String filename;
            private String height;
            private String width;

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
        }
        public static class RoomInfoBean {
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
        }

        public static class SmallCommunityInfoBean {
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
        }
    }
}
