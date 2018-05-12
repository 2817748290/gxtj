package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.UserLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserLikeMapper {

    public UserLike getUserLikeById(Integer userLikeId) throws Exception;

    public Integer updateUserLike(UserLike userLike) throws Exception;

    public Integer deleteUserLikeById(Integer userLikeId) throws Exception;

    public List<UserLike> findAll() throws Exception;

    public Integer addUserLike(UserLike userLike) throws Exception;

}
