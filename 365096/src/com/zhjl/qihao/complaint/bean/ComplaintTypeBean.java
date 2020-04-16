package com.zhjl.qihao.complaint.bean;

import java.util.List;

public class ComplaintTypeBean {

    /**
     * list : [{"labelName":"物业投诉","id":"150123148717137221cb421a461891e6"}]
     * message : 获取投诉类别成功
     * result : 0
     */

    private String message;
    private String result;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * labelName : 物业投诉
         * id : 150123148717137221cb421a461891e6
         */

        private String labelName;
        private String id;

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
