package com.zhjl.qihao.nearbyinteraction.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class NearByNoteContentBean implements Parcelable {


    /**
     * code : 200
     * data : {"appendList":[{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"15820938530943b9cf2536b34a5b8bfc","discussNum":1,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"。你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"15820941832223b0bd651b5a474fa8c3","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"明天早上好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158209430669606f634660ad4f39b92e","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"今晚你好啊","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158209566291ca91601d548a4dcb9c03","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"我再发","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158209807260a1484f08eae043b8a0cd","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216554320c757ad08078245189885","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"明天好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"1582165573129d4c3e6df89e48a8929b","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"明天上班","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216558964d1644ced39994e0fa28a","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"晚上好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216610794b3eeff59ca79404e96c8","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216631294450f1c0e67b14856a943","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"晚上好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216665316dd2e890b12f84e43ac01","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"1582168100627c0f6cd315a24ef382b2","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216846564ab145050c19e4997b38a","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}}],"browseNumber":3,"content":"测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论","createTime":"2020-02-18 15:40","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":9,"forumLabel":"随手美拍","forumNoteId":"158201161243b683987631c14c5aab5b","forumPictureList":[{"fileSize":203346,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg","height":"2203.0","width":"1862.0"}],"howLong":"2天前","noteSource":0,"praiseNum":2,"praiseStatus":0,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}}
     * message : 处理成功
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

    public static class DataBean implements Parcelable {
        /**
         * appendList : [{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"15820938530943b9cf2536b34a5b8bfc","discussNum":1,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"。你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"15820941832223b0bd651b5a474fa8c3","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"明天早上好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158209430669606f634660ad4f39b92e","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"今晚你好啊","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158209566291ca91601d548a4dcb9c03","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"2天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"我再发","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158209807260a1484f08eae043b8a0cd","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216554320c757ad08078245189885","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"明天好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"1582165573129d4c3e6df89e48a8929b","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"明天上班","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216558964d1644ced39994e0fa28a","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"晚上好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216610794b3eeff59ca79404e96c8","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216631294450f1c0e67b14856a943","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"晚上好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216665316dd2e890b12f84e43ac01","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"1582168100627c0f6cd315a24ef382b2","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216846564ab145050c19e4997b38a","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}}]
         * browseNumber : 3
         * content : 测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论
         * createTime : 2020-02-18 15:40
         * createUser : {"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"}
         * discussNum : 9
         * forumLabel : 随手美拍
         * forumNoteId : 158201161243b683987631c14c5aab5b
         * forumPictureList : [{"fileSize":203346,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/picture_1582080321923.jpg","height":"2203.0","width":"1862.0"}]
         * howLong : 2天前
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
        private long complaintId;
        private String howLong;
        private int noteSource;
        private int praiseNum;
        private int praiseStatus;
        private String smallCommunityCode;
        private String title;
        private TopicBean topic;
        private List<AppendListBean> appendList;
        private List<ForumPictureListBean> forumPictureList;

        public long getComplaintId() {
            return complaintId;
        }

        public void setComplaintId(long complaintId) {
            this.complaintId = complaintId;
        }

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

        public List<AppendListBean> getAppendList() {
            return appendList;
        }

        public void setAppendList(List<AppendListBean> appendList) {
            this.appendList = appendList;
        }

        public List<ForumPictureListBean> getForumPictureList() {
            return forumPictureList;
        }

        public void setForumPictureList(List<ForumPictureListBean> forumPictureList) {
            this.forumPictureList = forumPictureList;
        }

        public static class CreateUserBean implements Parcelable {
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

            public static class AvatarBean implements Parcelable {
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

                public static final Parcelable.Creator<AvatarBean> CREATOR = new Parcelable.Creator<AvatarBean>() {
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

            public CreateUserBean() {
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

        public static class AppendListBean implements Parcelable {
            /**
             * content : 你好呀
             * createUserInfo : {"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"}
             * discussId : 15820938530943b9cf2536b34a5b8bfc
             * discussNum : 1
             * forumNoteId : 158201161243b683987631c14c5aab5b
             * howLong : 2天前
             * praiseNum : 0
             * praiseStatus : 0
             * replyUserInfo : null
             */

            private String content;
            private CreateUserInfoBean createUserInfo;
            private String discussId;
            private int discussNum;
            private String forumNoteId;
            private String howLong;
            private int praiseNum;
            private int praiseStatus;
            private CreateUserInfoBean replyUserInfo;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public CreateUserInfoBean getCreateUserInfo() {
                return createUserInfo;
            }

            public void setCreateUserInfo(CreateUserInfoBean createUserInfo) {
                this.createUserInfo = createUserInfo;
            }

            public String getDiscussId() {
                return discussId;
            }

            public void setDiscussId(String discussId) {
                this.discussId = discussId;
            }

            public int getDiscussNum() {
                return discussNum;
            }

            public void setDiscussNum(int discussNum) {
                this.discussNum = discussNum;
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

            public CreateUserInfoBean getReplyUserInfo() {
                return replyUserInfo;
            }

            public void setReplyUserInfo(CreateUserInfoBean replyUserInfo) {
                this.replyUserInfo = replyUserInfo;
            }

            public static class CreateUserInfoBean implements Parcelable {
                /**
                 * avatar : null
                 * nickname : Pl47cP80kQV
                 * sex : 2
                 * sign : 在忙也不要忘记买菜哦～
                 * userId : 157585787506668804efc3fd45d89688
                 */

                private CreateUserBean.AvatarBean avatar;
                private String nickname;
                private int sex;
                private String sign;
                private String userId;

                public CreateUserBean.AvatarBean getAvatar() {
                    return avatar;
                }

                public void setAvatar(CreateUserBean.AvatarBean avatar) {
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

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
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
                    dest.writeString(this.sign);
                    dest.writeString(this.userId);
                }

                public CreateUserInfoBean() {
                }

                protected CreateUserInfoBean(Parcel in) {
                    this.avatar = in.readParcelable(CreateUserBean.AvatarBean.class.getClassLoader());
                    this.nickname = in.readString();
                    this.sex = in.readInt();
                    this.sign = in.readString();
                    this.userId = in.readString();
                }

                public static final Parcelable.Creator<CreateUserInfoBean> CREATOR = new Parcelable.Creator<CreateUserInfoBean>() {
                    @Override
                    public CreateUserInfoBean createFromParcel(Parcel source) {
                        return new CreateUserInfoBean(source);
                    }

                    @Override
                    public CreateUserInfoBean[] newArray(int size) {
                        return new CreateUserInfoBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.content);
                dest.writeParcelable(this.createUserInfo, flags);
                dest.writeString(this.discussId);
                dest.writeInt(this.discussNum);
                dest.writeString(this.forumNoteId);
                dest.writeString(this.howLong);
                dest.writeInt(this.praiseNum);
                dest.writeInt(this.praiseStatus);
                dest.writeParcelable(this.replyUserInfo, flags);
            }

            public AppendListBean() {
            }

            protected AppendListBean(Parcel in) {
                this.content = in.readString();
                this.createUserInfo = in.readParcelable(CreateUserInfoBean.class.getClassLoader());
                this.discussId = in.readString();
                this.discussNum = in.readInt();
                this.forumNoteId = in.readString();
                this.howLong = in.readString();
                this.praiseNum = in.readInt();
                this.praiseStatus = in.readInt();
                this.replyUserInfo = in.readParcelable(CreateUserInfoBean.class.getClassLoader());
            }

            public static final Creator<AppendListBean> CREATOR = new Creator<AppendListBean>() {
                @Override
                public AppendListBean createFromParcel(Parcel source) {
                    return new AppendListBean(source);
                }

                @Override
                public AppendListBean[] newArray(int size) {
                    return new AppendListBean[size];
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.fileSize);
                dest.writeString(this.filename);
                dest.writeString(this.height);
                dest.writeString(this.width);
            }

            public ForumPictureListBean() {
            }

            protected ForumPictureListBean(Parcel in) {
                this.fileSize = in.readInt();
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

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.browseNumber);
            dest.writeString(this.content);
            dest.writeString(this.createTime);
            dest.writeParcelable(this.createUser, flags);
            dest.writeInt(this.discussNum);
            dest.writeString(this.forumLabel);
            dest.writeString(this.forumNoteId);
            dest.writeLong(this.complaintId);
            dest.writeString(this.howLong);
            dest.writeInt(this.noteSource);
            dest.writeInt(this.praiseNum);
            dest.writeInt(this.praiseStatus);
            dest.writeString(this.smallCommunityCode);
            dest.writeString(this.title);
            dest.writeParcelable(this.topic, flags);
            dest.writeTypedList(this.appendList);
            dest.writeTypedList(this.forumPictureList);
        }

        protected DataBean(Parcel in) {
            this.browseNumber = in.readInt();
            this.content = in.readString();
            this.createTime = in.readString();
            this.createUser = in.readParcelable(CreateUserBean.class.getClassLoader());
            this.discussNum = in.readInt();
            this.forumLabel = in.readString();
            this.forumNoteId = in.readString();
            this.complaintId = in.readLong();
            this.howLong = in.readString();
            this.noteSource = in.readInt();
            this.praiseNum = in.readInt();
            this.praiseStatus = in.readInt();
            this.smallCommunityCode = in.readString();
            this.title = in.readString();
            this.topic = in.readParcelable(TopicBean.class.getClassLoader());
            this.appendList = in.createTypedArrayList(AppendListBean.CREATOR);
            this.forumPictureList = in.createTypedArrayList(ForumPictureListBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    public NearByNoteContentBean() {
    }

    protected NearByNoteContentBean(Parcel in) {
        this.code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<NearByNoteContentBean> CREATOR = new Parcelable.Creator<NearByNoteContentBean>() {
        @Override
        public NearByNoteContentBean createFromParcel(Parcel source) {
            return new NearByNoteContentBean(source);
        }

        @Override
        public NearByNoteContentBean[] newArray(int size) {
            return new NearByNoteContentBean[size];
        }
    };
}
