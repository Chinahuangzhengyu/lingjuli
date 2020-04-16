package com.zhjl.qihao.abproperty.model;

import java.util.List;

/**
 * time   :  2018/11/28
 * author :  Z
 * des    :  维修类型 JavaBean
 */
public class ServiceTypeBean {


    /**
     * result : 0
     * message : 成功
     * serviceId : 150123148707b5188e83971641cc9f6d
     * contactPhone : 18301031062
     * list : [{"costId":"15012314870734cb4c6c08ec475fbd8f","costName":"空调","costStandard":"100"},{"costId":"1542939155709fc3cbc14fef4e209722","costName":"冰箱","costStandard":"200"},{"costId":"154293916354b3d961bc883f43cca5cf","costName":"桌子","costStandard":"免费"},{"costId":"154296369260bf6b28af9d854227bc27","costName":"抽油烟机","costStandard":"100"},{"costId":"154296372002fc9b9038cc14423ea0bb","costName":"修下水道网点","costStandard":"20"},{"costId":"1543285163017b05409337d44c46b989","costName":"测试","costStandard":"20"}]
     */

    private String result;
    private String message;
    private String serviceId;
    private String contactPhone;
    private List<ListBean> list;

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * costId : 15012314870734cb4c6c08ec475fbd8f
         * costName : 空调
         * costStandard : 100
         */

        private String costId;
        private String costName;
        private String costStandard;

        public String getCostId() {
            return costId;
        }

        public void setCostId(String costId) {
            this.costId = costId;
        }

        public String getCostName() {
            return costName;
        }

        public void setCostName(String costName) {
            this.costName = costName;
        }

        public String getCostStandard() {
            return costStandard;
        }

        public void setCostStandard(String costStandard) {
            this.costStandard = costStandard;
        }
    }
}
