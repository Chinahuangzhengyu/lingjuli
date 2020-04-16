package com.zhjl.qihao.complaint.bean;

import java.util.List;

public class PropertyProgressBean {

    /**
     * code : 200
     * data : [{"createTime":"2019-09-19 22:48:19","forumNoteId":"15696600027664ed23d878574065bd6c","id":1,"pics":["http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"],"praiseNum":0,"praiseStatus":0,"replayNote":"你好我们接到投诉,马上处理."}]
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
         * createTime : 2019-09-19 22:48:19
         * forumNoteId : 15696600027664ed23d878574065bd6c
         * id : 1
         * pics : ["http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"]
         * praiseNum : 0
         * praiseStatus : 0
         * replayNote : 你好我们接到投诉,马上处理.
         */

        private String createTime;
        private String forumNoteId;
        private int id;
        private int praiseNum;
        private int praiseStatus;
        private String replayNote;
        private List<String> pics;
        private String howLong;

        public String getHowLong() {
            return howLong;
        }

        public void setHowLong(String howLong) {
            this.howLong = howLong;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getForumNoteId() {
            return forumNoteId;
        }

        public void setForumNoteId(String forumNoteId) {
            this.forumNoteId = forumNoteId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getPraiseStatus() {
            return praiseStatus;
        }

        public void setPraiseStatus(int praiseStatus) {
            this.praiseStatus = praiseStatus;
        }

        public String getReplayNote() {
            return replayNote;
        }

        public void setReplayNote(String replayNote) {
            this.replayNote = replayNote;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
