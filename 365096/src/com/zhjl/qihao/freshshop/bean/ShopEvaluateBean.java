package com.zhjl.qihao.freshshop.bean;

import java.util.List;

public class ShopEvaluateBean {

    /**
     * product_comments : [{"id":1,"phone_user_id":"150125593209311b1b8755e240e386c4","content":"东西很好我很喜欢","score":5,"score_status":"好评","nick_name":"黄佛丁","user_picture":"http://192.168.1.48/attachment/image/user/avatar/20191116/8221187f6a6f4e928074af9179a4e0f5_small.jpg","images":{"min":["http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/1_min.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/2_min.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/3_min.jpg"],"middle":["http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/1_middle.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/2_middle.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/3_middle.jpg"]}},{"id":2,"phone_user_id":"15237048182780fe50c0428540e79463","content":"不错不错","score":4,"score_status":"中评","nick_name":"骆驼","user_picture":"http://192.168.1.48/attachment/image/user/avatar/20191116/7a19707b661449fd8fc4c581ac9b72f3_small.jpg","images":{"min":["http://192.168.1.48/attachment/image/user/15237048182780fe50c0428540e79463/comment/1_min.jpg"],"middle":["http://192.168.1.48/attachment/image/user/15237048182780fe50c0428540e79463/comment/1_middle.jpg"]}},{"id":3,"phone_user_id":"153103740570afb8c0981fb7414191e8","content":"太棒了","score":3,"score_status":"差评","nick_name":"长风","user_picture":""}]
     * info : {"total":3,"totalPage":1,"currentPage":1}
     */

    private InfoBean info;
    private List<ProductCommentsBean> product_comments;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ProductCommentsBean> getProduct_comments() {
        return product_comments;
    }

    public void setProduct_comments(List<ProductCommentsBean> product_comments) {
        this.product_comments = product_comments;
    }

    public static class InfoBean {
        /**
         * total : 3
         * totalPage : 1
         * currentPage : 1
         */

        private int total;
        private int totalPage;
        private int currentPage;

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

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }
    }

    public static class ProductCommentsBean {
        /**
         * id : 1
         * phone_user_id : 150125593209311b1b8755e240e386c4
         * content : 东西很好我很喜欢
         * score : 5
         * score_status : 好评
         * nick_name : 黄佛丁
         * user_picture : http://192.168.1.48/attachment/image/user/avatar/20191116/8221187f6a6f4e928074af9179a4e0f5_small.jpg
         * images : {"min":["http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/1_min.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/2_min.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/3_min.jpg"],"middle":["http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/1_middle.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/2_middle.jpg","http://192.168.1.48/attachment/image/user/150125593209311b1b8755e240e386c4/comment/3_middle.jpg"]}
         */

        private int id;
        private String phone_user_id;
        private String content;
        private int score;
        private String score_status;
        private String nick_name;
        private String user_picture;
        private String create_date;
        private ImagesBean images;

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone_user_id() {
            return phone_user_id;
        }

        public void setPhone_user_id(String phone_user_id) {
            this.phone_user_id = phone_user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getScore_status() {
            return score_status;
        }

        public void setScore_status(String score_status) {
            this.score_status = score_status;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getUser_picture() {
            return user_picture;
        }

        public void setUser_picture(String user_picture) {
            this.user_picture = user_picture;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public static class ImagesBean {
            private List<String> min;
            private List<String> middle;

            public List<String> getMin() {
                return min;
            }

            public void setMin(List<String> min) {
                this.min = min;
            }

            public List<String> getMiddle() {
                return middle;
            }

            public void setMiddle(List<String> middle) {
                this.middle = middle;
            }
        }
    }
}
