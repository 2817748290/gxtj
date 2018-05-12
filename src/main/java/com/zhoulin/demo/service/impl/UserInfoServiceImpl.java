package com.zhoulin.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.mapper.UserInfoMapper;
import com.zhoulin.demo.service.MailService;
import com.zhoulin.demo.service.UserInfoService;
import com.zhoulin.demo.utils.RedisTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisTokenManager redisTokenManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<UserInfo> getAllUserInfo(){

        // 从缓存中获取用户列表 默认设置 1
        String key = "userInfoList_" + 1;
        ValueOperations<String, List<UserInfo>> operations = redisTemplate.opsForValue();

        // 缓存存在
//        boolean hasKey = redisTemplate.hasKey(key);
//        if (hasKey) {
//            List<UserInfo> userInfoList = operations.get(key);
//            LOGGER.info("serInfoMapper.getAllUserInfo(): 从缓存中获取了用户列表 >> " + userInfoList.toString());
//            return userInfoList;
//        }

        try {
            List<UserInfo> userInfoList = userInfoMapper.getAllUserInfo();
            //Redis有效时间设置为6个小时
//            operations.set(key, userInfoList, 6, TimeUnit.HOURS);
//            LOGGER.info("serInfoMapper.getAllUserInfo(): 用户列表插入缓存 >> " + userInfoList.toString());
            return userInfoList;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("获取用户列表失败");
            return null;
        }
    }

    @Override
    public UserInfo getUserInfoById(Integer userId){

        UserInfo userInfo = new UserInfo();

        // 从缓存中获取用户信息
        String key = "userInfo_" + userId;
        ValueOperations<String, UserInfo> operations = redisTemplate.opsForValue();

        // 缓存存在
//        boolean hasKey = redisTemplate.hasKey(key);
//        if (hasKey) {
//            userInfo = operations.get(key);
//            LOGGER.info("userInfoMapper.getUserInfoById(userId) : 从缓存中获取了用户信息 >> " + userInfo.toString());
//            return userInfo;
//        }

        try {
            userInfo = userInfoMapper.getUserInfoById(userId);

            // 插入缓存
//            operations.set(key, userInfo, 10, TimeUnit.SECONDS);
//            LOGGER.info("userInfoMapper.getUserInfoById(userId) : 用户信息插入缓存 >> " + userInfo.toString());

            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("获取对应用户信息失败");
            return null;
        }

    }

    /**
     * 新增/注册用户
     * @param userInfo
     * @return addStatus
     *  0: 插入数据失败
     *  1: 插入数据成功
     * -1: 插入数据出现异常
     */
    @Override
    public Integer addUserInfo(UserInfo userInfo) {

        Integer addStatus = 0;

        try {
            addStatus = userInfoMapper.addUserInfo(userInfo);
            if (addStatus == 1){
                addStatus = 1;
            }else if (addStatus == 0){
                addStatus = 0;
            }
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("新增用户信息失败");
            return -1;
        }
    }

    /**
     * 修改用户
     * @param userInfo
     * @return updateStatus
     *  0: 修改数据失败
     *  1: 修改数据成功
     * -1: 修改数据出现异常
     */
    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {

//        ValueOperations<String, UserInfo> operations = redisTemplate.opsForValue();

        Integer updateStatus = 0;

//        String key = "userInfo_" + userInfo.getUserId();
//        boolean hasKey = redisTemplate.hasKey(key);
//
//        if (hasKey) {
//            redisTemplate.delete(key);
//            LOGGER.info("userInfoMapper.updateUserInfo() : 从缓存中删除用户信息 >> " + userInfo.toString());
//        }

        try {
            updateStatus = userInfoMapper.updateUserInfo(userInfo);
            if (updateStatus == 1){
                updateStatus = 1;
//                operations.set(key, userInfo, 6, TimeUnit.HOURS);
//                LOGGER.info("userInfoMapper.getUserInfoById(userId) : 用户信息插入缓存 >> " + userInfo.toString());
                return userInfo;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("修改用户信息失败");
            return null;
        }
    }

    /**
     *
     * @param userId
     * @return delState
     *
     */
    @Override
    public Integer deleteUserInfoById(Integer userId) {

        Integer delState = 0;

        String key = "userInfo_" + userId;
        boolean hasKey = redisTemplate.hasKey(key);

        try {
            delState = userInfoMapper.deleteUserInfoById(userId);
            if (delState == 1){
                delState = 1;
                if(hasKey){
                    redisTemplate.delete(key);
                    LOGGER.info("userInfoMapper.updateUserInfo() : 从缓存中删除用户信息 >> " + userId);

                }
            }else if (delState == 0){
                delState = 0;
            }
            return delState;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public UserInfo findUserPW(String username, String password) {

        UserInfo userInfo = new UserInfo();

        try {
            userInfo = userInfoMapper.findUserPW(username, password);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserInfo getUserByUsername(String username){

        UserInfo userInfo = new UserInfo();

        userInfo = userInfoMapper.getUserByUsername(username);

        return userInfo;
    }

//    @Override
//    public Integer UserInfoSignUp(UserInfo userInfo) throws Exception {
//
//        String email = userInfo.getUserMail();
//        if (userInfoMapper.existEmail(email)){
//            LOGGER.error("用户注册，邮箱已注册:" + email);
//            return 0;
//        }
//        sendValidateEmail(userInfo);
//        return 1;
//
//    }
//
//    public void sendValidateEmail(UserInfo user){
//        String token = redisTokenManager.getTokenOfSignUp(user);
//        LOGGER.error("用户注册，准备发送邮件：User:" + JSONObject.toJSONString(user) + ", Token: " + token);
//        mailService.userValidate(user, token);
//    }
}
