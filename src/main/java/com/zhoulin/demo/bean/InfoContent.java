package com.zhoulin.demo.bean;

import java.io.Serializable;

public class InfoContent implements Serializable {

    private long id;

    private long infoId;

    private String content;

    public InfoContent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
