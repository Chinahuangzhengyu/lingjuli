package com.zhjl.qihao.nearbyinteraction.bean;

import java.util.List;

public class NoteHotSearchBean {

    /**
     * code : 200
     * data : [{"id":1,"name":"测试","times":1},{"id":2,"name":"android.widget.EditText{fae2218 VFED..CL. .F...... 120,0-651,120 #7f09019f app:id/et_search_shop}","times":1},{"id":3,"name":"好的","times":1}]
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
         * id : 1
         * name : 测试
         * times : 1
         */

        private int id;
        private String name;
        private int times;

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

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }
}
