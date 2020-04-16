package com.zhjl.qihao.systemsetting.bean;

import java.util.List;

public class ResidentBean {

    /**
     * code : 200
     * data : {"rooms":[{"residentId":"152205700521e5be13c152fd41d281b7","residentType":"1","roomCode":"00020010010126002","roomId":"15012319586070b12ee7bf774221a997","roomName":"H31单元2602","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":55},{"residentId":"157467051602af35f813d98b4f419e09","residentType":"","roomCode":"00020010030101002","roomId":"150518819022d21e158f3ca8468fa2cb","roomName":"H61单元0102","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":150},{"residentId":"157467203327259b32a2fc7f4e07a1c0","residentType":"","roomCode":"00020010030101002","roomId":"150518819022d21e158f3ca8468fa2cb","roomName":"H61单元0102","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":150},{"residentId":"157467214713ba112805e8224d33a6b7","residentType":"2","roomCode":"00020010030101002","roomId":"150518819022d21e158f3ca8468fa2cb","roomName":"H61单元0102","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":150}],"occupy":false}
     * message : 操作成功
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
         * rooms : [{"residentId":"152205700521e5be13c152fd41d281b7","residentType":"1","roomCode":"00020010010126002","roomId":"15012319586070b12ee7bf774221a997","roomName":"H31单元2602","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":55},{"residentId":"157467051602af35f813d98b4f419e09","residentType":"","roomCode":"00020010030101002","roomId":"150518819022d21e158f3ca8468fa2cb","roomName":"H61单元0102","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":150},{"residentId":"157467203327259b32a2fc7f4e07a1c0","residentType":"","roomCode":"00020010030101002","roomId":"150518819022d21e158f3ca8468fa2cb","roomName":"H61单元0102","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":150},{"residentId":"157467214713ba112805e8224d33a6b7","residentType":"2","roomCode":"00020010030101002","roomId":"150518819022d21e158f3ca8468fa2cb","roomName":"H61单元0102","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":150}]
         * occupy : false
         */

        private boolean occupy;
        private List<RoomsBean> rooms;
        private String occupyPhone;


        public String getOccupyPhone() {
            return occupyPhone;
        }

        public void setOccupyPhone(String occupyPhone) {
            this.occupyPhone = occupyPhone;
        }

        public boolean isOccupy() {
            return occupy;
        }

        public void setOccupy(boolean occupy) {
            this.occupy = occupy;
        }

        public List<RoomsBean> getRooms() {
            return rooms;
        }

        public void setRooms(List<RoomsBean> rooms) {
            this.rooms = rooms;
        }

        public static class RoomsBean {
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
            private String smallCommunityCode;
            private String smallCommunityName;
            private int usableArea;

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

            public int getUsableArea() {
                return usableArea;
            }

            public void setUsableArea(int usableArea) {
                this.usableArea = usableArea;
            }
        }
    }
}
