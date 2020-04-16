package com.zhjl.qihao.nearbyinteraction.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NearByNoteReplyContentBean {

    /**
     * code : 200
     * data : [{"content":"嘿嘿","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"15822721181070a477bcfad2496081a8","discussNum":1,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"16分前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"测试评论","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"15822718677349e60374bcfd443f8bd1","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"20分前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"嘿嘿","createUserInfo":{"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"},"discussId":"1582269360651e8fb16dcdc14b42a9f2","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1小时前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"},"discussId":"158216804816a6ce00af9d5c4014a203","discussNum":1,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"你好","createUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"},"discussId":"158216498806b39891ac6aa54408a6ef","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"杨奎你好","createUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"},"discussId":"15821642405497fff778601b47bcb8a4","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null},{"content":"杨奎你好","createUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"},"discussId":"15821642083067ed1060962246a5a4c3","discussNum":2,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"1天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":null}]
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
         * content : 嘿嘿
         * createUserInfo : {"avatar":{"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000},"nickname":"你好","sex":0,"sign":"我只在乎十年后的自己，怎么看现在的我！","userId":"15220328700869de92519083445f881d"}
         * discussId : 15822721181070a477bcfad2496081a8
         * discussNum : 1
         * forumNoteId : 158201161243b683987631c14c5aab5b
         * howLong : 16分前
         * praiseNum : 0
         * praiseStatus : 0
         * replyUserInfo : null
         */

        private String content;
        private NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean createUserInfo;
        private String discussId;
        private int discussNum;
        private String forumNoteId;
        private String howLong;
        private int praiseNum;
        private int praiseStatus;
        private NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean replyUserInfo;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean getCreateUserInfo() {
            return createUserInfo;
        }

        public void setCreateUserInfo(NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean createUserInfo) {
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

        public NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean getReplyUserInfo() {
            return replyUserInfo;
        }

        public void setReplyUserInfo(NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean replyUserInfo) {
            this.replyUserInfo = replyUserInfo;
        }

//        public static class CreateUserInfoBean implements Parcelable {
//            /**
//             * avatar : {"pictureId":"157622613729c686413a1611456da243","picturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg","timestamp":1576226137000}
//             * nickname : 你好
//             * sex : 0
//             * sign : 我只在乎十年后的自己，怎么看现在的我！
//             * userId : 15220328700869de92519083445f881d
//             */
//
//            private AvatarBean avatar;
//            private String nickname;
//            private int sex;
//            private String sign;
//            private String userId;
//
//            public AvatarBean getAvatar() {
//                return avatar;
//            }
//
//            public void setAvatar(AvatarBean avatar) {
//                this.avatar = avatar;
//            }
//
//            public String getNickname() {
//                return nickname;
//            }
//
//            public void setNickname(String nickname) {
//                this.nickname = nickname;
//            }
//
//            public int getSex() {
//                return sex;
//            }
//
//            public void setSex(int sex) {
//                this.sex = sex;
//            }
//
//            public String getSign() {
//                return sign;
//            }
//
//            public void setSign(String sign) {
//                this.sign = sign;
//            }
//
//            public String getUserId() {
//                return userId;
//            }
//
//            public void setUserId(String userId) {
//                this.userId = userId;
//            }
//
//            public static class AvatarBean implements Parcelable {
//                /**
//                 * pictureId : 157622613729c686413a1611456da243
//                 * picturePath : http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e.jpg
//                 * smallPicturePath : http://47.105.215.157:8080/psms/upload/2019-12-13/809a535a1eca4627a335ee8cf046715e_small.jpg
//                 * timestamp : 1576226137000
//                 */
//
//                private String pictureId;
//                private String picturePath;
//                private String smallPicturePath;
//                private long timestamp;
//
//                public String getPictureId() {
//                    return pictureId;
//                }
//
//                public void setPictureId(String pictureId) {
//                    this.pictureId = pictureId;
//                }
//
//                public String getPicturePath() {
//                    return picturePath;
//                }
//
//                public void setPicturePath(String picturePath) {
//                    this.picturePath = picturePath;
//                }
//
//                public String getSmallPicturePath() {
//                    return smallPicturePath;
//                }
//
//                public void setSmallPicturePath(String smallPicturePath) {
//                    this.smallPicturePath = smallPicturePath;
//                }
//
//                public long getTimestamp() {
//                    return timestamp;
//                }
//
//                public void setTimestamp(long timestamp) {
//                    this.timestamp = timestamp;
//                }
//
//                @Override
//                public int describeContents() {
//                    return 0;
//                }
//
//                @Override
//                public void writeToParcel(Parcel dest, int flags) {
//                    dest.writeString(this.pictureId);
//                    dest.writeString(this.picturePath);
//                    dest.writeString(this.smallPicturePath);
//                    dest.writeLong(this.timestamp);
//                }
//
//                public AvatarBean() {
//                }
//
//                protected AvatarBean(Parcel in) {
//                    this.pictureId = in.readString();
//                    this.picturePath = in.readString();
//                    this.smallPicturePath = in.readString();
//                    this.timestamp = in.readLong();
//                }
//
//                public static final Creator<AvatarBean> CREATOR = new Creator<AvatarBean>() {
//                    @Override
//                    public AvatarBean createFromParcel(Parcel source) {
//                        return new AvatarBean(source);
//                    }
//
//                    @Override
//                    public AvatarBean[] newArray(int size) {
//                        return new AvatarBean[size];
//                    }
//                };
//            }
//
//            @Override
//            public int describeContents() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel dest, int flags) {
//                dest.writeParcelable(this.avatar, flags);
//                dest.writeString(this.nickname);
//                dest.writeInt(this.sex);
//                dest.writeString(this.sign);
//                dest.writeString(this.userId);
//            }
//
//            public CreateUserInfoBean() {
//            }
//
//            protected CreateUserInfoBean(Parcel in) {
//                this.avatar = in.readParcelable(AvatarBean.class.getClassLoader());
//                this.nickname = in.readString();
//                this.sex = in.readInt();
//                this.sign = in.readString();
//                this.userId = in.readString();
//            }
//
//            public static final Creator<CreateUserInfoBean> CREATOR = new Creator<CreateUserInfoBean>() {
//                @Override
//                public CreateUserInfoBean createFromParcel(Parcel source) {
//                    return new CreateUserInfoBean(source);
//                }
//
//                @Override
//                public CreateUserInfoBean[] newArray(int size) {
//                    return new CreateUserInfoBean[size];
//                }
//            };
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(this.content);
//            dest.writeParcelable(this.createUserInfo, flags);
//            dest.writeString(this.discussId);
//            dest.writeInt(this.discussNum);
//            dest.writeString(this.forumNoteId);
//            dest.writeString(this.howLong);
//            dest.writeInt(this.praiseNum);
//            dest.writeInt(this.praiseStatus);
//            dest.writeParcelable(this.replyUserInfo, flags);
//        }
//
//        public DataBean() {
//        }
//
//        protected DataBean(Parcel in) {
//            this.content = in.readString();
//            this.createUserInfo = in.readParcelable(CreateUserInfoBean.class.getClassLoader());
//            this.discussId = in.readString();
//            this.discussNum = in.readInt();
//            this.forumNoteId = in.readString();
//            this.howLong = in.readString();
//            this.praiseNum = in.readInt();
//            this.praiseStatus = in.readInt();
//            this.replyUserInfo = in.readParcelable(CreateUserInfoBean.class.getClassLoader());
//        }
//
//        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
//            @Override
//            public DataBean createFromParcel(Parcel source) {
//                return new DataBean(source);
//            }
//
//            @Override
//            public DataBean[] newArray(int size) {
//                return new DataBean[size];
//            }
//        };

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

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.content = in.readString();
            this.createUserInfo = in.readParcelable(NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean.class.getClassLoader());
            this.discussId = in.readString();
            this.discussNum = in.readInt();
            this.forumNoteId = in.readString();
            this.howLong = in.readString();
            this.praiseNum = in.readInt();
            this.praiseStatus = in.readInt();
            this.replyUserInfo = in.readParcelable(NearByNoteContentBean.DataBean.AppendListBean.CreateUserInfoBean.class.getClassLoader());
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
