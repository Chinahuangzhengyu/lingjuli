package com.zhjl.qihao.propertyservicecomplaint.bean;

import java.util.List;

public class MessageListBean {

    /**
     * code : 200
     * data : {"totalPage":19,"noReadNum":109,"list":[{"createTime":"2019-07-25 19:46","messageId":3704,"notifyPic":"","status":0,"summary":"qwer","title":"物业消息","type":2},{"createTime":"2019-07-25 19:37","messageId":3702,"notifyPic":"","status":0,"summary":"消息通知","title":"物业消息","type":2},{"createTime":"2019-07-25 19:26","messageId":3701,"notifyPic":"","status":0,"summary":"消息通知","title":"您有一条新的物业消息","type":2},{"createTime":"2019-07-25 19:21","messageId":3699,"notifyPic":"","status":0,"summary":"物业测试","title":"物业消息","type":2},{"createTime":"2019-07-24 18:08","messageId":3697,"notifyPic":"","status":1,"summary":"消息通知","title":"物业消息","type":2},{"createTime":"2019-05-28 00:15","messageId":3692,"notifyPic":"","status":1,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:15","messageId":3691,"notifyPic":"","status":1,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:06","messageId":3689,"notifyPic":"","status":0,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:06","messageId":3688,"notifyPic":"","status":0,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:02","messageId":3686,"notifyPic":"","status":0,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2}]}
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
         * totalPage : 19
         * noReadNum : 109
         * list : [{"createTime":"2019-07-25 19:46","messageId":3704,"notifyPic":"","status":0,"summary":"qwer","title":"物业消息","type":2},{"createTime":"2019-07-25 19:37","messageId":3702,"notifyPic":"","status":0,"summary":"消息通知","title":"物业消息","type":2},{"createTime":"2019-07-25 19:26","messageId":3701,"notifyPic":"","status":0,"summary":"消息通知","title":"您有一条新的物业消息","type":2},{"createTime":"2019-07-25 19:21","messageId":3699,"notifyPic":"","status":0,"summary":"物业测试","title":"物业消息","type":2},{"createTime":"2019-07-24 18:08","messageId":3697,"notifyPic":"","status":1,"summary":"消息通知","title":"物业消息","type":2},{"createTime":"2019-05-28 00:15","messageId":3692,"notifyPic":"","status":1,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:15","messageId":3691,"notifyPic":"","status":1,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:06","messageId":3689,"notifyPic":"","status":0,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:06","messageId":3688,"notifyPic":"","status":0,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2},{"createTime":"2019-05-28 00:02","messageId":3686,"notifyPic":"","status":0,"summary":"您的维修服务已完成","title":"您的维修服务已完成","type":2}]
         */

        private int totalPage;
        private int noReadNum;
        private List<ListBean> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getNoReadNum() {
            return noReadNum;
        }

        public void setNoReadNum(int noReadNum) {
            this.noReadNum = noReadNum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * createTime : 2019-07-25 19:46
             * messageId : 3704
             * notifyPic :
             * status : 0
             * summary : qwer
             * title : 物业消息
             * type : 2
             */

            private String createTime;
            private int messageId;
            private String notifyPic;
            private int status;
            private String summary;
            private String title;
            private int type;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }

            public String getNotifyPic() {
                return notifyPic;
            }

            public void setNotifyPic(String notifyPic) {
                this.notifyPic = notifyPic;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
