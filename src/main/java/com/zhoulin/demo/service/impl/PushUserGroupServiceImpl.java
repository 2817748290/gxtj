package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.InfoService;
import com.zhoulin.demo.service.PushUserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PushUserGroupServiceImpl implements PushUserGroupService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private InfoService infoService;

    @Autowired
    private RedisServiceImpl redisTemplate;

    /**
     * 检测各个类型新闻是否有增加
     * @return 检测到更新的新闻
     */
    @Override
    @Scheduled(fixedRate = 600000)
    public List<Integer> pushUserGroupByInfoCreate() {
        logger.info("每600秒执行一次。开始……");
        List<Integer> typeList = new ArrayList<>();

        List<TypeRelation> typeRelations = new ArrayList<>();

        int size[] = new int[10];
//        redisTemplate.delete("size_1");
//        redisTemplate.delete("size_2");
//        redisTemplate.delete("size_3");
//        redisTemplate.delete("size_4");
//        redisTemplate.delete("size_5");
//        redisTemplate.delete("size_6");
//        redisTemplate.delete("size_7");
//        redisTemplate.delete("size_8");
//        redisTemplate.delete("size_9");
        try {
            ValueOperations<String, Integer> operations = redisTemplate.getRedisTemplate().opsForValue();
            for (int i=0;i<10;i++){
                String key = "size_" + (i+1);
                boolean hasKey = redisTemplate.getRedisTemplate().hasKey(key);
                size[i] = typeRelationMapper.getCountByTypeId(i+1);
                logger.info("类型： " + (i+1) + " 新闻数量： " + size[i]);
                if (hasKey) {
                    logger.info("!!!" + operations.get(key));
                    if (size[i] == operations.get(key)){
                        logger.info("该类型新闻没有更新");
                    }else {
                        operations.set(key, size[i], 6, TimeUnit.HOURS);
                        logger.info("插入缓存" + size[i]);
                        typeList.add(i);
                    }
                }else{
                    operations.set(key, size[i], 6, TimeUnit.HOURS);
                    typeList.add(i);
                }
            }
            return typeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
