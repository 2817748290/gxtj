package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.UserLike;

import java.util.List;

public interface UserLikeService {
    
    public UserLike getUserLikeById(Integer userLikeId) throws Exception;

    public UserLike updateUserLike(UserLike userLike) throws Exception;

    public UserLike addUserLike(UserLike userLike) throws Exception;

    public boolean deleteUserLikeById(Integer userLikeId) throws Exception;

    public List<UserLike> findAll() throws Exception;



}
