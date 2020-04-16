package com.zhjl.qihao.nearbyinteraction.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SearchNoteBean {
    @NotNull
    @Id
    private String name;

    @Generated(hash = 1046177440)
    public SearchNoteBean(@NotNull String name) {
        this.name = name;
    }

    @Generated(hash = 130993222)
    public SearchNoteBean() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
