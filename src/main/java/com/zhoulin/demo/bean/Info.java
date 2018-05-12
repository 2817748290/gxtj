package com.zhoulin.demo.bean;

import java.io.Serializable;
import java.util.Date;

public class Info implements Serializable {

    private Long id;

    private long infoId;

    private String title;

    private String author;

    private String description;

    private Integer reads;

    private Integer likes;

    private String keyword;

    private Integer score;

    private String sourceUrl;

    private String sourceSite;

    private Date publishDate;

    private InfoContent infoContent;

    private InfoImage infoImage;

    private boolean isRead;

    public Info() {
    }

    public Info(Long infoId) {
        this.infoId = infoId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public InfoContent getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(InfoContent infoContent) {
        this.infoContent = infoContent;
    }

    public InfoImage getInfoImage() {
        return infoImage;
    }

    public void setInfoImage(InfoImage infoImage) {
        this.infoImage = infoImage;
    }

    public Long getId() {
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", infoId=" + infoId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", reads=" + reads +
                ", likes=" + likes +
                ", keyword='" + keyword + '\'' +
                ", score=" + score +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", sourceSite='" + sourceSite + '\'' +
                ", publishDate=" + publishDate +
                ", infoContent=" + infoContent +
                ", infoImage=" + infoImage +
                '}';
    }
}
