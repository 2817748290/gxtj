package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.UserGroup;
import com.zhoulin.demo.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserGroupMapper {

    public UserGroup getByUserId(Integer userGroupId);

}
