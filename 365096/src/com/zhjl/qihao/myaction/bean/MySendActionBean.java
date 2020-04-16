package com.zhjl.qihao.myaction.bean;

import java.util.List;

public class MySendActionBean {

    /**
     * code : 200
     * data : [{"browseNumber":0,"content":"哈哈","createTime":"2019-11-30 04:51","createUser":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"userId":"15220328700869de92519083445f881d"},"discussNum":0,"forumLabel":"兴趣爱好","forumNoteId":"15750894856571725cb932174513bbaa","forumPictureList":[],"howLong":"83天前","noteSource":0,"praiseNum":0,"praiseStatus":0,"smallCommunityCode":"0002001","title":"今天天气真好","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},{"browseNumber":2,"content":"1111111111","createTime":"2020-02-18 10:30","createUser":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"userId":"15220328700869de92519083445f881d"},"discussNum":0,"forumLabel":"随手美拍","forumNoteId":"1581993025917374d0e80671434287e7","forumPictureList":[],"howLong":"3天前","noteSource":0,"praiseNum":1,"praiseStatus":0,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题02","id":2,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题01"}},{"browseNumber":2,"content":"终于成功了","createTime":"2020-02-19 10:45","createUser":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"userId":"15220328700869de92519083445f881d"},"discussNum":2,"forumLabel":"随手美拍","forumNoteId":"158208034458ccfcf068aecb476a9229","forumPictureList":[{"fileSize":203346,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg","height":"2203.0","width":"1862.0"}],"howLong":"2天前","noteSource":0,"praiseNum":0,"praiseStatus":0,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题02","id":2,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题01"}}]
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
         * browseNumber : 0
         * content : 哈哈
         * createTime : 2019-11-30 04:51
         * createUser : {"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"userId":"15220328700869de92519083445f881d"}
         * discussNum : 0
         * forumLabel : 兴趣爱好
         * forumNoteId : 15750894856571725cb932174513bbaa
         * forumPictureList : []
         * howLong : 83天前
         * noteSource : 0
         * praiseNum : 0
         * praiseStatus : 0
         * smallCommunityCode : 0002001
         * title : 今天天气真好
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

        public static class CreateUserBean {
            /**
             * avatar : {"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000}
             * nickname : 你好
             * sex : 0
             * userId : 15220328700869de92519083445f881d
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
                 * pictureId : 157622613729c686413a1611456da243
                 * picturePath : http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg
                 * smallPicturePath : http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg
                 * timestamp : 1576226137000
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
    }
}
