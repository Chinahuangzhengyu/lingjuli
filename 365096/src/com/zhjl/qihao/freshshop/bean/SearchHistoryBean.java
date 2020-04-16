package com.zhjl.qihao.freshshop.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SearchHistoryBean {
    @NotNull
    @Id
    private String name;

    @Generated(hash = 1923361211)
    public SearchHistoryBean(@NotNull String name) {
        this.name = name;
    }

    @Generated(hash = 1570282321)
    public SearchHistoryBean() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
