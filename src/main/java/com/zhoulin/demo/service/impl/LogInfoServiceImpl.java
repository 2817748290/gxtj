package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.LogInfo;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.mapper.LogInfoMapper;
import com.zhoulin.demo.service.LogInfoService;
import com.zhoulin.demo.utils.VerificationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class LogInfoServiceImpl implements LogInfoService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInfoServiceImpl.class);

    @Autowired
    private LogInfoMapper logInfoMapper;

    @Autowired
    private VerificationUtils verificationUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户登录时就调用 后台直接进行分析
     * @param userId
     * @return
     */
    @Override
    public List<LogInfo> getLogInfoByUserId(Integer userId){

        // 从缓存中获取列表
//        String key = "infoList_" + new Date();
//        ValueOperations<String, List<LogInfo>> operations = redisTemplate.opsForValue();

        // 缓存存在
//        boolean hasKey = redisTemplate.hasKey(key);
//        if (hasKey) {
//            List<LogInfo> infoList = operations.get(key);
//            LOGGER.info("从缓存中获取了浏览日志列表 >> " + infoList.toString());
//            return infoList;
//        }

        try {
            List<LogInfo> infoList = logInfoMapper.getLogInfoByUserId(userId);

//            operations.set(key, infoList, 5, TimeUnit.HOURS);
//            LOGGER.info("浏览日志列表插入缓存 >> " + infoList.toString());
            return infoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public LogInfo getLogInfoByLogId(Integer logId){

        LogInfo logInfo = new LogInfo();

        // 从缓存中获取浏览日志列表
        String key = "logInfo_" + logId;
        ValueOperations<String, LogInfo> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            logInfo = operations.get(key);
            LOGGER.info("从缓存中获取了浏览日志列表 >> " + logInfo.toString());
            return logInfo;
        }

        try {

            logInfo = logInfoMapper.getLogInfoByLogId(logId);

            // 插入缓存
            operations.set(key, logInfo, 10, TimeUnit.SECONDS);
            LOGGER.info("浏览日志列表插入缓存 >> " + logInfo.toString());

            return logInfo;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    @Override
    public LogInfo getLogInfoByInfoId(Integer infoId) {

        LogInfo logInfo = new LogInfo();

        try {
            logInfo = logInfoMapper.getLogInfoByInfoId(infoId);
            return logInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Integer updateLogInfo(LogInfo logInfo){

        Integer updateStatus = -1;

        String key = "logInfo_" + logInfo.getLogId();
        boolean hasKey = redisTemplate.hasKey(key);

        if (hasKey) {
            redisTemplate.delete(key);

            LOGGER.info("从缓存中删除浏览日志列表 >> " + logInfo.toString());
        }

        try {

            updateStatus = verificationUtils.verification(logInfoMapper.updateLogInfo(logInfo));

            return updateStatus;

        } catch (Exception e) {

            e.printStackTrace();

            return updateStatus;
        }
    }

    @Override
    public Integer deleteLogInfoById(Integer logId){

        Integer delStatus = -1;

        String key = "logInfo_" + logId;
        boolean hasKey = redisTemplate.hasKey(key);

        if(hasKey){

            redisTemplate.delete(key);

            LOGGER.info("从缓存中删除浏览日志列表 >> " + logId);

        }

        try {

            delStatus = verificationUtils.verification(logInfoMapper.deleteLogInfoById(logId));

            return delStatus;

        } catch (Exception e) {

            e.printStackTrace();

            return delStatus;
        }
    }

    @Override
    public Integer addLogInfo(LogInfo logInfo){

        Integer addStatus = -1;

        try {

            addStatus = verificationUtils.verification(logInfoMapper.addLogInfo(logInfo));

            return addStatus;

        } catch (Exception e) {

            e.printStackTrace();

            return addStatus;
        }
    }

    @Override
    public List<LogInfo> getLogInfoNowadays(Integer userId) {

        List<LogInfo> userlogs = new ArrayList<>();

        try {
            userlogs = logInfoMapper.getLogInfoNowadays(userId);

            return userlogs;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isReadInfo(Integer userId, Long infoId) {

        boolean isRead = false;
        try {
            int count = logInfoMapper.isReadInfo(userId, infoId);
            if (count>0){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return isRead;
        }


    }

    /**
     * 去重
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<Integer> getDistinctTypeByUserId(int userId){

        List<Integer> typeIdList = new ArrayList<>();

        try {
            typeIdList = logInfoMapper.getDistinctTypeByUserId(userId);
            return typeIdList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
