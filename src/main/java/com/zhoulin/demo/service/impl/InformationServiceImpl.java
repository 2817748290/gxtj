package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.mapper.InformationMapper;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.InformationService;
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
public class InformationServiceImpl implements InformationService {

    private static final Logger logger = LoggerFactory.getLogger(InformationServiceImpl.class);

    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private SearchService searchService;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private VerificationUtils verificationUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public InfoSearch getInitInfo(InfoSearch infoSearch) {

        return null;
    }

    @Override
    public Information getInfoByInfoId(long infoId){

        Information information = new Information();

        String key = "information_" + infoId;
        ValueOperations<String, Information> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey){
            information = operations.get(key);
            logger.info("从缓存中获取了资讯信息 >> " + information.toString());
            return information;
        }

        try {
            information = informationMapper.getInfoByInfoId(infoId);

//            operations.set(key, information, 6, TimeUnit.HOURS);
//            logger.info("资讯信息插入缓存 >> " + information.toString());
            return information;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer updateInformation(Information information){

        ValueOperations<String, Information> operations = redisTemplate.opsForValue();

        Integer updateStatus = -1;

        String key = "information_" + information.getId();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey){
            //缓存删除原有信息
//            redisTemplate.delete(key);
//            logger.info("从缓存中删除了资讯信息 >> " + information.toString());
        }

        try {
            updateStatus = verificationUtils.verification(informationMapper.updateInformation(information));
            if (updateStatus == 1){
                //更新索引数据
                searchService.index(information.getId());
//                operations.set(key, information, 6, TimeUnit.HOURS);
//                logger.info("资讯信息插入缓存 >> " + information.toString());
            }
            return updateStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return updateStatus;
        }
    }

    @Override
    public Integer deleteInformationById(long infoId){

        Integer delStatus = -1;

        String key = "information_" + infoId;

        boolean hasKey = redisTemplate.hasKey(key);


        try {
            delStatus = verificationUtils.verification(informationMapper.deleteInformationById(infoId));
            if (delStatus == 1){
                //删除索引数据
                searchService.remove(infoId);
                if (hasKey){
                    //缓存删除原有信息
                    redisTemplate.delete(key);
                    logger.info("从缓存中删除了资讯信息 >> " + infoId);
                }
            }
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return delStatus;
        }

    }

    @Override
    public List<Information> findAll() {

        List<Information> infoAllList = new ArrayList<>();
        TypeRelation typeRelation = new TypeRelation();
        // 从缓存中获取资讯列表 默认设置为 1
        String key = "infoAllList_" + 1;
        ValueOperations<String, List<Information>> operations = redisTemplate.opsForValue();

//        boolean hasKey = redisTemplate.hasKey(key);
//        if (hasKey) {
//            infoAllList = operations.get(key);
//            logger.info("从缓存中获取资讯列表 >> " + infoAllList.toString());
//            return infoAllList;
//        }

        try {
            infoAllList = informationMapper.findAll();

            //Redis有效时间设置为6个小时
//            operations.set(key, infoAllList, 6, TimeUnit.HOURS);
//            logger.info("资讯列表插入缓存 >> " + infoAllList.toString());
            for (Information information : infoAllList) {
                typeRelation = typeRelationMapper.getInfoByTRId(information.getId());
                information.setContent(typeRelation.getOnlyText());
            }
            return infoAllList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Information> findInfoByDate() {

        List<Information> dateInfoList = new ArrayList<>();

        // 从缓存中获取资讯列表 默认设置 1
        String key = "dateInfoList" + 1;
        ValueOperations<String, List<Information>> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            dateInfoList = operations.get(key);
            logger.info("从缓存中获取资讯列表 >> " + dateInfoList.toString());
            return dateInfoList;
        }

        try {
            dateInfoList = informationMapper.findInfoByDate();

            //Redis有效时间设置为6个小时
            operations.set(key, dateInfoList, 6, TimeUnit.HOURS);
            logger.info("资讯列表插入缓存 >> " + dateInfoList.toString());
            return dateInfoList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
