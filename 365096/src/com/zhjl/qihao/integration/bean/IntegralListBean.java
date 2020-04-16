package com.zhjl.qihao.integration.bean;

import java.util.List;

/**
 * 作者： 黄郑宇
 * 时间： 2018/8/21
 * 类作用：积分列表bean
 */
public class IntegralListBean {


    /**
     * status : true
     * nbit_number : 1000
     * nbit_list : [{"create_date":"2019-11-30 09:48:56","number":0,"memo":"物业服务订单"},{"create_date":"2019-11-30 09:59:40","number":0,"memo":"物业服务订单"},{"create_date":"2019-11-30 10:29:52","number":0,"memo":"物业服务订单"},{"create_date":"2019-11-30 11:24:07","number":0,"memo":"物业服务订单"},{"create_date":"2019-12-03 10:45:32","number":0,"memo":"物业服务订单"},{"create_date":"2019-12-03 10:48:38","number":0,"memo":"物业服务订单"},{"create_date":"2019-12-03 11:04:03","number":0,"memo":"物业服务订单"},{"create_date":"2019-12-03 11:07:44","number":0,"memo":"物业服务订单"},{"create_date":"2019-12-03 11:54:27","number":0,"memo":"物业服务订单"},{"create_date":"2019-12-06 04:02:48","number":-1000,"memo":"赠送积分"}]
     * info : {"page":1,"total":10,"total_page":1}
     */

    private boolean status;
    private long nbit_number;
    private InfoBean info;
    private List<NbitListBean> nbit_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getNbit_number() {
        return nbit_number;
    }

    public void setNbit_number(Long nbit_number) {
        this.nbit_number = nbit_number;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<NbitListBean> getNbit_list() {
        return nbit_list;
    }

    public void setNbit_list(List<NbitListBean> nbit_list) {
        this.nbit_list = nbit_list;
    }

    public static class InfoBean {
        /**
         * page : 1
         * total : 10
         * total_page : 1
         */

        private int page;
        private int total;
        private int total_page;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }
    }

    public static class NbitListBean {
        /**
         * create_date : 2019-11-30 09:48:56
         * number : 0
         * memo : 物业服务订单
         */

        private String create_date;
        private String number;
        private String memo;

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
