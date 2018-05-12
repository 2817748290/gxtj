package com.zhoulin.demo.bean;

import java.io.Serializable;

public class InfoImage implements Serializable {

    private Integer id;

    private long infoId;

    private String image;

    public InfoImage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
