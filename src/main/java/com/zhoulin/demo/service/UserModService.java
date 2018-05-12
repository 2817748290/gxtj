package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.UserMod;

public interface UserModService {

    public UserMod getUserModByUserId(Integer userId) throws Exception;

    public Integer updateUserMod(UserMod userMod) throws Exception;

    public Integer deleteUserModByUserId(Integer userId) throws Exception;

    public Integer addUserModForRegister(UserMod userMod) throws Exception;

}
