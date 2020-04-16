package com.zhjl.qihao.hotspot.bean;

import com.zhjl.qihao.myaction.bean.MyReplyActionBean;

import java.util.List;

public class EveryWeekHotspotListBean {


    /**
     * code : 200
     * data : [{"content":"你好$","createTime":"2019-11-28 09:54","createUser":{"avatar":{"pictureId":"1574906461161b1d340cac074e55a0b5","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d_small.jpg","timestamp":1574935261000},"nickname":"你好,明天","sex":0},"discussNum":0,"forumLabel":"物业投诉","forumNoteId":"1574906040293421a10306614e6bb1ee","forumPictureList":[{"pictureId":"1574906030149d7efc5554d84786a058","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/cfad4cd9570645dfac694d76ca8b82ba.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/cfad4cd9570645dfac694d76ca8b82ba_small.jpg","timestamp":1574934830000},{"pictureId":"15749060303068bbf449dfec413a8eed","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/e71039fdf6d44e75b371ac4bc90b2310.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/e71039fdf6d44e75b371ac4bc90b2310_small.jpg","timestamp":1574934830000},{"pictureId":"157490603039601e4a0e3bc54cf691fa","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/06b252392ee244ff974d9e93b328e876.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/06b252392ee244ff974d9e93b328e876_small.jpg","timestamp":1574934830000}],"howLong":"1小时前","praiseNum":0,"praiseStatus":0,"smallCommunityCode":"0002001","sort":1,"title":"测试发帖帖子"},{"content":"测试帖子发布","createTime":"2019-11-28 10:16","createUser":{"avatar":{"pictureId":"1574906461161b1d340cac074e55a0b5","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d_small.jpg","timestamp":1574935261000},"nickname":"你好,明天","sex":0},"discussNum":0,"forumLabel":"随手美拍","forumNoteId":"15749073924576becd87d5404a3ca5f5","forumPictureList":[{"pictureId":"15749073870641949961c5e74fcfa498","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/6c942333568b4a9d86c6f0f90f62a86e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/6c942333568b4a9d86c6f0f90f62a86e_small.jpg","timestamp":1574936187000},{"pictureId":"1574907387199041b76ccffe485ab385","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/aa0d4c32a0ea48089c8de99f71fd0ba9.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/aa0d4c32a0ea48089c8de99f71fd0ba9_small.jpg","timestamp":1574936187000}],"howLong":"42分前","praiseNum":0,"praiseStatus":0,"smallCommunityCode":"0002001","sort":2,"title":"你好"}]
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
         * content : 你好$
         * createTime : 2019-11-28 09:54
         * createUser : {"avatar":{"pictureId":"1574906461161b1d340cac074e55a0b5","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d_small.jpg","timestamp":1574935261000},"nickname":"你好,明天","sex":0}
         * discussNum : 0
         * forumLabel : 物业投诉
         * forumNoteId : 1574906040293421a10306614e6bb1ee
         * forumPictureList : [{"pictureId":"1574906030149d7efc5554d84786a058","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/cfad4cd9570645dfac694d76ca8b82ba.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/cfad4cd9570645dfac694d76ca8b82ba_small.jpg","timestamp":1574934830000},{"pictureId":"15749060303068bbf449dfec413a8eed","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/e71039fdf6d44e75b371ac4bc90b2310.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/e71039fdf6d44e75b371ac4bc90b2310_small.jpg","timestamp":1574934830000},{"pictureId":"157490603039601e4a0e3bc54cf691fa","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/06b252392ee244ff974d9e93b328e876.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/06b252392ee244ff974d9e93b328e876_small.jpg","timestamp":1574934830000}]
         * howLong : 1小时前
         * praiseNum : 0
         * praiseStatus : 0
         * smallCommunityCode : 0002001
         * sort : 1
         * title : 测试发帖帖子
         */

        private String content;
        private String createTime;
        private CreateUserBean createUser;
        private int discussNum;
        private String forumLabel;
        private String forumNoteId;
        private String howLong;
        private int praiseNum;
        private int praiseStatus;
        private String smallCommunityCode;
        private int sort;
        private String title;
        private List<ForumPictureListBean> forumPictureList;
        private long browseNumber;
        private TopicBean topic;

        public TopicBean getTopic() {
            return topic;
        }

        public void setTopic(TopicBean topic) {
            this.topic = topic;
        }

        public long getBrowseNumber() {
            return browseNumber;
        }

        public void setBrowseNumber(long browseNumber) {
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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ForumPictureListBean> getForumPictureList() {
            return forumPictureList;
        }

        public void setForumPictureList(List<ForumPictureListBean> forumPictureList) {
            this.forumPictureList = forumPictureList;
        }

        public static class CreateUserBean {
            /**
             * avatar : {"pictureId":"1574906461161b1d340cac074e55a0b5","picturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d_small.jpg","timestamp":1574935261000}
             * nickname : 你好,明天
             * sex : 0
             */

            private AvatarBean avatar;
            private String nickname;
            private int sex;

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

            public static class AvatarBean {
                /**
                 * pictureId : 1574906461161b1d340cac074e55a0b5
                 * picturePath : http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d.jpg
                 * smallPicturePath : http://47.105.215.157:8080/psms/upload/2019-11-28/2dcce71dadaf429692f1c54ac093501d_small.jpg
                 * timestamp : 1574935261000
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

        public static class ForumPictureListBean {
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
