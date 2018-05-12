package com.zhoulin.demo.bean;

import java.io.Serializable;

public class RecessiveGroup implements Serializable{

    private Integer userId;

    private Integer typeId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RecessiveGroup{" +
                "userId=" + userId +
                ", typeId=" + typeId +
                '}';
    }
}
