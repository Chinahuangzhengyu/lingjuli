package com.zhjl.qihao.myaction.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteContentBean;

import java.util.ArrayList;
import java.util.List;

public class MyReplyActionBean {

    /**
     * code : 200
     * data : [{"content":"哈哈","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"1575085515134e6aeba51f9f4c1391c6","discussNum":0,"forumNoteDetailVO":{"appendList":[],"browseNumber":0,"content":"路路通","createTime":"2019-11-30 03:22","createUser":{"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"},"discussNum":1,"forumLabel":"生活杂谈","forumNoteId":"15750841210308a289172f4d40b3873b","forumPictureList":[],"howLong":"83天前","noteSource":0,"praiseNum":0,"praiseStatus":null,"smallCommunityCode":"0002001","title":"图片","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},"forumNoteId":"15750841210308a289172f4d40b3873b","howLong":"83天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"哈哈","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"157525868484013e0f807a7647698b9e","discussNum":0,"forumNoteDetailVO":{"appendList":[],"browseNumber":0,"content":"测试帖子$","createTime":"2019-11-30 10:18","createUser":{"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"},"discussNum":5,"forumLabel":"随手美拍","forumNoteId":"15751091381108150ba8263646fd843d","forumPictureList":[],"howLong":"83天前","noteSource":0,"praiseNum":1,"praiseStatus":null,"smallCommunityCode":"0044001","title":"测试发帖","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},"forumNoteId":"15751091381108150ba8263646fd843d","howLong":"81天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"哈哈","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"15765506485926ba90401a244130b407","discussNum":0,"forumNoteDetailVO":{"appendList":[],"browseNumber":1,"content":"测试发布帖子","createTime":"2019-12-10 00:43","createUser":{"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"},"discussNum":4,"forumLabel":"随手美拍","forumNoteId":"157593862261804ee8b5c17a4610a8f6","forumPictureList":[],"howLong":"73天前","noteSource":0,"praiseNum":2,"praiseStatus":null,"smallCommunityCode":"0044001","title":"测试","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},"forumNoteId":"157593862261804ee8b5c17a4610a8f6","howLong":"66天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"嘿嘿","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"1582269360651e8fb16dcdc14b42a9f2","discussNum":0,"forumNoteDetailVO":{"appendList":[],"browseNumber":3,"content":"测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论","createTime":"2020-02-18 15:40","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":17,"forumLabel":"随手美拍","forumNoteId":"158201161243b683987631c14c5aab5b","forumPictureList":[],"howLong":"3天前","noteSource":0,"praiseNum":2,"praiseStatus":null,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1小时前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"测试评论","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"15822718677349e60374bcfd443f8bd1","discussNum":0,"forumNoteDetailVO":{"appendList":[],"browseNumber":3,"content":"测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论","createTime":"2020-02-18 15:40","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":17,"forumLabel":"随手美拍","forumNoteId":"158201161243b683987631c14c5aab5b","forumPictureList":[],"howLong":"3天前","noteSource":0,"praiseNum":2,"praiseStatus":null,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1小时前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"嘿嘿","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"15822721181070a477bcfad2496081a8","discussNum":1,"forumNoteDetailVO":{"appendList":[],"browseNumber":3,"content":"测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论","createTime":"2020-02-18 15:40","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":17,"forumLabel":"随手美拍","forumNoteId":"158201161243b683987631c14c5aab5b","forumPictureList":[],"howLong":"3天前","noteSource":0,"praiseNum":2,"praiseStatus":null,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1小时前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"哈哈","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"15822752681177ec9b04531d42e496b1","discussNum":0,"forumNoteDetailVO":{"appendList":[],"browseNumber":3,"content":"测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论测试测试长篇大论","createTime":"2020-02-18 15:40","createUser":{"avatar":{"pictureId":"","picturePath":"","smallPicturePath":"","timestamp":0},"nickname":"Pl47cP80kQV","sex":2,"userId":"157585787506668804efc3fd45d89688"},"discussNum":17,"forumLabel":"随手美拍","forumNoteId":"158201161243b683987631c14c5aab5b","forumPictureList":[],"howLong":"3天前","noteSource":0,"praiseNum":2,"praiseStatus":null,"smallCommunityCode":"0044001","title":"","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}},"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"13分前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null}]
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
         * content : 哈哈
         * createUserInfo : {"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"}
         * discussId : 1575085515134e6aeba51f9f4c1391c6
         * discussNum : 0
         * forumNoteDetailVO : {"appendList":[],"browseNumber":0,"content":"路路通","createTime":"2019-11-30 03:22","createUser":{"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"},"discussNum":1,"forumLabel":"生活杂谈","forumNoteId":"15750841210308a289172f4d40b3873b","forumPictureList":[],"howLong":"83天前","noteSource":0,"praiseNum":0,"praiseStatus":null,"smallCommunityCode":"0002001","title":"图片","topic":{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"}}
         * forumNoteId : 15750841210308a289172f4d40b3873b
         * howLong : 83天前
         * praiseNum : 0
         * praiseStatus : 0
         * replyUserInfo : null
         */

