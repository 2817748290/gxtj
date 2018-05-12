package com.zhoulin.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textrank.TextRankKeyword;
import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;
import com.zhoulin.demo.mapper.InfoImageMapper;
import com.zhoulin.demo.mapper.RecessiveGroupMapper;
import com.zhoulin.demo.mapper.TypeMapper;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.*;
import com.zhoulin.demo.utils.CheckType;
import com.zhoulin.demo.utils.ReckonUserGroup;
import com.zhoulin.demo.utils.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 推送功能模块
 */
@Component
public class PushServiceImpl implements PushService {

    private static final Logger logger = LoggerFactory.getLogger(PushServiceImpl.class) ;

    @Autowired
    private ModService modService;

    @Autowired
    private RecessiveGroupMapper recessiveGroupMapper;

    @Autowired
    private InformationService informationService;

    @Autowired
    private LogInfoService logInfoService;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private InfoService infoService;

    @Autowired
    private JcsegService jcsegService;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private CheckType checkType;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserModService userModService;

    @Autowired
    private ReckonUserGroup reckonUserGroup;

    @Override
    public List<Info> pushInformation(long id) {

        InfoSearch infoSearch = new InfoSearch();

        String keywords = "";

        List<Info> informationList = new ArrayList<>();

        try {

            Information information = informationService.getInfoByInfoId(id);

            keywords = information.getKeyword();

            infoSearch.setMutiContent(keywords);

            ServiceMultiResult<Long> multiResult = modService.queryMuti(infoSearch);

            for (Long infoId:multiResult.getResult()) {
//                Info infor = objectMapper.readValue(rs, Info.class);
//                logger.info("最终关键词为 :  " + information.toString());
                Info infor = infoService.getInfoByInfoIdForImage(infoId);
                informationList.add(infor);
            }

//            for (Info info : informationList) {
//                InfoImage image = infoImageMapper.getInfoImageByInfoId(info.getInfoId());
//                info.setInfoImage(image);
//            }

            return informationList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<UserInfo> pushInfoForRecessiveGroup(Integer typeId){

        List<RecessiveGroup> recessiveGroups = new ArrayList<>();
        try {
            //获得用户群
            recessiveGroups = recessiveGroupMapper.getUserRecessiveGroup(typeId);
            Integer userId = 0;

            for (RecessiveGroup recessiveGroup: recessiveGroups) {
                userId = recessiveGroup.getUserId();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据 仔细阅读 && 历史阅读 分析 用户兴趣点 进行推送
     * 若是 用户仔细阅读记录 为空 则
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<Info> logAnalyzForPush(Integer userId){
        List<Long> kwInfoIds = new ArrayList<>();
        List<Long> mergeInfoIds = new ArrayList<>();
        List<Long> tyInfoIds = new ArrayList<>();

        //历史阅读记录
        List<LogInfo> logInfos = new ArrayList<>();

        //仔细阅读记录
        List<UserRead> userReadList = new ArrayList<>();
        List<TypeRelation> typeRelations = new ArrayList<>();
        TypeRelation typeRelation = new TypeRelation();
        InfoSearch infoSearch = new InfoSearch();
        List<Long> infoIds = new ArrayList<>();
        List<Integer> types = new ArrayList<>();
        List<Integer> typeIds = new ArrayList<>();
        String keywords = "";
        String keywordsString = "";

        try {
            logInfos = logInfoService.getLogInfoByUserId(userId);
            userReadList = userReadService.getUserReadByUserId(userId);
            UserMod userMod = userModService.getUserModByUserId(userId);

            if(userReadList.size()>0){
                for (UserRead userRead: userReadList) {
                    //查找对应新闻的详细信息
                    typeRelation = typeRelationMapper.getInfoByTRId(userRead.getInfoId());
                    Info info = infoService.getInfoByInfoId(userRead.getInfoId());
                    keywords = keywords + info.getKeyword();
                    types.add(typeRelation.getTypeId());
                    infoIds.add(userRead.getInfoId());
                    logger.info("调用 >>>> 仔细阅读记录！");
                }
            }else {
                for (LogInfo logInfo: logInfos) {
                    //查找对应新闻的详细信息
                    typeRelation = typeRelationMapper.getInfoByTRId(logInfo.getInfoId());
                    Info info = infoService.getInfoByInfoId(logInfo.getInfoId());
                    keywords = keywords + info.getKeyword();
                    types.add(typeRelation.getTypeId());
                    infoIds.add(logInfo.getInfoId());
                    logger.info("调用 >>>> 历史阅读记录！");
                }
            }

            List<String> finalKeywords = jcsegService.getKeywordsphrase(keywords);
//            List<String> finalKeywords = new TextRankKeyword().getKeyword("", keywords);
            for (String kw : finalKeywords) {
                keywordsString = keywordsString + kw + ",";
            }
            logger.info("最终关键词为 :  " + keywordsString);

            //根据关键词
            infoSearch.setMutiContent(keywordsString);
            //全局抓取
            ServiceMultiResult<Long> multiResult = modService.queryMuti(infoSearch);

            kwInfoIds = multiResult.getResult();

            List<Info> typeAndLogInfoList = new ArrayList<>();

            List<Long> typeInfoIds = new ArrayList<>();

            List<Integer> userTypes = checkType.getUserModNum(userMod);

            for (Integer typeId:userTypes) {
                Type type = typeMapper.getTypeByTypeId(typeId);
                InfoSearch infoSearch1 = new InfoSearch();
                infoSearch1.setTypeName(type.getTypeName());
                ServiceMultiResult<Long> typeRS = modService.queryTypeName(infoSearch1);

                List<Long> list = typeRS.getResult();

                typeInfoIds.addAll(list);
            }


            typeInfoIds.removeAll(kwInfoIds);

            kwInfoIds.addAll(typeInfoIds);


            for (long infoId:kwInfoIds) {
                Info info = infoService.getInfoByInfoIdForImage(infoId);
                typeAndLogInfoList.add(info);
            }

            return typeAndLogInfoList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据类型推送
     * @param typeId
     * @return
     * @throws Exception
     */
    @Override
    public List<Info> pushInfoByTypeId(Integer typeId) {

        List<TypeRelation> typeRelations = new ArrayList<>();

        List<Info> infoList = new ArrayList<>();

        try {
            typeRelations = typeRelationMapper.getInfoByTypeId(typeId);

            for (TypeRelation typeRelation : typeRelations) {
                Info info = infoService.getInfoByInfoId(typeRelation.getInfoId());
                info.setInfoContent(null);
                infoList.add(info);
            }
            return infoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 针对用户隐性组进行推送
     * @param userId
     * @return
     */
    @Override
    public List<Info> pushInfoByUserGroup(Integer userId) {

//        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        List<Info> finalInfoList = new ArrayList<>();

        try {
            List<Integer> groups = reckonUserGroup.reckonTypeArea(userId);
            for (int typeId:groups) {
                String key = "size_" + typeId;
                int size = typeRelationMapper.getCountByTypeId(typeId);
                Type type = typeMapper.getTypeByTypeId(typeId);
                List<Info> infoList = new ArrayList<>();
//                if (size != operations.get(key)){
                    List<Long> infoIds = modService.queryTypeName(new InfoSearch(type.getTypeName())).getResult();
                    for (Long infoId:infoIds) {
                        Info info = infoService.getInfoByInfoIdForImage(infoId);
                        infoList.add(info);
                    }
                    if (infoList.size()<6){
                        finalInfoList.addAll(infoList);
                    }else{
                        finalInfoList.addAll(infoList.subList(0,4));
                    }
//                    return infoList;
//                }
            }
            return finalInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取当前最新发布的 该类型 的新闻
     * @param typId
     * @return
     */
    public List<Info> InfoAnalyz(Integer typId) {

        List<TypeRelation> wantTypeList =  new ArrayList<>();
        List<Info> wantInfoList = new ArrayList<>();
        Info info = new Info();
        try {
            //获得新闻群
//            wantTypeList = typeRelationMapper.getInfoBytTypeId(typId);

            for (TypeRelation typeRelation:wantTypeList) {
                info = infoService.getInfoByInfoIdForImage(typeRelation.getInfoId());
                wantInfoList.add(info);
            }
            return wantInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
