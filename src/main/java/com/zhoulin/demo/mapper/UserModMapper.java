package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.UserMod;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserModMapper {

    public UserMod getUserModByUserId(Integer userId) throws Exception;

    public Integer updateUserMod(UserMod userMod) throws Exception;

    public Integer deleteUserModByUserId(Integer userId) throws Exception;

    public Integer addUserModForRegister(UserMod userMod) throws Exception;


}
