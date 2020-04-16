package com.zhjl.qihao.nearbyinteraction.bean;

import java.util.List;

public class SearchNotesBean {

    /**
     * code : 200
     * data : [{"browseNumber":3,"content":"测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论","createTime":"2020-02-18 15:40","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":18,"forumLabel":"随手美拍","forumNoteId":"158201161243b683987631c14c5aab5b","forumPictureList":[],"howLong":"8天前","noteSource":0,"praiseNum":2,"praiseStatus":0,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},{"browseNumber":1,"content":"测试发布帖子","createTime":"2019-12-10 00:43","createUser":{"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"},"discussNum":4,"forumLabel":"随手美拍","forumNoteId":"157593862261804ee8b5c17a4610a8f6","forumPictureList":[],"howLong":"78天前","noteSource":0,"praiseNum":2,"praiseStatus":0,"smallCommunityCode":"0044001","title":"测试","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},{"browseNumber":1,"content":"测试发布帖子","createTime":"2019-12-10 00:43","createUser":{"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"},"discussNum":0,"forumLabel":"随手美拍","forumNoteId":"1575938622266f8e449f09cb40bf9a7a","forumPictureList":[],"howLong":"78天前","noteSource":0,"praiseNum":0,"praiseStatus":0,"smallCommunityCode":"0044001","title":"测试","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},{"browseNumber":1,"content":"测试帖子$","createTime":"2019-11-30 10:18","createUser":{"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"},"discussNum":5,"forumLabel":"随手美拍","forumNoteId":"15751091381108150ba8263646fd843d","forumPictureList":[],"howLong":"88天前","noteSource":0,"praiseNum":1,"praiseStatus":0,"smallCommunityCode":"0044001","title":"测试发帖","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}}]
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
         * browseNumber : 3
         * content : 测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论
         * createTime : 2020-02-18 15:40
         * createUser : {"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"}
         * discussNum : 18
         * forumLabel : 随手美拍
         * forumNoteId : 158201161243b683987631c14c5aab5b
         * forumPictureList : []
         * howLong : 8天前
         * noteSource : 0
         * praiseNum : 2
         * praiseStatus : 0
         * smallCommunityCode : 0044001
         * title :
         * topic : {"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}
         */

        private int browseNumber;
        private String content;
        private String createTime;
        private CreateUserBean createUser;
        private int discussNum;
        private String forumLabel;
        private String forumNoteId;
        private String howLong;
        private int noteSource;
        private int praiseNum;
        private int praiseStatus;
        private String smallCommunityCode;
        private String title;
        private TopicBean topic;
        private List<ForumPictureListBean> forumPictureList;

        public int getBrowseNumber() {
            return browseNumber;
        }

        public void setBrowseNumber(int browseNumber) {
            this.browseNumber = browseNumber;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public CreateUserBean getCreateUser() {
            return createUser;
        }

        public void setCreateUser(CreateUserBean createUser) {
            this.createUser = createUser;
        }

        public int getDiscussNum() {
            return discussNum;
        }

        public void setDiscussNum(int discussNum) {
            this.discussNum = discussNum;
        }

        public String getForumLabel() {
            return forumLabel;
        }

        public void setForumLabel(String forumLabel) {
            this.forumLabel = forumLabel;
        }

        public String getForumNoteId() {
            return forumNoteId;
        }

        public void setForumNoteId(String forumNoteId) {
            this.forumNoteId = forumNoteId;
        }

        public String getHowLong() {
            return howLong;
        }

        public void setHowLong(String howLong) {
            this.howLong = howLong;
        }

        public int getNoteSource() {
            return noteSource;
        }

        public void setNoteSource(int noteSource) {
            this.noteSource = noteSource;
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

        public String getSmallCommunityCode() {
            return smallCommunityCode;
        }

        public void setSmallCommunityCode(String smallCommunityCode) {
            this.smallCommunityCode = smallCommunityCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public TopicBean getTopic() {
            return topic;
        }

        public void setTopic(TopicBean topic) {
            this.topic = topic;
        }

        public List<ForumPictureListBean> getForumPictureList() {
            return forumPictureList;
        }

        public void setForumPictureList(List<ForumPictureListBean> forumPictureList) {
            this.forumPictureList = forumPictureList;
        }


        public static class ForumPictureListBean{
            /**
             * fileSize : 203346
             * filename : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg
             * height : 2203.0
             * width : 1862.0
             */

            private int fileSize;
            private String filename;
            private String height;
            private String width;

            public int getFileSize() {
                return fileSize;
            }

            public void setFileSize(int fileSize) {
                this.fileSize = fileSize;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }
        }
        public static class CreateUserBean {
            /**
             * avatar : {"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0}
             * nickname : Pl47cP80kQV
             * sex : 2
             * userId : 157585787506668804efc3fd45d89688
             */

            private AvatarBean avatar;
            private String nickname;
            private int sex;
            private String userId;

            public AvatarBean getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBean avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public static class AvatarBean {
                /**
                 * pictureId :
                 * picturePath :
                 * smallPicturePath :
                 * timestamp : 0
                 */

                private String pictureId;
                private String picturePath;
                private String smallPicturePath;
                private long timestamp;

                public String getPictureId() {
                    return pictureId;
                }

                public void setPictureId(String pictureId) {
                    this.pictureId = pictureId;
                }

                public String getPicturePath() {
                    return picturePath;
                }

                public void setPicturePath(String picturePath) {
                    this.picturePath = picturePath;
                }

                public String getSmallPicturePath() {
                    return smallPicturePath;
                }

                public void setSmallPicturePath(String smallPicturePath) {
                    this.smallPicturePath = smallPicturePath;
                }

                public long getTimestamp() {
                    return timestamp;
                }

                public void setTimestamp(long timestamp) {
                    this.timestamp = timestamp;
                }
            }
        }

        public static class TopicBean {
            /**
             * comment : 测试话题
             * id : 1
             * topicIcon : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png
             * topicName : 测试话题
             */

            private String comment;
            private int id;
            private String topicIcon;
            private String topicName;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTopicIcon() {
                return topicIcon;
            }

            public void setTopicIcon(String topicIcon) {
                this.topicIcon = topicIcon;
            }

            public String getTopicName() {
                return topicName;
            }

            public void setTopicName(String topicName) {
                this.topicName = topicName;
            }
        }
    }
}
