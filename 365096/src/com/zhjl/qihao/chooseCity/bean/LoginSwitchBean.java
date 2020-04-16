package com.zhjl.qihao.chooseCity.bean;

public class LoginSwitchBean {


    /**
     * code : 200
     * data : {"roomCode":"","roomName":"","smallCommunityCode":"0002003","smallCommunityName":"北京管理","userType":0}
     * message : ok
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
         * roomCode :
         * roomName :
         * smallCommunityCode : 0002003
         * smallCommunityName : 北京管理
         * userType : 0
         */

        private String roomCode;
        private String roomName;
        private String smallCommunityCode;
        private String smallCommunityName;
        private int userType;

        public String getRoomCode() {
            return roomCode;
        }

        public void setRoomCode(String roomCode) {
            this.roomCode = roomCode;
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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
