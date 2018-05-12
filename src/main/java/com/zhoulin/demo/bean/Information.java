package com.zhoulin.demo.bean;

import java.io.Serializable;
import java.util.Date;

public class Information implements Serializable{

    private Long id;

    private String title;

    private String author;

    private String description;

    private Integer reads;

    private Integer likes;

    private String keyword;

    private Integer score;

    private String content;

    private String sourceUrl;

    private String sourceSite;

    private Date publishDate;

    private Date contentTime;

    private Date feedTime;

    private String typeName;

    private String userAddFlag;

    private InfoImage infoImage;

    public Information() {
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", reads=" + reads +
                ", likes=" + likes +
                ", keyword='" + keyword + '\'' +
                ", score=" + score +
                ", content='" + content + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", sourceSite='" + sourceSite + '\'' +
                ", publishDate=" + publishDate +
                ", contentTime=" + contentTime +
                ", feedTime=" + feedTime +
                ", typeName='" + typeName + '\'' +
                ", userAddFlag='" + userAddFlag + '\'' +
                ", infoImage=" + infoImage +
                '}';
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public InfoImage getInfoImage() {
        return infoImage;
    }

    public void setInfoImage(InfoImage infoImage) {
        this.infoImage = infoImage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getContentTime() {
        return contentTime;
    }

    public void setContentTime(Date contentTime) {
        this.contentTime = contentTime;
    }

    public Date getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(Date feedTime) {
        this.feedTime = feedTime;
    }

    public String getUserAddFlag() {
        return userAddFlag;
    }

    public void setUserAddFlag(String userAddFlag) {
        this.userAddFlag = userAddFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReads() {
        return reads;
    }

    public void setReads(Integer reads) {
        this.reads = reads;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

}
