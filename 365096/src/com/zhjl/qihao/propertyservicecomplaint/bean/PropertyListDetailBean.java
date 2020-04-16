package com.zhjl.qihao.propertyservicecomplaint.bean;

import java.util.List;

public class PropertyListDetailBean {


    /**
     * code : 200
     * data : {"address":"我要投诉","complaint":"博南小区H33栋3单元0202","complaintNo":"2020031711052842","description":"你好，我是我","ossPictureVO":[{"fileSize":1077620,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200317/picture_1584414327341.jpg","height":"4032.0","width":"3024.0"}],"results":[{"createDate":"2020-03-17","pictures":[{"pictureId":"158441565133cf3e42cf640f40c98cf8","picturePath":"http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9_small.jpg","timestamp":1584415651000}],"receiver":"测试人员","result":"已经处理完成"}],"status":1,"title":"投诉"}
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

    public static class DataBean {
        /**
         * address : 我要投诉
         * complaint : 博南小区H33栋3单元0202
         * complaintNo : 2020031711052842
         * description : 你好，我是我
         * ossPictureVO : [{"fileSize":1077620,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200317/picture_1584414327341.jpg","height":"4032.0","width":"3024.0"}]
         * results : [{"createDate":"2020-03-17","pictures":[{"pictureId":"158441565133cf3e42cf640f40c98cf8","picturePath":"http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9_small.jpg","timestamp":1584415651000}],"receiver":"测试人员","result":"已经处理完成"}]
         * status : 1
         * title : 投诉
         */

        private String address;
        private String complaint;
        private String complaintNo;
        private String description;
        private int status;
        private String title;
        private List<OssPictureVOBean> ossPictureVO;
        private List<ResultsBean> results;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getComplaint() {
            return complaint;
        }

        public void setComplaint(String complaint) {
            this.complaint = complaint;
        }

        public String getComplaintNo() {
            return complaintNo;
        }

        public void setComplaintNo(String complaintNo) {
            this.complaintNo = complaintNo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<OssPictureVOBean> getOssPictureVO() {
            return ossPictureVO;
        }

        public void setOssPictureVO(List<OssPictureVOBean> ossPictureVO) {
            this.ossPictureVO = ossPictureVO;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class OssPictureVOBean {
            /**
             * fileSize : 1077620
             * filename : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200317/picture_1584414327341.jpg
             * height : 4032.0
             * width : 3024.0
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
        }

        public static class ResultsBean {
            /**
             * createDate : 2020-03-17
             * pictures : [{"pictureId":"158441565133cf3e42cf640f40c98cf8","picturePath":"http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9.jpg","smallPicturePath":"http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9_small.jpg","timestamp":1584415651000}]
             * receiver : 测试人员
             * result : 已经处理完成
             */

            private String createDate;
            private String receiver;
            private String result;
            private List<PicturesBean> pictures;

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public List<PicturesBean> getPictures() {
                return pictures;
            }

            public void setPictures(List<PicturesBean> pictures) {
                this.pictures = pictures;
            }

            public static class PicturesBean {
                /**
                 * pictureId : 158441565133cf3e42cf640f40c98cf8
                 * picturePath : http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9.jpg
                 * smallPicturePath : http://47.105.215.157:8080/psms/upload/2020-3-17/1cfc14cf04004449a1ba95e84eb16de9_small.jpg
                 * timestamp : 1584415651000
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
}
