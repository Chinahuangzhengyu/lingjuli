package com.zhjl.qihao.propertyservicerepair.bean;

import java.util.List;

public class PropertyRepairInfoBean {

    /**
     * code : 200
     * data : {"smallCommunityCode":"0002001","smallCommunityList":[{"smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园"},{"smallCommunityCode":"0003001","smallCommunityName":"龙阁公寓"},{"smallCommunityCode":"0042001","smallCommunityName":"金泉国际新城"}],"types":[{"name":"公共路灯","typeId":1},{"name":"花园绿化","typeId":2}]}
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
         * smallCommunityCode : 0002001
         * smallCommunityList : [{"smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园"},{"smallCommunityCode":"0003001","smallCommunityName":"龙阁公寓"},{"smallCommunityCode":"0042001","smallCommunityName":"金泉国际新城"}]
         * types : [{"name":"公共路灯","typeId":1},{"name":"花园绿化","typeId":2}]
         */

        private String smallCommunityCode;
        private List<SmallCommunityListBean> smallCommunityList;
        private List<TypesBean> types;

        public String getSmallCommunityCode() {
            return smallCommunityCode;
        }

        public void setSmallCommunityCode(String smallCommunityCode) {
            this.smallCommunityCode = smallCommunityCode;
        }

        public List<SmallCommunityListBean> getSmallCommunityList() {
            return smallCommunityList;
        }

        public void setSmallCommunityList(List<SmallCommunityListBean> smallCommunityList) {
            this.smallCommunityList = smallCommunityList;
        }

        public List<TypesBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypesBean> types) {
            this.types = types;
        }

        public static class SmallCommunityListBean {
            /**
             * smallCommunityCode : 0002001
             * smallCommunityName : 北京百花KASO创意公园
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

        public static class TypesBean {
            /**
             * name : 公共路灯
             * typeId : 1
             */

            private String name;
            private int typeId;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }
        }
    }
}
