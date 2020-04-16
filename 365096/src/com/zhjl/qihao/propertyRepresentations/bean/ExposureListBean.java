package com.zhjl.qihao.propertyRepresentations.bean;

import java.util.List;

public class ExposureListBean {

    /**
     * code : 200
     * data : [{"createUser":{"smallCommunityCode":"0044001","smallCommunityName":"博南小区"},"howLong":"2020-04-11","id":18,"link":"https://tj.qihaolingjuli.com/api/dynamic/manageInfo?id=18","title":"如何正确学习SpringBoot，进行现代化网站开发？","views":1}]
     * message : 获取数据列表成功
     * total : 0
     * totalPage : 1
     */

    private int code;
    private String message;
    private int total;
    private int totalPage;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createUser : {"smallCommunityCode":"0044001","smallCommunityName":"博南小区"}
         * howLong : 2020-04-11
         * id : 18
         * link : https://tj.qihaolingjuli.com/api/dynamic/manageInfo?id=18
         * title : 如何正确学习SpringBoot，进行现代化网站开发？
         * views : 1
         */

        private CreateUserBean createUser;
        private String howLong;
        private int id;
        private String link;
        private String title;
        private int views;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public CreateUserBean getCreateUser() {
            return createUser;
        }

        public void setCreateUser(CreateUserBean createUser) {
            this.createUser = createUser;
        }

        public String getHowLong() {
            return howLong;
        }

        public void setHowLong(String howLong) {
            this.howLong = howLong;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public static class CreateUserBean {
            /**
             * smallCommunityCode : 0044001
             * smallCommunityName : 博南小区
             */

            private String smallCommunityCode;
            private String smallCommunityName;

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
        }
    }
}
