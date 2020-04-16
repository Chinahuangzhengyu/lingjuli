package com.zhjl.qihao.complaint.bean;

import java.util.List;

public class CommentHistoryRecordBean {


    /**
     * code : 200
     * data : [{"children":[],"content":"你好","createTime":"2019-09-27 01:51:35","createUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"},"nickName":"天天开心","sex":0},"discussId":"1569491495975479b410df3f4813a424","discussNum":0,"howLong":"22天前","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg"},"nickName":"你好","sex":0},"replyUserType":0}]
     * message : 获取数据列表成功
     * totalPage : 1
     */

    private int code;
    private String message;
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
         * children : []
         * content : 你好
         * createTime : 2019-09-27 01:51:35
         * createUserInfo : {"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"},"nickName":"天天开心","sex":0}
         * discussId : 1569491495975479b410df3f4813a424
         * discussNum : 0
         * howLong : 22天前
         * praiseNum : 0
         * praiseStatus : 0
         * replyUserInfo : {"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg"},"nickName":"你好","sex":0}
         * replyUserType : 0
         */

        private String content;
        private String createTime;
        private CreateUserInfoBean createUserInfo;
        private String discussId;
        private int discussNum;
        private String howLong;
        private int praiseNum;
        private int praiseStatus;
        private ReplyUserInfoBean replyUserInfo;
        private int replyUserType;
        private List<?> children;

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

        public int getReplyUserType() {
            return replyUserType;
        }

        public void setReplyUserType(int replyUserType) {
            this.replyUserType = replyUserType;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }

        public static class CreateUserInfoBean {
            /**
             * avatar : {"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"}
             * nickName : 天天开心
             * sex : 0
             */

            private AvatarBean avatar;
            private String nickName;
            private int sex;

            public AvatarBean getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBean avatar) {
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

            public static class AvatarBean {
                /**
                 * picturePath : http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg
                 * samllPicturePath : http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg
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
            }
        }

        public static class ReplyUserInfoBean {
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

            public static class AvatarBeanX {
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
            }
        }
    }
}
