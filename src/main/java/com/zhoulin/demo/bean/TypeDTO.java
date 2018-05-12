package com.zhoulin.demo.bean;

import java.io.Serializable;
import java.util.List;

public class TypeDTO implements Serializable {

    private int typeId;

    private List<Object> objectList;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    @Override
    public String toString() {
        return "TypeDTO{" +
                "typeId=" + typeId +
                ", objectList=" + objectList +
                '}';
    }
}
