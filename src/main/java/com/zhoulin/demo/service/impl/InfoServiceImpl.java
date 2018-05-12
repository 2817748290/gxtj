package com.zhoulin.demo.service.impl;

import com.hankcs.hanlp.HanLP;
import com.zhoulin.demo.bean.Info;
import com.zhoulin.demo.bean.InfoContent;
import com.zhoulin.demo.bean.InfoImage;
import com.zhoulin.demo.mapper.InfoContentMapper;
import com.zhoulin.demo.mapper.InfoImageMapper;
import com.zhoulin.demo.mapper.InfoMapper;
import com.zhoulin.demo.service.InfoService;
import com.zhoulin.demo.service.JcsegService;
import com.zhoulin.demo.utils.VerificationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class InfoServiceImpl implements InfoService{

    private static final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private InfoContentMapper infoContentMapper;

    @Autowired
    private InfoImageMapper infoImageMapper;

    @Autowired
    private VerificationUtils verificationUtils;

    @Autowired
    private JcsegService jcsegService;

    @Autowired
    private RedisServiceImpl redisTemplate;

    /**
     * 完整版
     * @param infoId
     * @return
     */
    @Override
    public Info getInfoByInfoId(long infoId) {

        Info info = new Info();

        InfoContent infoContent = new InfoContent();

        InfoImage infoImage = new InfoImage();

        try {
            info = infoMapper.getInfoByInfoId(infoId);
            if(info!=null){
                infoContent = infoContentMapper.getInfoContentByInfoId(infoId);
                infoImage = infoImageMapper.getInfoImageByInfoId(infoId);

                info.setInfoContent(infoContent);
                info.setInfoImage(infoImage);
                return info;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 缩略图版
     * @param infoId
     * @return
     * @throws Exception
     */
    @Override
    public Info getInfoByInfoIdForImage(long infoId) throws Exception {

        Info info = new Info();

        InfoImage infoImage = new InfoImage();

        try {
            info = infoMapper.getInfoByInfoId(infoId);

            infoImage = infoImageMapper.getInfoImageByInfoId(info.getInfoId());

            info.setInfoImage(infoImage);

            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer updateInfo(Info info) {
        Integer updateStatus = -1;

        try {
            updateStatus = verificationUtils.verification(infoMapper.updateInfo(info));
            return updateStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return updateStatus;
        }

    }

    @Override
    public Integer deleteInfoById(long infoId) {
        return null;
    }

    @Override
    public List<Info> findAll(){
        List<Info> dateAllList = new ArrayList<>();
        try {
            dateAllList = infoMapper.findAll();
            return dateAllList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取最新资讯 根据时间
     * @param limitNum
     * @return
     */
    @Override
    public List<Info> findInfoByDate(int limitNum){

        int offset = (limitNum - 1) * 20;

        List<Info> dateList = new ArrayList<>();

//        InfoContent infoContent = new InfoContent();

        InfoImage infoImage = new InfoImage();

        try {
            dateList = infoMapper.findInfoByDate(offset);

            for (Info info : dateList) {
//                infoContent = infoContentMapper.getInfoContentByInfoId(info.getId());
                infoImage = infoImageMapper.getInfoImageByInfoId(info.getInfoId());

//                info.setInfoContent(infoContent);
                info.setInfoImage(infoImage);
                logger.info("资讯图片注入成功");
            }

            return dateList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Scheduled(fixedRate = 600000)
    public List<String> getHotWords(){

        List<Info> dateList = new ArrayList<>();
        List<String> hotWords = new ArrayList<>();

        String key = "hotWords_1";
        ValueOperations<String, List<String>> operations = redisTemplate.getRedisTemplate().opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.getRedisTemplate().hasKey(key);
        if (hasKey) {
            redisTemplate.getRedisTemplate().delete(key);
        }

        try {
            dateList = infoMapper.findInfoByDate(1);
            for (Info info:dateList) {
                String content = info.getTitle() + info.getDescription();
                List<String> phrase = jcsegService.getKeyphrase(content);
                if (phrase != null){
                    for (String word:phrase) {
                        hotWords.add(word);
                    }
                }
            }
            logger.info("HotWords:   " + hotWords);
            operations.set(key, hotWords, 2, TimeUnit.HOURS);
            return hotWords;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int allCount() {
        try {
            int allNum = infoMapper.allCount();
            return allNum;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
