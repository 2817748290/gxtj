package com.zhoulin.demo.bean.form;

import java.util.HashMap;
import java.util.Map;

/**
 * 新闻搜索请求参数结构体
 */
public class InfoSearch {

    private int start = 0;

    private int size = 100;

    private String titleSearch;

    private String authorSearch;

    private String description;

    private String content;

    private String sourceSiteSearch;

    private String keyWords;

    private Integer readsSearch;

    private Integer likesSearch;

    private int offset;     //分页偏移数（下一页就是查往后偏移offset个后，拿limit个）(就是从第几个开始拿)

    private int limit;      //前端需要的数据数

    private String sort;    //前端table表sortName属性定义的分类Id

    private String order = "desc";   //升序或倒序

    private int total;

    private Object rows;

    private int infoId;

    private String mutiContent;

    private String typeName;

    public Map result(){
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",rows);
        return map;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMutiContent() {
        return mutiContent;
    }

    public void setMutiContent(String mutiContent) {
        this.mutiContent = mutiContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public int getSize() {
        if (this.size < 1) {
            return 5;
        } else if (this.size > 100) {
            return 100;
        } else {
            return this.size;
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getAuthorSearch() {
        return authorSearch;
    }

    public void setAuthorSearch(String authorSearch) {
        this.authorSearch = authorSearch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceSiteSearch() {
        return sourceSiteSearch;
    }

    public void setSourceSiteSearch(String sourceSiteSearch) {
        this.sourceSiteSearch = sourceSiteSearch;
    }

    public Integer getReadsSearch() {
        return readsSearch;
    }

    public void setReadsSearch(Integer readsSearch) {
        this.readsSearch = readsSearch;
    }

    public Integer getLikesSearch() {
        return likesSearch;
    }

    public void setLikesSearch(Integer likesSearch) {
        this.likesSearch = likesSearch;
    }

    public InfoSearch() {

    }

    public InfoSearch(String mutiContent, String typeName) {
        this.mutiContent = mutiContent;
        this.typeName = typeName;
    }

    public InfoSearch(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "InfoSearch{" +
                "titleSearch='" + titleSearch + '\'' +
                ", authorSearch='" + authorSearch + '\'' +
                ", description='" + description + '\'' +
                ", sourceSiteSearch='" + sourceSiteSearch + '\'' +
                ", readsSearch=" + readsSearch +
                ", likesSearch=" + likesSearch +
                ", offset=" + offset +
                ", limit=" + limit +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", total=" + total +
                ", row=" + rows +
                ", infoId=" + infoId +
                '}';
    }
}
