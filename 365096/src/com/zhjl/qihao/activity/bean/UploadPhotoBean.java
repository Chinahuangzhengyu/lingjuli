package com.zhjl.qihao.activity.bean;

public class UploadPhotoBean {

    /**
     * code : 200
     * data : {"fileSize":18391,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/54548.png","format":"png","height":300,"mineType":"image/png","ossId":45,"width":300}
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
         * fileSize : 18391
         * filename : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200219/54548.png
         * format : png
         * height : 300
         * mineType : image/png
         * ossId : 45
         * width : 300
         */

        private int fileSize;
        private String filename;
        private String format;
        private int height;
        private String mineType;
        private int ossId;
        private int width;

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

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getMineType() {
            return mineType;
        }

        public void setMineType(String mineType) {
            this.mineType = mineType;
        }

        public int getOssId() {
            return ossId;
        }

        public void setOssId(int ossId) {
            this.ossId = ossId;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
