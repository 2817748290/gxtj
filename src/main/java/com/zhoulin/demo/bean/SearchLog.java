package com.zhoulin.demo.bean;

import java.io.Serializable;
import java.util.Date;

public class SearchLog implements Serializable {

    private int slogId;

    private String searchContent;

    private Date lookTime;

    public int getSlogId() {
        return slogId;
    }

    public void setSlogId(int slogId) {
        this.slogId = slogId;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public Date getLookTime() {
        return lookTime;
    }

    public void setLookTime(Date lookTime) {
        this.lookTime = lookTime;
    }

    @Override
    public String toString() {
        return "SearchLog{" +
                "slogId=" + slogId +
                ", search_content='" + searchContent + '\'' +
                ", lookTime=" + lookTime +
                '}';
    }
}