        private String content;
        private CreateUserInfoBean createUserInfo;
        private String discussId;
        private String replyDiscussId;
        private int discussNum;
        private ForumNoteDetailVOBean forumNoteDetailVO;
        private String forumNoteId;
        private String howLong;
        private int praiseNum;
        private int praiseStatus;
        private int newReply;
        private ReplyUserInfoBean replyUserInfo;

        public String getReplyDiscussId() {
            return replyDiscussId;
        }

        public void setReplyDiscussId(String replyDiscussId) {
            this.replyDiscussId = replyDiscussId;
        }

        public int getNewReply() {
            return newReply;
        }

        public void setNewReply(int newReply) {
            this.newReply = newReply;
        }

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

        public ForumNoteDetailVOBean getForumNoteDetailVO() {
            return forumNoteDetailVO;
        }

        public void setForumNoteDetailVO(ForumNoteDetailVOBean forumNoteDetailVO) {
            this.forumNoteDetailVO = forumNoteDetailVO;
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

        public ReplyUserInfoBean getReplyUserInfo() {
            return replyUserInfo;
        }

        public void setReplyUserInfo(ReplyUserInfoBean replyUserInfo) {
            this.replyUserInfo = replyUserInfo;
        }

        public static class CreateUserInfoBean implements Parcelable {
            /**
             * avatar : {"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000}
             * nickname : 你好
             * sex : 0
             * sign : 我只在乎十年后的自己，怎么看现在的我！
             * userId : 15220328700869de92519083445f881d
             */

            private AvatarBean avatar;
            private String nickname;
            private int sex;
            private String sign;
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
                this.avatar = in.readParcelable(AvatarBean.class.getClassLoader());
                this.nickname = in.readString();
                this.sex = in.readInt();
                this.sign = in.readString();
                this.userId = in.readString();
            }

            public static final Creator<CreateUserInfoBean> CREATOR = new Creator<CreateUserInfoBean>() {
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
        public static class ReplyUserInfoBean implements Parcelable {
            /**
             * avatar : {"picturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg"}
             * nickName : 你好
             * sex : 0
             */

            private AvatarBeanX avatar;
            private String nickName;
            private int sex;

            public AvatarBeanX getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBeanX avatar) {
                this.avatar = avatar;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public static class AvatarBeanX implements Parcelable {
                /**
                 * picturePath : http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg
                 * samllPicturePath : http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg
                 */

                private String picturePath;
                private String samllPicturePath;

                public String getPicturePath() {
                    return picturePath;
                }

                public void setPicturePath(String picturePath) {
                    this.picturePath = picturePath;
                }

                public String getSamllPicturePath() {
                    return samllPicturePath;
                }

                public void setSamllPicturePath(String samllPicturePath) {
                    this.samllPicturePath = samllPicturePath;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.picturePath);
                    dest.writeString(this.samllPicturePath);
                }

                public AvatarBeanX() {
                }

                protected AvatarBeanX(Parcel in) {
                    this.picturePath = in.readString();
                    this.samllPicturePath = in.readString();
                }

                public static final Creator<AvatarBeanX> CREATOR = new Creator<AvatarBeanX>() {
                    @Override
                    public AvatarBeanX createFromParcel(Parcel source) {
                        return new AvatarBeanX(source);
                    }

                    @Override
                    public AvatarBeanX[] newArray(int size) {
                        return new AvatarBeanX[size];
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
                dest.writeString(this.nickName);
                dest.writeInt(this.sex);
            }

            public ReplyUserInfoBean() {
            }

            protected ReplyUserInfoBean(Parcel in) {
                this.avatar = in.readParcelable(AvatarBeanX.class.getClassLoader());
                this.nickName = in.readString();
                this.sex = in.readInt();
            }

            public static final Creator<ReplyUserInfoBean> CREATOR = new Creator<ReplyUserInfoBean>() {
                @Override
                public ReplyUserInfoBean createFromParcel(Parcel source) {
                    return new ReplyUserInfoBean(source);
                }

                @Override
                public ReplyUserInfoBean[] newArray(int size) {
                    return new ReplyUserInfoBean[size];
                }
            };
        }

        public static class ForumNoteDetailVOBean implements Parcelable {
            /**
             * appendList : []
             * browseNumber : 0
             * content : 路路通
             * createTime : 2019-11-30 03:22
             * createUser : {"avatar":{"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000},"nickname":"你好,明天","sex":0,"userId":"156637648572fd40c9058ee54b2f9c36"}
             * discussNum : 1
             * forumLabel : 生活杂谈
             * forumNoteId : 15750841210308a289172f4d40b3873b
             * forumPictureList : []
             * howLong : 83天前
             * noteSource : 0
             * praiseNum : 0
             * praiseStatus : null
             * smallCommunityCode : 0002001
             * title : 图片
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
            private String praiseStatus;
            private String smallCommunityCode;
            private String title;
            private TopicBean topic;
            private List<NearByNoteContentBean.DataBean.AppendListBean> appendList;
            private List<NearByNoteContentBean.DataBean.ForumPictureListBean> forumPictureList;

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

            public String getPraiseStatus() {
                return praiseStatus;
            }

            public void setPraiseStatus(String praiseStatus) {
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

            public List<NearByNoteContentBean.DataBean.AppendListBean> getAppendList() {
                return appendList;
            }

            public void setAppendList(List<NearByNoteContentBean.DataBean.AppendListBean> appendList) {
                this.appendList = appendList;
            }

            public List<NearByNoteContentBean.DataBean.ForumPictureListBean> getForumPictureList() {
                return forumPictureList;
            }

            public void setForumPictureList(List<NearByNoteContentBean.DataBean.ForumPictureListBean> forumPictureList) {
                this.forumPictureList = forumPictureList;
            }



            public static class CreateUserBean implements Parcelable {
                /**
                 * avatar : {"pictureId":"15760353316807dc58ddbfd74da1b23d","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg","timestamp":1576035332000}
                 * nickname : 你好,明天
                 * sex : 0
                 * userId : 156637648572fd40c9058ee54b2f9c36
                 */

                private AvatarBeanX avatar;
                private String nickname;
                private int sex;
                private String userId;

                public AvatarBeanX getAvatar() {
                    return avatar;
                }

                public void setAvatar(AvatarBeanX avatar) {
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

                public static class AvatarBeanX implements Parcelable {
                    /**
                     * pictureId : 15760353316807dc58ddbfd74da1b23d
                     * picturePath : http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd.jpg
                     * smallPicturePath : http://47.105.215.157:8080/psms/upload/2019-12-11/c186819d0c1447608830c4f1c7b4fadd_small.jpg
                     * timestamp : 1576035332000
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

                    public AvatarBeanX() {
                    }

                    protected AvatarBeanX(Parcel in) {
                        this.pictureId = in.readString();
                        this.picturePath = in.readString();
                        this.smallPicturePath = in.readString();
                        this.timestamp = in.readLong();
                    }

                    public static final Creator<AvatarBeanX> CREATOR = new Creator<AvatarBeanX>() {
                        @Override
                        public AvatarBeanX createFromParcel(Parcel source) {
                            return new AvatarBeanX(source);
                        }

                        @Override
                        public AvatarBeanX[] newArray(int size) {
                            return new AvatarBeanX[size];
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
                    this.avatar = in.readParcelable(AvatarBeanX.class.getClassLoader());
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

            public ForumNoteDetailVOBean() {
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
                dest.writeString(this.howLong);
                dest.writeInt(this.noteSource);
                dest.writeInt(this.praiseNum);
                dest.writeString(this.praiseStatus);
                dest.writeString(this.smallCommunityCode);
                dest.writeString(this.title);
                dest.writeParcelable(this.topic, flags);
                dest.writeList(this.appendList);
                dest.writeList(this.forumPictureList);
            }

            protected ForumNoteDetailVOBean(Parcel in) {
                this.browseNumber = in.readInt();
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
                this.smallCommunityCode = in.readString();
                this.title = in.readString();
                this.topic = in.readParcelable(TopicBean.class.getClassLoader());
                this.appendList = new ArrayList<NearByNoteContentBean.DataBean.AppendListBean>();
                in.readList(this.appendList, NearByNoteContentBean.DataBean.AppendListBean.class.getClassLoader());
                this.forumPictureList = new ArrayList<NearByNoteContentBean.DataBean.ForumPictureListBean>();
                in.readList(this.forumPictureList, NearByNoteContentBean.DataBean.ForumPictureListBean.class.getClassLoader());
            }

            public static final Creator<ForumNoteDetailVOBean> CREATOR = new Creator<ForumNoteDetailVOBean>() {
                @Override
                public ForumNoteDetailVOBean createFromParcel(Parcel source) {
                    return new ForumNoteDetailVOBean(source);
                }

                @Override
                public ForumNoteDetailVOBean[] newArray(int size) {
                    return new ForumNoteDetailVOBean[size];
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
            dest.writeString(this.content);
            dest.writeParcelable(this.createUserInfo, flags);
            dest.writeString(this.discussId);
            dest.writeString(this.replyDiscussId);
            dest.writeInt(this.discussNum);
            dest.writeParcelable(this.forumNoteDetailVO, flags);
            dest.writeString(this.forumNoteId);
            dest.writeString(this.howLong);
            dest.writeInt(this.praiseNum);
            dest.writeInt(this.praiseStatus);
            dest.writeInt(this.newReply);
            dest.writeParcelable(this.replyUserInfo, flags);
        }

        protected DataBean(Parcel in) {
            this.content = in.readString();
            this.createUserInfo = in.readParcelable(CreateUserInfoBean.class.getClassLoader());
            this.discussId = in.readString();
            this.replyDiscussId = in.readString();
            this.discussNum = in.readInt();
            this.forumNoteDetailVO = in.readParcelable(ForumNoteDetailVOBean.class.getClassLoader());
            this.forumNoteId = in.readString();
            this.howLong = in.readString();
            this.praiseNum = in.readInt();
            this.praiseStatus = in.readInt();
            this.newReply = in.readInt();
            this.replyUserInfo = in.readParcelable(ReplyUserInfoBean.class.getClassLoader());
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
}
