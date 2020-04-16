package com.zhjl.qihao.propertyRepresentations.bean;

import java.util.List;

public class ExposureTypeBean {

    /**
     * code : 200
     * data : [{"communityCode":"ZZZZ","createTime":"02020-04-08 11:00","id":1,"name":"事件通报","smallCommunityCode":""},{"communityCode":"ZZZZ","createTime":"02020-04-08 11:00","id":2,"name":"物业申述","smallCommunityCode":""}]
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
         * communityCode : ZZZZ
         * createTime : 02020-04-08 11:00
         * id : 1
         * name : 事件通报
         * smallCommunityCode :
         */

        private String communityCode;
        private String createTime;
        private int id;
        private String name;
        private String smallCommunityCode;

        public String getCommunityCode() {
            return communityCode;
        }

        public void setCommunityCode(String communityCode) {
            this.communityCode = communityCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSmallCommunityCode() {
            return smallCommunityCode;
        }

        public void setSmallCommunityCode(String smallCommunityCode) {
            this.smallCommunityCode = smallCommunityCode;
        }
    }
}
