package com.zhoulin.demo.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: YannYaoLaJi
 * @Description:分页对象
 * @Date Created in 19:57 2018/3/7
 */
public class BaseTableMessage {
    private String sort;    //前端table表sortName属性定义的分类Id
    private String order;   //升序或倒序
    private int offset;     //分页偏移数（下一页就是查往后偏移offset个后，拿limit个）(就是从第几个开始拿)
    private int limit;      //前端需要的数据数
    private int page;
    private int total;      //table需要后台返回两个值，total表示总数
    private Object rows;    //rows放返回数据
    private int status;
    private int tags;


    public Map result(){
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",rows);
//        map.put("status",status);
        return map;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTags() {
        return tags;
    }

    public void setTags(int tags) {
        this.tags = tags;
    }
}
