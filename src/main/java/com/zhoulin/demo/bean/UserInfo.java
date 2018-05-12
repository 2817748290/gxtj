package com.zhoulin.demo.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zhoulin.demo.config.CustomJsonDateDeserializer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer userId;

    private String username;

    private String password;

    private String nickname;

    private String userImageUrl;

    private String userMail;

    private Integer userGroupId;

    private Integer userStatus;

//    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date lastLoginTime;

//    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date lastLoginOutTime;

//    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date gmtModified;

//    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date gmtCreate;

    private List<String> roles;

    private UserGroup userGroup;

    public UserInfo(Integer userId, String username, String password, String nickname, String userImageUrl, String userMail, Integer userGroupId, Integer userStatus, Date lastLoginTime, Date lastLoginOutTime, Date gmtModified, Date gmtCreate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userImageUrl = userImageUrl;
        this.userMail = userMail;
        this.userGroupId = userGroupId;
        this.userStatus = userStatus;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginOutTime = lastLoginOutTime;
        this.gmtModified = gmtModified;
        this.gmtCreate = gmtCreate;
    }

    public UserInfo() {
        super();
    }

    public UserInfo(String username, String password, String nickname, Integer userGroupId, Integer userStatus, Date gmtModified, Date gmtCreate) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userGroupId = userGroupId;
        this.userStatus = userStatus;
        this.gmtModified = gmtModified;
        this.gmtCreate = gmtCreate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl == null ? null : userImageUrl.trim();
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail == null ? null : userMail.trim();
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginOutTime() {
        return lastLoginOutTime;
    }

    public void setLastLoginOutTime(Date lastLoginOutTime) {
        this.lastLoginOutTime = lastLoginOutTime;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userImageUrl='" + userImageUrl + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userGroupId=" + userGroupId +
                ", userStatus=" + userStatus +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginOutTime=" + lastLoginOutTime +
                ", gmtModified=" + gmtModified +
                ", gmtCreate=" + gmtCreate +
                ", roles=" + roles +
                ", userGroup=" + userGroup +
                '}';
    }
}