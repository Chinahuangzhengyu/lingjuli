package com.zhjl.qihao.complaint.bean;

import com.zhjl.qihao.ablogin.bean.LoginBean;

import java.util.List;

//详情
public class ComplaintDetailBean {


    /**
     * code : 200
     * data : {"communityCode":"0002001","content":"平台用户谩骂～","createTime":"2019-09-29 00:35:42","createUser":{"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"},"nickName":"天天开心","sex":0,"sign":""},"discussNum":0,"forumLabel":{"id":"150123148717596e839d4af34a7eb756","labelName":"平台投诉"},"forumNoteId":"15696597421648e3e7c2a6b34009994d","forumPicture":[{"createTime":1504171556000,"createUser":"150125608705d79d08b274744d87b6cd","id":"150414275646aee3a2526e144c4ca92b","idFk":"1504142772136d598f18c1f54e9f9ab8","picturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d.jpg","samllPicturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d_small.jpg"}],"howLong":"19天前","noteSource":0,"praiseNum":1,"praiseStatus":0,"processStatus":0,"replays":[{"createTime":"2019-10-07 23:57:27","forumNoteId":"15696597421648e3e7c2a6b34009994d","id":19,"praiseNum":0,"praiseStatus":false,"replayNote":"哈哈","replayPics":""},{"createTime":"2019-10-07 23:34:15","forumNoteId":"15696597421648e3e7c2a6b34009994d","id":17,"praiseNum":0,"praiseStatus":false,"replayNote":"测试图片","replayPics":"/upload/2019-10-7/2b31c28d381a4e3d85cc0a4cf038281a.jpg,/upload/2019-10-7/5f1d6c7dba524e4ba069cbd0fd672964.jpg"},{"createTime":"2019-10-07 23:33:07","forumNoteId":"15696597421648e3e7c2a6b34009994d","id":16,"praiseNum":0,"praiseStatus":false,"replayNote":"谢谢你的反馈 我们正在处理","replayPics":""}],"title":"平台投诉"}
     * message : ok
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

    public static class DataBean {
        /**
         * communityCode : 0002001
         * content : 平台用户谩骂～
         * createTime : 2019-09-29 00:35:42
         * createUser : {"avatar":{"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"},"nickName":"天天开心","sex":0,"sign":""}
         * discussNum : 0
         * forumLabel : {"id":"150123148717596e839d4af34a7eb756","labelName":"平台投诉"}
         * forumNoteId : 15696597421648e3e7c2a6b34009994d
         * forumPicture : [{"createTime":1504171556000,"createUser":"150125608705d79d08b274744d87b6cd","id":"150414275646aee3a2526e144c4ca92b","idFk":"1504142772136d598f18c1f54e9f9ab8","picturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d.jpg","samllPicturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d_small.jpg"}]
         * howLong : 19天前
         * noteSource : 0
         * praiseNum : 1
         * praiseStatus : 0
         * processStatus : 0
         * replays : [{"createTime":"2019-10-07 23:57:27","forumNoteId":"15696597421648e3e7c2a6b34009994d","id":19,"praiseNum":0,"praiseStatus":false,"replayNote":"哈哈","replayPics":""},{"createTime":"2019-10-07 23:34:15","forumNoteId":"15696597421648e3e7c2a6b34009994d","id":17,"praiseNum":0,"praiseStatus":false,"replayNote":"测试图片","replayPics":"/upload/2019-10-7/2b31c28d381a4e3d85cc0a4cf038281a.jpg,/upload/2019-10-7/5f1d6c7dba524e4ba069cbd0fd672964.jpg"},{"createTime":"2019-10-07 23:33:07","forumNoteId":"15696597421648e3e7c2a6b34009994d","id":16,"praiseNum":0,"praiseStatus":false,"replayNote":"谢谢你的反馈 我们正在处理","replayPics":""}]
         * title : 平台投诉
         */

