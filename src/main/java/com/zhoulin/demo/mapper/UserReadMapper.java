package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.UserRead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserReadMapper {

    public List<UserRead> getUserReadByUserId(Integer userId) throws Exception;

    public Integer addUserRead(UserRead userRead) throws Exception;

    public Integer updateUserRead(UserRead userRead) throws Exception;

    public UserRead findUserReadExist(int userId, long infoId) throws Exception;

}
