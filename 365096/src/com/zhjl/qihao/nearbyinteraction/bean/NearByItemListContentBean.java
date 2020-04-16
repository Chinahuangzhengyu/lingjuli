package com.zhjl.qihao.nearbyinteraction.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class NearByItemListContentBean {


    /**
     * code : 200
     * data : [{"browseNumber":0,"complaintId":"","content":"1111111111","createTime":"2020-02-18 10:30","createUser":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0},"discussNum":0,"forumLabel":"随手美拍","forumNoteId":"1581993025917374d0e80671434287e7","forumPictureList":[{"fileSize":203346,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg","height":"2203.0","width":"1862.0"}],"howLong":"1天前","noteSource":0,"praiseNum":0,"praiseStatus":null,"processStatus":null,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题02","id":2,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题01"}},{"browseNumber":0,"complaintId":"","content":"终于成功了","createTime":"2020-02-19 10:45","createUser":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0},"discussNum":0,"forumLabel":"随手美拍","forumNoteId":"158208034458ccfcf068aecb476a9229","forumPictureList":[{"fileSize":203346,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg","height":"2203.0","width":"1862.0"}],"howLong":"2分前","noteSource":0,"praiseNum":0,"praiseStatus":null,"processStatus":null,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题02","id":2,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题01"}}]
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

    public static class DataBean implements Parcelable {
        /**
         * browseNumber : 0
         * complaintId :
         * content : 1111111111
         * createTime : 2020-02-18 10:30
         * createUser : {"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0}
         * discussNum : 0
         * forumLabel : 随手美拍
         * forumNoteId : 1581993025917374d0e80671434287e7
         * forumPictureList : [{"fileSize":203346,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg","height":"2203.0","width":"1862.0"}]
         * howLong : 1天前
         * noteSource : 0
         * praiseNum : 0
         * praiseStatus : null
         * processStatus : null
         * smallCommunityCode : 0044001
         * title :
         * topic : {"comment":"测试话题02","id":2,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题01"}
         */

        private int browseNumber;
        private String complaintId;
        private String content;
        private String createTime;
        private CreateUserBean createUser;
        private int discussNum;
        private String forumLabel;
        private String forumNoteId;
        private String howLong;
        private int noteSource;
        private int praiseNum;
        private String praiseStatus;
        private String processStatus;
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

        public String getComplaintId() {
            return complaintId;
        }

        public void setComplaintId(String complaintId) {
            this.complaintId = complaintId;
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

        public String getPraiseStatus() {
            return praiseStatus;
        }

        public void setPraiseStatus(String praiseStatus) {
            this.praiseStatus = praiseStatus;
        }

        public String getProcessStatus() {
            return processStatus;
        }

        public void setProcessStatus(String processStatus) {
            this.processStatus = processStatus;
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

        public static class CreateUserBean implements Parcelable {
            /**
             * avatar : {"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000}
             * nickname : 你好
             * sex : 0
             */

            private AvatarBean avatar;
            private String nickname;
            private int sex;
            private String userId;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

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

            public static class AvatarBean implements Parcelable {
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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.pictureId);
                    dest.writeString(this.picturePath);
                    dest.writeString(this.smallPicturePath);
                    dest.writeLong(this.timestamp);
                }

                public AvatarBean() {
                }

                protected AvatarBean(Parcel in) {
                    this.pictureId = in.readString();
                    this.picturePath = in.readString();
                    this.smallPicturePath = in.readString();
                    this.timestamp = in.readLong();
                }

                public static final Creator<AvatarBean> CREATOR = new Creator<AvatarBean>() {
                    @Override
                    public AvatarBean createFromParcel(Parcel source) {
                        return new AvatarBean(source);
                    }

                    @Override
                    public AvatarBean[] newArray(int size) {
                        return new AvatarBean[size];
                    }
                };
            }

            public CreateUserBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.avatar, flags);
                dest.writeString(this.nickname);
                dest.writeInt(this.sex);
                dest.writeString(this.userId);
            }

            protected CreateUserBean(Parcel in) {
                this.avatar = in.readParcelable(AvatarBean.class.getClassLoader());
                this.nickname = in.readString();
                this.sex = in.readInt();
                this.userId = in.readString();
            }

            public static final Creator<CreateUserBean> CREATOR = new Creator<CreateUserBean>() {
                @Override
                public CreateUserBean createFromParcel(Parcel source) {
                    return new CreateUserBean(source);
                }

                @Override
                public CreateUserBean[] newArray(int size) {
                    return new CreateUserBean[size];
                }
            };
        }

        public static class TopicBean implements Parcelable {
            /**
             * comment : 测试话题02
             * id : 2
             * topicIcon : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png
             * topicName : 测试话题01
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.comment);
                dest.writeInt(this.id);
                dest.writeString(this.topicIcon);
                dest.writeString(this.topicName);
            }

            public TopicBean() {
            }

            protected TopicBean(Parcel in) {
                this.comment = in.readString();
                this.id = in.readInt();
                this.topicIcon = in.readString();
                this.topicName = in.readString();
            }

            public static final Creator<TopicBean> CREATOR = new Creator<TopicBean>() {
                @Override
                public TopicBean createFromParcel(Parcel source) {
                    return new TopicBean(source);
                }

                @Override
                public TopicBean[] newArray(int size) {
                    return new TopicBean[size];
                }
            };
        }

        public static class ForumPictureListBean implements Parcelable {
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(this.fileSize);
                dest.writeString(this.filename);
                dest.writeString(this.height);
                dest.writeString(this.width);
            }

            public ForumPictureListBean() {
            }

            protected ForumPictureListBean(Parcel in) {
                this.fileSize = in.readLong();
                this.filename = in.readString();
                this.height = in.readString();
                this.width = in.readString();
            }

            public static final Creator<ForumPictureListBean> CREATOR = new Creator<ForumPictureListBean>() {
                @Override
                public ForumPictureListBean createFromParcel(Parcel source) {
                    return new ForumPictureListBean(source);
                }

                @Override
                public ForumPictureListBean[] newArray(int size) {
                    return new ForumPictureListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.browseNumber);
            dest.writeString(this.complaintId);
            dest.writeString(this.content);
            dest.writeString(this.createTime);
            dest.writeParcelable(this.createUser, flags);
            dest.writeInt(this.discussNum);
            dest.writeString(this.forumLabel);
            dest.writeString(this.forumNoteId);
            dest.writeString(this.howLong);
            dest.writeInt(this.noteSource);
            dest.writeInt(this.praiseNum);
            dest.writeString(this.praiseStatus);
            dest.writeString(this.processStatus);
            dest.writeString(this.smallCommunityCode);
            dest.writeString(this.title);
            dest.writeParcelable(this.topic, flags);
            dest.writeList(this.forumPictureList);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.browseNumber = in.readInt();
            this.complaintId = in.readString();
            this.content = in.readString();
            this.createTime = in.readString();
            this.createUser = in.readParcelable(CreateUserBean.class.getClassLoader());
            this.discussNum = in.readInt();
            this.forumLabel = in.readString();
            this.forumNoteId = in.readString();
            this.howLong = in.readString();
            this.noteSource = in.readInt();
            this.praiseNum = in.readInt();
            this.praiseStatus = in.readString();
            this.processStatus = in.readString();
            this.smallCommunityCode = in.readString();
            this.title = in.readString();
            this.topic = in.readParcelable(TopicBean.class.getClassLoader());
            this.forumPictureList = new ArrayList<ForumPictureListBean>();
            in.readList(this.forumPictureList, ForumPictureListBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
