package com.zhoulin.demo.utils;

public class Relation {
    private Integer id;
    private Integer content;

    public Relation() {
    }

    public Relation(Integer id, Integer content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }
}
