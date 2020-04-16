package com.zhjl.qihao.propertyservicepay.bean;

import java.util.List;

public class PropertyPayTypeListBean {

    /**
     * code : 200
     * data : [{"cateId":6,"cateName":"测试分类"},{"cateId":2,"cateName":"停车费"},{"cateId":1,"cateName":"物业费"}]
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
         * cateId : 6
         * cateName : 测试分类
         */

        private int cateId;
        private String cateName;

        public int getCateId() {
            return cateId;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }
    }
}
