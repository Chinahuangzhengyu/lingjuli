package com.zhjl.qihao.freshshop.bean;

import java.util.List;

public class ShopTypeBean {

    /**
     * id : 5
     * parent_id : 3
     * level : 3_5
     * level_num : 2
     * name : 百货类
     * image :
     * code : 0102
     * sort : 1
     * op : <a>1234</a>
     * children : [{"id":25,"parent_id":5,"level":"3_5_25","level_num":3,"name":"家居类","image":"","code":"010201","sort":20,"op":"<a>1234<\/a>","children":[]}]
     */

    private int id;
    private int parent_id;
    private String level;
    private int level_num;
    private String name;
    private String image;
    private String code;
    private int sort;
    private String op;
    private List<ChildrenBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLevel_num() {
        return level_num;
    }

    public void setLevel_num(int level_num) {
        this.level_num = level_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 25
         * parent_id : 5
         * level : 3_5_25
         * level_num : 3
         * name : 家居类
         * image :
         * code : 010201
         * sort : 20
         * op : <a>1234</a>
         * children : []
         */

        private int id;
        private int parent_id;
        private String level;
        private int level_num;
        private String name;
        private String image;
        private String code;
        private int sort;
        private String op;
        private List<?> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getLevel_num() {
            return level_num;
        }

        public void setLevel_num(int level_num) {
            this.level_num = level_num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }
    }
}
