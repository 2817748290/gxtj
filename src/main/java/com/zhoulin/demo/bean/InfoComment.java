package com.zhoulin.demo.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zhoulin.demo.config.CustomJsonDateDeserializer;

import java.util.Date;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 19:36 2018/3/7
 */
public class InfoComment {
    private Integer id;

    private String content;

    /**
     * 在哪篇文章下评论
     * */
    private Integer infoId;
    /**
     * 评论者用户id
     * */
    private Integer userId;

    /**
     * 父级评论id
     * */
    private Integer parentCommentId;

    /**
     * 回复哪个评论者的id
     */
    private Integer replyUserId;

    private Integer like;

    private Integer dislike;
    /**
     * 状态，0为启用 1为删除
     */
    private Integer status;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date createTime;

    ////////////
    private UserInfo userInfo;

    private UserInfo replyUser;

    public InfoComment() {
    }

    public InfoComment(Integer id, String content, Integer infoId, Integer userId, Integer parentCommentId, Integer replyUserId, Integer like, Integer dislike, Integer status, Date createTime, UserInfo userInfo, UserInfo replyUser) {
        this.id = id;
        this.content = content;
        this.infoId = infoId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.replyUserId = replyUserId;
        this.like = like;
        this.dislike = dislike;
        this.status = status;
        this.createTime = createTime;
        this.userInfo = userInfo;
        this.replyUser = replyUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Integer getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Integer replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(UserInfo replyUser) {
        this.replyUser = replyUser;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    @Override
    public String toString() {
        return "InfoComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", infoId=" + infoId +
                ", userId=" + userId +
                ", parentCommentId=" + parentCommentId +
                ", replyUserId=" + replyUserId +
                ", like=" + like +
                ", dislike=" + dislike +
                ", status=" + status +
                ", createTime=" + createTime +
                ", userInfo=" + userInfo +
                ", replyUser=" + replyUser +
                '}';
    }
}
