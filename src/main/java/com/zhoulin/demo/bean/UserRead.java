package com.zhoulin.demo.bean;

import java.io.Serializable;
import java.util.Date;

public class UserRead implements Serializable {

    private int userReadId;

    private int userId;

    private long infoId;

    private Date lookTime;

    public int getUserReadId() {
        return userReadId;
    }

    public void setUserReadId(int userReadId) {
        this.userReadId = userReadId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public Date getLookTime() {
        return lookTime;
    }

    public void setLookTime(Date lookTime) {
        this.lookTime = lookTime;
    }

    @Override
    public String toString() {
        return "UserRead{" +
                "userReadId=" + userReadId +
                ", userId=" + userId +
                ", infoId=" + infoId +
                ", lookTime=" + lookTime +
                '}';
    }
}
