package com.zhjl.qihao.abrefactor.bean;

import java.util.List;

public class ComplaintListBean {


    /**
     * code : 200
     * data : [{"communityCode":"0002001","content":"啊","createTime":"2019-09-29 00:40:02","discussNum":0,"forumLabel":{"id":"150123148717137221cb421a461891e6","labelName":"物业投诉"},"forumNoteId":"15696600027664ed23d878574065bd6c","forumPicture":[{"createTime":1504171556000,"createUser":"150125608705d79d08b274744d87b6cd","id":"150414275646aee3a2526e144c4ca92b","idFk":"1504142772136d598f18c1f54e9f9ab8","picturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d.jpg","samllPicturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d_small.jpg"}],"howLong":"19天前","noteSource":0,"phoneUser":{"avatar":{"picturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","samllPicturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528_small.jpg"},"nickName":"你好","sex":0,"sign":"%E6%88%91%E5%8F%AA%E5%9C%A8%E4%B9%8E%E5%8D%81%E5%B9%B4%E5%90%8E%E7%9A%84%E8%87%AA%E5%B7%B1%E6%80%8E%E4%B9%88%E7%9C%8B%E7%8E%B0%E5%9C%A8%E7%9A%84%E6%88%91%EF%BC%81"},"praiseNum":0,"praiseStatus":0,"processStatus":0,"title":"投诉"},{"communityCode":"0002001","content":"啊哈哈","createTime":"2019-09-30 00:53:55","discussNum":0,"forumLabel":{"id":"150123148717137221cb421a461891e6","labelName":"物业投诉"},"forumNoteId":"156966083589c5155adf5eb84d8e93d2","forumPicture":[],"howLong":"18天前","noteSource":0,"phoneUser":{"avatar":{"picturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","samllPicturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528_small.jpg"},"nickName":"你好","sex":0,"sign":"%E6%88%91%E5%8F%AA%E5%9C%A8%E4%B9%8E%E5%8D%81%E5%B9%B4%E5%90%8E%E7%9A%84%E8%87%AA%E5%B7%B1%E6%80%8E%E4%B9%88%E7%9C%8B%E7%8E%B0%E5%9C%A8%E7%9A%84%E6%88%91%EF%BC%81"},"praiseNum":2,"praiseStatus":0,"processStatus":0,"title":"投诉"},{"communityCode":"0002001","content":"平台用户谩骂～","createTime":"2019-09-29 00:35:42","discussNum":0,"forumLabel":{"id":"150123148717596e839d4af34a7eb756","labelName":"平台投诉"},"forumNoteId":"15696597421648e3e7c2a6b34009994d","forumPicture":[],"howLong":"19天前","noteSource":0,"phoneUser":{"avatar":{"picturePath":"/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249_small.jpg"},"nickName":"天天开心","sex":0,"sign":""},"praiseNum":1,"praiseStatus":0,"processStatus":0,"title":"平台投诉"},{"communityCode":"0002001","content":"手表","createTime":"2019-09-29 00:36:43","discussNum":0,"forumLabel":{"id":"150123148717596e839d4af34a7eb756","labelName":"平台投诉"},"forumNoteId":"156965980335a93cfd89909e465d974c","forumPicture":[],"howLong":"19天前","noteSource":0,"phoneUser":{"avatar":{"picturePath":"/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249.jpg","samllPicturePath":"/upload/2019-9-27/acb1689637ca402bb2c2d754de4a1249_small.jpg"},"nickName":"天天开心","sex":0,"sign":""},"praiseNum":1,"praiseStatus":0,"processStatus":0,"title":"平台投诉"}]
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
         * communityCode : 0002001
         * content : 啊
         * createTime : 2019-09-29 00:40:02
         * discussNum : 0
         * forumLabel : {"id":"150123148717137221cb421a461891e6","labelName":"物业投诉"}
         * forumNoteId : 15696600027664ed23d878574065bd6c
         * forumPicture : [{"createTime":1504171556000,"createUser":"150125608705d79d08b274744d87b6cd","id":"150414275646aee3a2526e144c4ca92b","idFk":"1504142772136d598f18c1f54e9f9ab8","picturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d.jpg","samllPicturePath":"/upload/2017-8-31/2e1914d566ed4a0c870950f76bbf891d_small.jpg"}]
         * howLong : 19天前
         * noteSource : 0
         * phoneUser : {"avatar":{"picturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","samllPicturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528_small.jpg"},"nickName":"你好","sex":0,"sign":"%E6%88%91%E5%8F%AA%E5%9C%A8%E4%B9%8E%E5%8D%81%E5%B9%B4%E5%90%8E%E7%9A%84%E8%87%AA%E5%B7%B1%E6%80%8E%E4%B9%88%E7%9C%8B%E7%8E%B0%E5%9C%A8%E7%9A%84%E6%88%91%EF%BC%81"}
         * praiseNum : 0
         * praiseStatus : 0
         * processStatus : 0
         * title : 投诉
         */

        private String communityCode;
        private String content;
        private String createTime;
        private int discussNum;
        private ForumLabelBean forumLabel;
        private String forumNoteId;
        private String howLong;
        private int noteSource;
        private PhoneUserBean phoneUser;
        private int praiseNum;
        private int praiseStatus;
        private int processStatus;
        private String title;
        private List<ForumPictureBean> forumPicture;

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

        public int getDiscussNum() {
            return discussNum;
        }

        public void setDiscussNum(int discussNum) {
            this.discussNum = discussNum;
        }

        public ForumLabelBean getForumLabel() {
            return forumLabel;
        }

        public void setForumLabel(ForumLabelBean forumLabel) {
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

        public PhoneUserBean getPhoneUser() {
            return phoneUser;
        }

        public void setPhoneUser(PhoneUserBean phoneUser) {
            this.phoneUser = phoneUser;
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

        public List<ForumPictureBean> getForumPicture() {
            return forumPicture;
        }

        public void setForumPicture(List<ForumPictureBean> forumPicture) {
            this.forumPicture = forumPicture;
        }

        public static class ForumLabelBean {
            /**
             * id : 150123148717137221cb421a461891e6
             * labelName : 物业投诉
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

        public static class PhoneUserBean {
            /**
             * avatar : {"picturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","samllPicturePath":"/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528_small.jpg"}
             * nickName : 你好
             * sex : 0
             * sign : %E6%88%91%E5%8F%AA%E5%9C%A8%E4%B9%8E%E5%8D%81%E5%B9%B4%E5%90%8E%E7%9A%84%E8%87%AA%E5%B7%B1%E6%80%8E%E4%B9%88%E7%9C%8B%E7%8E%B0%E5%9C%A8%E7%9A%84%E6%88%91%EF%BC%81
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
                 * picturePath : /upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg
                 * samllPicturePath : /upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528_small.jpg
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
    }
}
