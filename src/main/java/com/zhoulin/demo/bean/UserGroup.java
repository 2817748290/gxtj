package com.zhoulin.demo.bean;

import java.io.Serializable;

public class UserGroup implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    private Integer userGroupId;

    private String groupName;

    private String permission;

    public UserGroup(Integer userGroupId, String groupName, String permission) {
        this.userGroupId = userGroupId;
        this.groupName = groupName;
        this.permission = permission;
    }

    public UserGroup() {
        super();
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }
}