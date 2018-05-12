package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.UserMod;
import com.zhoulin.demo.mapper.UserModMapper;
import com.zhoulin.demo.service.UserModService;
import com.zhoulin.demo.utils.VerificationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 用户阅读模型
 */
@Component
public class UserModServiceImpl implements UserModService {

    private static final Logger logger = LoggerFactory.getLogger(UserModServiceImpl.class);

    @Autowired
    private UserModMapper userModMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VerificationUtils verificationUtils;

    @Override
    public UserMod getUserModByUserId(Integer userId) {

        UserMod userMod = new UserMod();

        String key = "userMod_" + userId;
//        ValueOperations<String, UserMod> operations = redisTemplate.opsForValue();

        boolean haskey = redisTemplate.hasKey(key);

//        if (haskey){
//            userMod = operations.get(key);
//            logger.info("从缓存中取出用户模型>>>>>" + userMod);
//            return userMod;
//        }

        try {
            userMod = userModMapper.getUserModByUserId(userId);
//            operations.set(key, userMod, 6, TimeUnit.HOURS);
//            logger.info("缓存中插入用户模型>>>>>" + userMod);
            return userMod;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Integer updateUserMod(UserMod userMod) {

        Integer upStatus = -1;

//        String key = "userMod_" + userMod.getUserId();
//        ValueOperations<String, UserMod> operations = redisTemplate.opsForValue();

//        boolean hasKey = redisTemplate.hasKey(key);

        try {
            upStatus = verificationUtils.verification(userModMapper.updateUserMod(userMod));

//            if (upStatus==1){
//                if (hasKey){
//                    redisTemplate.delete(key);
//                    logger.info("从缓存中删除用户模型>>>>>" + userMod.getUserId());
//                }
//                operations.set(key, userMod, 6, TimeUnit.HOURS);
//                logger.info("从缓存中重新插入用户模型>>>>>" + userMod);
//            }
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Integer deleteUserModByUserId(Integer userId) {

        Integer delStatus = -1;

        String key = "userMod_" + userId;

        boolean hasKey = redisTemplate.hasKey(key);


        try {
            delStatus = verificationUtils.verification(userModMapper.deleteUserModByUserId(userId));
            if (delStatus == 1){
                if (hasKey){
                    //缓存删除原有信息
                    redisTemplate.delete(key);
                    logger.info("从缓存中删除用户模型 >> " + userId);
                }
            }
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return delStatus;
        }
    }

    @Override
    public Integer addUserModForRegister(UserMod userMod) {

        Integer addStatus = -1;

        try {
            addStatus = verificationUtils.verification(userModMapper.addUserModForRegister(userMod));
            logger.info("添加用户模型 >> " + addStatus);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return addStatus;
        }

    }
}
