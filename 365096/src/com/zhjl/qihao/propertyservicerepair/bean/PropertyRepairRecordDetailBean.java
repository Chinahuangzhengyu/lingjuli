package com.zhjl.qihao.propertyservicerepair.bean;

import java.util.List;

public class PropertyRepairRecordDetailBean {

    /**
     * code : 200
     * data : {"address":"北京百花KASO创意公园","brokenType":"","description":"888","id":6,"repairNo":"2019110717170565","repairPictures":[{"pictureId":"1573118216317fb234c183fd4a81a70b","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/945e0bd04f8f466b8be9cb0febdbe8c8.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/945e0bd04f8f466b8be9cb0febdbe8c8_small.jpg","timestamp":1573147016000},{"pictureId":"157311821642e6b09790fa7b4e4e881e","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/3d7b4b3c0839476d9ca7850ef9d3bceb.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/3d7b4b3c0839476d9ca7850ef9d3bceb_small.jpg","timestamp":1573147016000},{"pictureId":"15731182165225ac91a88baa41388f62","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/275f4be1bb1445f8b972a27a24d6b649.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/275f4be1bb1445f8b972a27a24d6b649_small.jpg","timestamp":1573147016000},{"pictureId":"15731182166215fb27f103ee44fea705","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/0196a11fcf5c4811a318b765508e5bd2.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/0196a11fcf5c4811a318b765508e5bd2_small.jpg","timestamp":1573147016000}],"results":[{"brokenCause":"灯杆被水腐蚀严重","createTime":"2019-11-06","dealPeople":"飞翔的企鹅","processPics":[{"pictureId":"150123951477651a362b2193464bb029","picturePath":"http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8_small.jpg","timestamp":1501268314000}],"processResult":"解决","receiver":"测试"}],"status":"处理中","typeName":"公共路灯"}
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
         * address : 北京百花KASO创意公园
         * brokenType :
         * description : 888
         * id : 6
         * repairNo : 2019110717170565
         * repairPictures : [{"pictureId":"1573118216317fb234c183fd4a81a70b","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/945e0bd04f8f466b8be9cb0febdbe8c8.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/945e0bd04f8f466b8be9cb0febdbe8c8_small.jpg","timestamp":1573147016000},{"pictureId":"157311821642e6b09790fa7b4e4e881e","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/3d7b4b3c0839476d9ca7850ef9d3bceb.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/3d7b4b3c0839476d9ca7850ef9d3bceb_small.jpg","timestamp":1573147016000},{"pictureId":"15731182165225ac91a88baa41388f62","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/275f4be1bb1445f8b972a27a24d6b649.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/275f4be1bb1445f8b972a27a24d6b649_small.jpg","timestamp":1573147016000},{"pictureId":"15731182166215fb27f103ee44fea705","picturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/0196a11fcf5c4811a318b765508e5bd2.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-11-7/0196a11fcf5c4811a318b765508e5bd2_small.jpg","timestamp":1573147016000}]
         * results : [{"brokenCause":"灯杆被水腐蚀严重","createTime":"2019-11-06","dealPeople":"飞翔的企鹅","processPics":[{"pictureId":"150123951477651a362b2193464bb029","picturePath":"http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8_small.jpg","timestamp":1501268314000}],"processResult":"解决","receiver":"测试"}]
         * status : 处理中
         * typeName : 公共路灯
         */

        private String address;
        private String brokenType;
        private String description;
        private int id;
        private String repairNo;
        private String status;
        private String typeName;
        private List<RepairPicturesBean> repairPictures;
        private List<ResultsBean> results;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBrokenType() {
            return brokenType;
        }

        public void setBrokenType(String brokenType) {
            this.brokenType = brokenType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRepairNo() {
            return repairNo;
        }

        public void setRepairNo(String repairNo) {
            this.repairNo = repairNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<RepairPicturesBean> getRepairPictures() {
            return repairPictures;
        }

        public void setRepairPictures(List<RepairPicturesBean> repairPictures) {
            this.repairPictures = repairPictures;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class RepairPicturesBean {
            /**
             * pictureId : 1573118216317fb234c183fd4a81a70b
             * picturePath : http://192.168.1.50:8080/psms/upload/2019-11-7/945e0bd04f8f466b8be9cb0febdbe8c8.jpg
             * smallPicturePath : http://192.168.1.50:8080/psms/upload/2019-11-7/945e0bd04f8f466b8be9cb0febdbe8c8_small.jpg
             * timestamp : 1573147016000
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

        public static class ResultsBean {
            /**
             * brokenCause : 灯杆被水腐蚀严重
             * createTime : 2019-11-06
             * dealPeople : 飞翔的企鹅
             * processPics : [{"pictureId":"150123951477651a362b2193464bb029","picturePath":"http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8_small.jpg","timestamp":1501268314000}]
             * processResult : 解决
             * receiver : 测试
             */

            private String brokenCause;
            private String createTime;
            private String dealPeople;
            private String processResult;
            private String receiver;
            private List<ProcessPicsBean> processPics;

            public String getBrokenCause() {
                return brokenCause;
            }

            public void setBrokenCause(String brokenCause) {
                this.brokenCause = brokenCause;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDealPeople() {
                return dealPeople;
            }

            public void setDealPeople(String dealPeople) {
                this.dealPeople = dealPeople;
            }

            public String getProcessResult() {
                return processResult;
            }

            public void setProcessResult(String processResult) {
                this.processResult = processResult;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public List<ProcessPicsBean> getProcessPics() {
                return processPics;
            }

            public void setProcessPics(List<ProcessPicsBean> processPics) {
                this.processPics = processPics;
            }

            public static class ProcessPicsBean {
                /**
                 * pictureId : 150123951477651a362b2193464bb029
                 * picturePath : http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8.jpg
                 * smallPicturePath : http://192.168.1.50:8080/psms/upload/2017-7-28/2eacfe3902f4463e8e0d2817519649b8_small.jpg
                 * timestamp : 1501268314000
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
