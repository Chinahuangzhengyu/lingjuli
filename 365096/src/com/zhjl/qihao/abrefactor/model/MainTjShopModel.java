package com.zhjl.qihao.abrefactor.model;

import java.util.List;

/**
 * time   :  2018/7/19
 * author :  Z
 * des    :
 */

public class MainTjShopModel {

    /**
     * result : 0
     * message : 获取成功
     * shoplist : [{"url_name":"(测试)我的店","url":"http://www.taobao.com","logo":"http://120.27.221.45/uploadfile/all/1531883711.jpg","note":"~~~~~~~~~~~~~~~~~~","shopid":"","shopname":""}]
     * pageNum : 1
     */

    private String result;
    private String message;
    private int pageNum;
    private List<ShoplistBean> shoplist;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<ShoplistBean> getShoplist() {
        return shoplist;
    }

    public void setShoplist(List<ShoplistBean> shoplist) {
        this.shoplist = shoplist;
    }

    public static class ShoplistBean {
        /**
         * url_name : (测试)我的店
         * url : http://www.taobao.com
         * logo : http://120.27.221.45/uploadfile/all/1531883711.jpg
         * note : ~~~~~~~~~~~~~~~~~~
         * shopid :
         * shopname :
         */

        private String url_name;
        private String url;
        private String logo;
        private String note;
        private String shopid;
        private String shopname;
        private String service_cell;

        public String getService_cell() {
            return service_cell;
        }

        public void setService_cell(String service_cell) {
            this.service_cell = service_cell;
        }

        public String getUrl_name() {
            return url_name;
        }

        public void setUrl_name(String url_name) {
            this.url_name = url_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        @Override
        public String toString() {
            return "ShoplistBean{" +
                    "url_name='" + url_name + '\'' +
                    ", url='" + url + '\'' +
                    ", logo='" + logo + '\'' +
                    ", note='" + note + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", shopname='" + shopname + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MainTjShopModel{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", pageNum=" + pageNum +
                ", shoplist=" + shoplist +
                '}';
    }
}
