package com.zhoulin.demo.bean;

import java.io.Serializable;

public class InfoRelation implements Serializable {

    private int infoId;

    private int reads;

    private int likes;

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getReads() {
        return reads;
    }

    public void setReads(int reads) {
        this.reads = reads;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "InfoRelation{" +
                "infoId=" + infoId +
                ", reads=" + reads +
                ", likes=" + likes +
                '}';
    }
}
