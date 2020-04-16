package com.zhjl.qihao.propertyservicerepair.bean;

import java.util.List;

public class PropertyRepairRecordBean {

    /**
     * code : 200
     * data : [{"createTime":"2019-11-09 14:10","description":"哈哈","id":9,"smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-9/f27a68a8b46048aab89bd59f4359c6c6_small.jpg","status":"未处理","typeName":"公共路灯"},{"createTime":"2019-11-09 14:10","description":"","id":8,"smallPicturePath":"","status":"未处理","typeName":"公共路灯"},{"createTime":"2019-11-09 14:07","description":"","id":7,"smallPicturePath":"","status":"未处理","typeName":"公共路灯"}]
     * message : 获取数据成功
     */

    private int code;
    private String message;
    private List<DataBean> data;
    private int totalPage;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

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
         * createTime : 2019-11-09 14:10
         * description : 哈哈
         * id : 9
         * smallPicturePath : http://192.168.1.50:8080/psms/upload/2019-11-9/f27a68a8b46048aab89bd59f4359c6c6_small.jpg
         * status : 未处理
         * typeName : 公共路灯
         */

        private String createTime;
        private String description;
        private long id;
        private String smallPicturePath;
        private String status;
        private String typeName;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSmallPicturePath() {
            return smallPicturePath;
        }

        public void setSmallPicturePath(String smallPicturePath) {
            this.smallPicturePath = smallPicturePath;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
