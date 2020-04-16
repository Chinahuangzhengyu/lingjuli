package com.zhjl.qihao.abrefactor.bean;

public class UploadAppBean {

    /**
     * code : 200
     * data : {"appVersion":"1.0.0","downloadUrl":"http://baidu.com","forceUpdate":1,"newVersion":1,"updateLog":"上线版本号"}
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
         * appVersion : 1.0.0
         * downloadUrl : http://baidu.com
         * forceUpdate : 1
         * newVersion : 1
         * updateLog : 上线版本号
         */

        private String appVersion;
        private String downloadUrl;
        private int forceUpdate;
        private int newVersion;
        private String updateLog;

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public int getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(int forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public int getNewVersion() {
            return newVersion;
        }

        public void setNewVersion(int newVersion) {
            this.newVersion = newVersion;
        }

        public String getUpdateLog() {
            return updateLog;
        }

        public void setUpdateLog(String updateLog) {
            this.updateLog = updateLog;
        }
    }
}
