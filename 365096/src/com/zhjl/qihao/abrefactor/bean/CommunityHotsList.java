package com.zhjl.qihao.abrefactor.bean;

import java.util.List;

public class CommunityHotsList {

    /**
     * code : 200
     * data : [{"browseNumber":2,"content":"有问题，就是这样的一个问题","createTime":"2020-02-12 11:21","createUser":{"avatar":{"pictureId":"157535734724caddc6ba50b2420589f5","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b_small.jpg","timestamp":1575357347000},"nickname":"庞瑶","sex":2,"userId":"157507819280b72429266d6444fa9b2e"},"discussNum":0,"forumLabel":"物业投诉","forumNoteId":"158147769797019818f433c241929b2c","forumPictureList":[],"howLong":"2020-02-12","noteSource":0,"praiseNum":0,"praiseStatus":0,"smallCommunityCode":"0044001","title":"111111111","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},{"browseNumber":1,"content":"难搞","createTime":"2020-03-16 17:41","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"陈伟光","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":0,"forumLabel":"物业投诉","forumNoteId":"1584351678735a772bd4c6df4fd58019","forumPictureList":[{"fileSize":82739,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200316/picture_1584351677498.jpg","height":"1102.0","width":"826.0"}],"howLong":"20小时前","noteSource":0,"praiseNum":1,"praiseStatus":0,"smallCommunityCode":"0044001","title":"投诉","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},{"browseNumber":1,"content":"你好，我是我","createTime":"2020-03-17 11:05","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"陈伟光","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":0,"forumLabel":"物业投诉","forumNoteId":"1584414329044f796c2a950942f6882c","forumPictureList":[{"fileSize":1077620,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200317/picture_1584414327341.jpg","height":"4032.0","width":"3024.0"}],"howLong":"3小时前","noteSource":0,"praiseNum":0,"praiseStatus":0,"smallCommunityCode":"0044001","title":"投诉","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}}]
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
         * browseNumber : 2
         * content : 有问题，就是这样的一个问题
         * createTime : 2020-02-12 11:21
         * createUser : {"avatar":{"pictureId":"157535734724caddc6ba50b2420589f5","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b_small.jpg","timestamp":1575357347000},"nickname":"庞瑶","sex":2,"userId":"157507819280b72429266d6444fa9b2e"}
         * discussNum : 0
         * forumLabel : 物业投诉
         * forumNoteId : 158147769797019818f433c241929b2c
         * forumPictureList : []
         * howLong : 2020-02-12
         * noteSource : 0
         * praiseNum : 0
         * praiseStatus : 0
         * smallCommunityCode : 0044001
         * title : 111111111
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
             * avatar : {"pictureId":"157535734724caddc6ba50b2420589f5","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b_small.jpg","timestamp":1575357347000}
             * nickname : 庞瑶
             * sex : 2
             * userId : 157507819280b72429266d6444fa9b2e
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
                 * pictureId : 157535734724caddc6ba50b2420589f5
                 * picturePath : http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b.jpg
                 * smallPicturePath : http://47.105.215.157:8080/psms/upload/2019-12-3/85075deb7f984304b6fde98d007ffa9b_small.jpg
                 * timestamp : 1575357347000
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
        public static class ForumPictureListBean{
            /**
             * fileSize : 203346
             * filename : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg
             * height : 2203.0
             * width : 1862.0
             */

            private long fileSize;
            private String filename;
            private String height;
            private String width;

            public long getFileSize() {
                return fileSize;
            }

            public void setFileSize(long fileSize) {
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
