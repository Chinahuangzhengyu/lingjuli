package com.zhjl.qihao.systemsetting.bean;

import java.util.List;

public class RoomsBean {

    /**
     * code : 200
     * data : [{"roomId":"15048601366390e33260f05a41ee98d9","roomName":"H71单元0101"}]
     * message : 获取数据成功
     */

    private int code;
    private String message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * roomId : 15048601366390e33260f05a41ee98d9
         * roomName : H71单元0101
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
}
