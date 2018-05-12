package com.zhoulin.demo.bean;

import java.io.Serializable;

public class UserLike implements Serializable {

    private Integer userLikeId;

    private Integer userId;

    private Integer type;

    private Integer infoId;

    public UserLike() {
    }

    public UserLike(Integer userLikeId, Integer userId, Integer type, Integer infoId) {
        this.userLikeId = userLikeId;
        this.userId = userId;
        this.type = type;
        this.infoId = infoId;
    }

    public Integer getUserLikeId() {
        return userLikeId;
    }

    public void setUserLikeId(Integer userLikeId) {
        this.userLikeId = userLikeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }
}
