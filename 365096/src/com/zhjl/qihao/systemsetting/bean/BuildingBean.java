package com.zhjl.qihao.systemsetting.bean;

import java.util.List;

public class BuildingBean {

    /**
     * code : 200
     * data : [{"id":"15012318519184a6b36e71f047f69a0f","partitionCode":"0002001001","partitionName":"H3"},{"id":"15048571524923fd6cec0e5548b38ffe","partitionCode":"0002001002","partitionName":"H5"},{"id":"1504860000697e20575644324a4db6f7","partitionCode":"0002001003","partitionName":"H6"},{"id":"150486001399f1115583f735447bbfef","partitionCode":"0002001004","partitionName":"H7"}]
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
         * id : 15012318519184a6b36e71f047f69a0f
         * partitionCode : 0002001001
         * partitionName : H3
         */

        private String id;
        private String partitionCode;
        private String partitionName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPartitionCode() {
            return partitionCode;
        }

        public void setPartitionCode(String partitionCode) {
            this.partitionCode = partitionCode;
        }

        public String getPartitionName() {
            return partitionName;
        }

        public void setPartitionName(String partitionName) {
            this.partitionName = partitionName;
        }
    }
}
