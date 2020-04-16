package com.zhjl.qihao.complaint.bean;

import java.util.List;

public class ComplaintDetailReplyBean {


    /**
     * code : 200
     * data : [{"children":[{"children":[],"content":"测试回复讨论","createTime":"2019-10-18 17:05:16","createUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"},"nickName":"天天开心","sex":0,"sign":""},"createUserType":0,"discussId":"157138951617ca4f11d4eade4c28990f","discussNum":0,"praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"},"nickName":"黄佛丁","sex":0,"sign":""},"replyUserType":0}],"content":"的确好","createTime":"2018-06-08 23:57:33","createUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"},"nickName":"黄佛丁","sex":0,"sign":""},"createUserType":0,"discussId":"152844465302f0b39a27fdd244629912","discussNum":1,"forumNoteId":"15013948411993be54cff7794c76b27b","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a_small.jpg"},"nickName":"小智","sex":1,"sign":""},"replyUserType":0,"title":"铭基创意园环境真好","typeFk":"1"},{"children":[],"content":"想看看这两年我都有自己独特魅力、一起的时间过长会很辛苦\u2026\u2026不想让人看到这个我觉得这个人的眼睛","createTime":"2018-06-08 23:57:46","createUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"},"nickName":"黄佛丁","sex":0,"sign":""},"createUserType":0,"discussId":"15284446664280750a4ef0be438f8c84","discussNum":0,"forumNoteId":"15013948411993be54cff7794c76b27b","praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a_small.jpg"},"nickName":"小智","sex":1,"sign":""},"replyUserType":0,"title":"铭基创意园环境真好","typeFk":"1"}]
     * message : 获取数据列表成功
     * totalPage : 2
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
         * children : [{"children":[],"content":"测试回复讨论","createTime":"2019-10-18 17:05:16","createUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"},"nickName":"天天开心","sex":0,"sign":""},"createUserType":0,"discussId":"157138951617ca4f11d4eade4c28990f","discussNum":0,"praiseNum":0,"praiseStatus":0,"replyUserInfo":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"},"nickName":"黄佛丁","sex":0,"sign":""},"replyUserType":0}]
         * content : 的确好
         * createTime : 2018-06-08 23:57:33
         * createUserInfo : {"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"},"nickName":"黄佛丁","sex":0,"sign":""}
         * createUserType : 0
         * discussId : 152844465302f0b39a27fdd244629912
         * discussNum : 1
         * forumNoteId : 15013948411993be54cff7794c76b27b
         * praiseNum : 0
         * praiseStatus : 0
         * replyUserInfo : {"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a_small.jpg"},"nickName":"小智","sex":1,"sign":""}
         * replyUserType : 0
         * title : 铭基创意园环境真好
         * typeFk : 1
         */

        private String content;
        private String createTime;
        private CreateUserInfoBean createUserInfo;
        private int createUserType;
        private String discussId;
        private int discussNum;
        private String forumNoteId;
        private int praiseNum;
        private int praiseStatus;
        private ReplyUserInfoBean replyUserInfo;
        private int replyUserType;
        private String title;
        private String typeFk;
        private String howLong;
        private List<ChildrenBean> children;

        public String getHowLong() {
            return howLong;
        }

        public void setHowLong(String howLong) {
            this.howLong = howLong;
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

        public CreateUserInfoBean getCreateUserInfo() {
            return createUserInfo;
        }

        public void setCreateUserInfo(CreateUserInfoBean createUserInfo) {
            this.createUserInfo = createUserInfo;
        }

        public int getCreateUserType() {
            return createUserType;
        }

        public void setCreateUserType(int createUserType) {
            this.createUserType = createUserType;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTypeFk() {
            return typeFk;
        }

        public void setTypeFk(String typeFk) {
            this.typeFk = typeFk;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class CreateUserInfoBean {
            /**
             * avatar : {"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"}
             * nickName : 黄佛丁
             * sex : 0
             * sign :
             */

            private AvatarBean avatar;
            private String nickName;
            private int sex;
            private String sign;

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

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public static class AvatarBean {
                /**
                 * picturePath : http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg
                 * samllPicturePath : http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg
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
             * avatar : {"picturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a_small.jpg"}
             * nickName : 小智
             * sex : 1
             * sign :
             */

            private AvatarBeanX avatar;
            private String nickName;
            private int sex;
            private String sign;

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

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public static class AvatarBeanX {
                /**
                 * picturePath : http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a.jpg
                 * samllPicturePath : http://192.168.1.50:8080/psms/upload/2018-2-26/9462db4c3506487e87b860f17842d97a_small.jpg
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

        public static class ChildrenBean {
            /**
             * children : []
             * content : 测试回复讨论
             * createTime : 2019-10-18 17:05:16
             * createUserInfo : {"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"},"nickName":"天天开心","sex":0,"sign":""}
             * createUserType : 0
             * discussId : 157138951617ca4f11d4eade4c28990f
             * discussNum : 0
             * praiseNum : 0
             * praiseStatus : 0
             * replyUserInfo : {"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"},"nickName":"黄佛丁","sex":0,"sign":""}
             * replyUserType : 0
             */

            private String content;
            private String createTime;
            private CreateUserInfoBeanX createUserInfo;
            private int createUserType;
            private String howLong;
            private String discussId;
            private int discussNum;
            private int praiseNum;
            private int praiseStatus;
            private ReplyUserInfoBeanX replyUserInfo;
            private int replyUserType;
            private List<?> children;

            public String getHowLong() {
                return howLong;
            }

            public void setHowLong(String howLong) {
                this.howLong = howLong;
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

            public CreateUserInfoBeanX getCreateUserInfo() {
                return createUserInfo;
            }

            public void setCreateUserInfo(CreateUserInfoBeanX createUserInfo) {
                this.createUserInfo = createUserInfo;
            }

            public int getCreateUserType() {
                return createUserType;
            }

            public void setCreateUserType(int createUserType) {
                this.createUserType = createUserType;
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

            public ReplyUserInfoBeanX getReplyUserInfo() {
                return replyUserInfo;
            }

            public void setReplyUserInfo(ReplyUserInfoBeanX replyUserInfo) {
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

            public static class CreateUserInfoBeanX {
                /**
                 * avatar : {"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"}
                 * nickName : 天天开心
                 * sex : 0
                 * sign :
                 */

                private AvatarBeanXX avatar;
                private String nickName;
                private int sex;
                private String sign;

                public AvatarBeanXX getAvatar() {
                    return avatar;
                }

                public void setAvatar(AvatarBeanXX avatar) {
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

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public static class AvatarBeanXX {
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

            public static class ReplyUserInfoBeanX {
                /**
                 * avatar : {"picturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg"}
                 * nickName : 黄佛丁
                 * sex : 0
                 * sign :
                 */

                private AvatarBeanXXX avatar;
                private String nickName;
                private int sex;
                private String sign;

                public AvatarBeanXXX getAvatar() {
                    return avatar;
                }

                public void setAvatar(AvatarBeanXXX avatar) {
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

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public static class AvatarBeanXXX {
                    /**
                     * picturePath : http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5.jpg
                     * samllPicturePath : http://192.168.1.50:8080/psms/upload/2017-8-28/8221187f6a6f4e928074af9179a4e0f5_small.jpg
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
}
