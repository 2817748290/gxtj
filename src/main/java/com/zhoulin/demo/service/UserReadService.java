package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.UserRead;

import java.util.List;

public interface UserReadService {

    public List<UserRead> getUserReadByUserId(Integer userId) throws Exception;

    public Integer addUserRead(UserRead userRead) throws Exception;

    public Integer updateUserRead(UserRead userRead) throws Exception;

    public UserRead findUserReadExist(int userId, long infoId) throws Exception;
}
