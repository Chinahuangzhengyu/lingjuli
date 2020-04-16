package com.zhjl.qihao.nearbyinteraction.bean;

import java.util.List;

public class NoteReplyListBean {

    /**
     * code : 200
     * data : [{"content":"你好呀","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"158216846564ab145050c19e4997b38a","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"6天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}},{"content":"你好","createUserInfo":{"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"},"discussId":"1582168100627c0f6cd315a24ef382b2","discussNum":0,"forumNoteId":"158201161243b683987631c14c5aab5b","howLong":"6天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}}]
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
         * content : 你好呀
         * createUserInfo : {"avatar":null,"nickname":"Pl47cP80kQV","sex":2,"sign":"在忙也不要忘记买菜哦～","userId":"157585787506668804efc3fd45d89688"}
         * discussId : 158216846564ab145050c19e4997b38a
         * discussNum : 0
         * forumNoteId : 158201161243b683987631c14c5aab5b
         * howLong : 6天前
         * praiseNum : 0
         * praiseStatus : 0
         * replyUserInfo : {"avatar":null,"nickname":"n4W82i12C7m","sex":2,"sign":"再忙也不要忘记买菜哦～","userId":"1579503854544e52ed4068904af695b3"}
         */

        private String content;
        private CreateUserInfoBean createUserInfo;
        private String discussId;
        private int discussNum;
        private String forumNoteId;
        private String howLong;
        private int praiseNum;
        private int praiseStatus;
        private ReplyUserInfoBean replyUserInfo;


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

        public ReplyUserInfoBean getReplyUserInfo() {
            return replyUserInfo;
        }

        public void setReplyUserInfo(ReplyUserInfoBean replyUserInfo) {
            this.replyUserInfo = replyUserInfo;
        }

        public static class CreateUserInfoBean {
            /**
             * avatar : null
             * nickname : Pl47cP80kQV
             * sex : 2
             * sign : 在忙也不要忘记买菜哦～
             * userId : 157585787506668804efc3fd45d89688
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
        }

        public static class ReplyUserInfoBean {
            /**
             * avatar : null
             * nickname : n4W82i12C7m
             * sex : 2
             * sign : 再忙也不要忘记买菜哦～
             * userId : 1579503854544e52ed4068904af695b3
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
}
