package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.HumanListenerService;
import com.zhoulin.demo.service.LogInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 时间阈值
 */
@Component
public class HumanListenerServiceImpl implements HumanListenerService {

    private static final Logger logger = LoggerFactory.getLogger(HumanListenerServiceImpl.class);

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private LogInfoService logInfoService;


    @Override
    public HashMap<Integer, List<LogInfoDTO>> userReadTime(int userId) {
        List<LogInfoDTO> logInfoDTOS = new ArrayList<>();
        List<Integer> types = new ArrayList<>();
        try {
            List<LogInfo> logInfos = logInfoService.getLogInfoByUserId(userId);
            for (LogInfo logInfo:logInfos) {
                //得到阅读时间
                long timeDiff =logInfo.getEndTime().getTime() - logInfo.getLookTime().getTime();
                //获得新闻类型
                TypeRelation typeRelation = typeRelationMapper.getInfoByTRId(logInfo.getInfoId());

                //类型列表
                types.add(typeRelation.getTypeId());

                //DTO封装
                LogInfoDTO logInfoDTO = new LogInfoDTO(logInfo, typeRelation.getTypeId(), timeDiff);
                logInfoDTOS.add(logInfoDTO);
            }

            HashSet hashSet = new HashSet(types);
            types.clear();
            types.addAll(hashSet);

            HashMap hashMap = new HashMap<Integer, List<LogInfoDTO>>();

            //动态生成list
            for (int i=0;i<types.size();i++){
                List<LogInfoDTO> list = new ArrayList<>();
                hashMap.put(types.get(i), list);
            }

            //判断归类 生成list
            for (LogInfoDTO logInfoDTO :logInfoDTOS) {
                for (int i=0;i<types.size();i++){
                    if(logInfoDTO.getTypeId() == types.get(i)){
                        List<LogInfoDTO> list = (List<LogInfoDTO>) hashMap.get(types.get(i));
                        list.add(logInfoDTO);
                        hashMap.put(logInfoDTO.getTypeId(), list);
                    }
                }
            }
//            logger.info("typeList>>>" + types.get(0).toString() + types.get(1).toString());
//            List<LogInfoDTO> list_0 = (List<LogInfoDTO>) hashMap.get(0);
//            List<LogInfoDTO> list_1 = (List<LogInfoDTO>) hashMap.get(1);
//            logger.info("hashMap   " + list_0.get(0).toString() + list_0.get(1).toString());
//            logger.info("hashMap   " + list_1.get(0).toString());

//            return logInfoDTOS;
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}