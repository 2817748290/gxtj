package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.UserLike;
import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.mapper.UserLikeMapper;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.UserLikeService;
import com.zhoulin.demo.service.search.SearchService;
import com.zhoulin.demo.utils.VerificationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserLikeServiceImpl implements UserLikeService {

    private static final Logger logger = LoggerFactory.getLogger(UserLikeServiceImpl.class);

    @Autowired
    private UserLikeMapper userLikeMapper;


    @Override
    public UserLike getUserLikeById(Integer userLikeId) throws Exception {
        UserLike userLike = new UserLike();
        try {
            userLike = userLikeMapper.getUserLikeById(userLikeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userLike;
    }

    @Override
    public UserLike updateUserLike(UserLike userLike){

        UserLike result = null;
        try {
            userLikeMapper.updateUserLike(userLike);
            result = this.getUserLikeById(userLike.getUserLikeId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public UserLike addUserLike(UserLike userLike) throws Exception {
        UserLike result = null;
        try {
            userLikeMapper.addUserLike(userLike);
            result = this.getUserLikeById(userLike.getUserLikeId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteUserLikeById(Integer userLikeId){

        boolean state = false;
        try {
            state = userLikeMapper.deleteUserLikeById(userLikeId) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public List<UserLike> findAll() {

        List<UserLike> userLikeList = new ArrayList<>();

        try {
            userLikeList = userLikeMapper.findAll();

            return userLikeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