        private String communityCode;
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
        private int processStatus;
        private String title;
        private List<LoginBean.DataBean.UserInfoBean.AvatarBean> forumPictureList;
        private List<ReplaysBean> replays;
        private String complaintId;

        public String getComplaintId() {
            return complaintId;
        }

        public void setComplaintId(String complaintId) {
            this.complaintId = complaintId;
        }

        public String getCommunityCode() {
            return communityCode;
        }

        public void setCommunityCode(String communityCode) {
            this.communityCode = communityCode;
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

        public int getProcessStatus() {
            return processStatus;
        }

        public void setProcessStatus(int processStatus) {
            this.processStatus = processStatus;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<LoginBean.DataBean.UserInfoBean.AvatarBean> getForumPicture() {
            return forumPictureList;
        }

        public void setForumPicture(List<LoginBean.DataBean.UserInfoBean.AvatarBean> forumPicture) {
            this.forumPictureList = forumPicture;
        }

        public List<ReplaysBean> getReplays() {
            return replays;
        }

        public void setReplays(List<ReplaysBean> replays) {
            this.replays = replays;
        }

        public static class CreateUserBean {
            /**
             * avatar : {"picturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg"}
             * nickName : 天天开心
             * sex : 0
             * sign :
             */

            private AvatarBean avatar;
            private String nickname;
            private int sex;
            private String sign;

            public AvatarBean getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBean avatar) {
                this.avatar = avatar;
            }

            public String getNickName() {
                return nickname;
            }

            public void setNickName(String nickName) {
                this.nickname = nickName;
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
                 * picturePath : http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg
                 * samllPicturePath : http://192.168.1.50:8080/psms/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg
                 */

                private String picturePath;
                private String smallPicturePath;

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
            }
        }

        public static class ForumLabelBean {
            /**
             * id : 150123148717596e839d4af34a7eb756
             * labelName : 平台投诉
             */

            private String id;
            private String labelName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLabelName() {
                return labelName;
            }

            public void setLabelName(String labelName) {
                this.labelName = labelName;
            }
        }

        public static class ForumPictureBean {
            /**
             * createTime : 1504171556000
             * createUser : 150125608705d79d08b274744d87b6cd
             * id : 150414275646aee3a2526e144c4ca92b
             * idFk : 1504142772136d598f18c1f54e9f9ab8
             * picturePath : /upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d.jpg
             * samllPicturePath : /upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d_small.jpg
             */

            private long createTime;
            private String createUser;
            private String id;
            private String idFk;
            private String picturePath;
            private String samllPicturePath;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIdFk() {
                return idFk;
            }

            public void setIdFk(String idFk) {
                this.idFk = idFk;
            }

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

        public static class ReplaysBean {
            /**
             * createTime : 2019-10-07 23:57:27
             * forumNoteId : 15696597421648e3e7c2a6b34009994d
             * id : 19
             * praiseNum : 0
             * praiseStatus : false
             * replayNote : 哈哈
             * replayPics :
             */

            private String createTime;
            private String forumNoteId;
            private int id;
            private int praiseNum;
            private boolean praiseStatus;
            private String replayNote;
            private String replayPics;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getForumNoteId() {
                return forumNoteId;
            }

            public void setForumNoteId(String forumNoteId) {
                this.forumNoteId = forumNoteId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPraiseNum() {
                return praiseNum;
            }

            public void setPraiseNum(int praiseNum) {
                this.praiseNum = praiseNum;
            }

            public boolean isPraiseStatus() {
                return praiseStatus;
            }

            public void setPraiseStatus(boolean praiseStatus) {
                this.praiseStatus = praiseStatus;
            }

            public String getReplayNote() {
                return replayNote;
            }

            public void setReplayNote(String replayNote) {
                this.replayNote = replayNote;
            }

            public String getReplayPics() {
                return replayPics;
            }

            public void setReplayPics(String replayPics) {
                this.replayPics = replayPics;
            }
        }
    }
}
