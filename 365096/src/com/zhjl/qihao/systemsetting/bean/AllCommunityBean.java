package com.zhjl.qihao.systemsetting.bean;

import java.util.List;

public class AllCommunityBean {

    /**
     * code : 200
     * data : [{"smallCommunityCode":"0001001","smallCommunityName":"香逸湾小区"},{"smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园"},{"smallCommunityCode":"0003001","smallCommunityName":"龙阁公寓"},{"smallCommunityCode":"0004001","smallCommunityName":"中浩集团总部"},{"smallCommunityCode":"0005001","smallCommunityName":"竟园艺术中心"},{"smallCommunityCode":"0006001","smallCommunityName":"万方家园"},{"smallCommunityCode":"0007001","smallCommunityName":"方恒东景"},{"smallCommunityCode":"0009001","smallCommunityName":"园景小区"},{"smallCommunityCode":"0010001","smallCommunityName":"杨家洼小区"},{"smallCommunityCode":"0011001","smallCommunityName":"西苑小区"},{"smallCommunityCode":"0012001","smallCommunityName":"宝星园小区"},{"smallCommunityCode":"0013001","smallCommunityName":"慧谷阳光"},{"smallCommunityCode":"0014001","smallCommunityName":"华鼎世家"},{"smallCommunityCode":"0015001","smallCommunityName":"南湖东园"},{"smallCommunityCode":"0016001","smallCommunityName":"精英家园"},{"smallCommunityCode":"0017001","smallCommunityName":"银都花园"},{"smallCommunityCode":"0018001","smallCommunityName":"临江丽苑"},{"smallCommunityCode":"0019001","smallCommunityName":"天河桃苑"},{"smallCommunityCode":"0020001","smallCommunityName":"阳光名苑"},{"smallCommunityCode":"0021001","smallCommunityName":"杏园小区"},{"smallCommunityCode":"0022001","smallCommunityName":"大关南六苑"},{"smallCommunityCode":"0023001","smallCommunityName":"好望角小区"},{"smallCommunityCode":"0024001","smallCommunityName":"凤凰社区东区"},{"smallCommunityCode":"0025001","smallCommunityName":"南翼经济小区"},{"smallCommunityCode":"0026001","smallCommunityName":"建清园小区"},{"smallCommunityCode":"0027001","smallCommunityName":"逸成东苑"},{"smallCommunityCode":"0028001","smallCommunityName":"柏儒苑"},{"smallCommunityCode":"0029001","smallCommunityName":"西颐小区"},{"smallCommunityCode":"0030001","smallCommunityName":"高里掌路1号院"},{"smallCommunityCode":"0031001","smallCommunityName":"建西苑北里"},{"smallCommunityCode":"0032001","smallCommunityName":"八角北里"},{"smallCommunityCode":"0033001","smallCommunityName":"璟公院"},{"smallCommunityCode":"0034001","smallCommunityName":"北蜂窝路5号院"},{"smallCommunityCode":"0035001","smallCommunityName":"首体南路1号院"},{"smallCommunityCode":"0036001","smallCommunityName":"高云小区"},{"smallCommunityCode":"0037001","smallCommunityName":"1号院"},{"smallCommunityCode":"0037002","smallCommunityName":"2号院"},{"smallCommunityCode":"0002002","smallCommunityName":"深圳小区"},{"smallCommunityCode":"0002003","smallCommunityName":"北京管理"},{"smallCommunityCode":"0038001","smallCommunityName":"舞动京城"},{"smallCommunityCode":"0039001","smallCommunityName":"安慧北里小区"},{"smallCommunityCode":"0039002","smallCommunityName":"安慧里二区"},{"smallCommunityCode":"0039003","smallCommunityName":"安慧里三区"},{"smallCommunityCode":"0039004","smallCommunityName":"安慧里四区"},{"smallCommunityCode":"0039005","smallCommunityName":"安慧里五区"},{"smallCommunityCode":"0040001","smallCommunityName":"沿海赛洛城"},{"smallCommunityCode":"0036002","smallCommunityName":"第二楼盘小区"},{"smallCommunityCode":"0036003","smallCommunityName":"太和风景小区"},{"smallCommunityCode":"0036004","smallCommunityName":"第四楼盘小区"},{"smallCommunityCode":"0036005","smallCommunityName":"金色家园小区"},{"smallCommunityCode":"0001002","smallCommunityName":"中国移动小区"},{"smallCommunityCode":"0001003","smallCommunityName":"广东电信小区"},{"smallCommunityCode":"0042001","smallCommunityName":"金泉国际新城"}]
     * message : 获取数据成功
     */

    private int code;
    private String message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * smallCommunityCode : 0001001
         * smallCommunityName : 香逸湾小区
         */

        private String smallCommunityCode;
        private String smallCommunityName;

        public String getSmallCommunityCode() {
            return smallCommunityCode;
        }

        public void setSmallCommunityCode(String smallCommunityCode) {
            this.smallCommunityCode = smallCommunityCode;
        }

        public String getSmallCommunityName() {
            return smallCommunityName;
        }

        public void setSmallCommunityName(String smallCommunityName) {
            this.smallCommunityName = smallCommunityName;
        }
    }
}
