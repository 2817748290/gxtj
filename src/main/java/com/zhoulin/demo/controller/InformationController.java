package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.config.security.JwtTokenUtil;
import com.zhoulin.demo.mapper.InfoContentMapper;
import com.zhoulin.demo.mapper.InfoImageMapper;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.*;
import com.zhoulin.demo.service.impl.RedisServiceImpl;
import com.zhoulin.demo.utils.CheckType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/public/information")
public class InformationController {

    private final static Logger logger = LoggerFactory.getLogger(InformationController.class);

    @Autowired
    private InformationService informationService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private UserModService userModService;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private InfoImageMapper infoImageMapper;

    @Autowired
    private InfoContentMapper infoContentMapper;

    @Autowired
    private LogInfoService logInfoService;

    @Autowired
    private CheckType checkType;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisServiceImpl redisTemplate;

    /**
     * 根据【资讯编号】获取对应资讯信息
     * @param infoId 资讯编号
     * @return
     */
    @RequestMapping(value = "/getInfoByInfoId/{infoId}", method = RequestMethod.POST)
    @ResponseBody
    public Message getUserInfoById(@PathVariable(value = "infoId") Long infoId, HttpServletRequest request){

        List<Long> infoIdList = new ArrayList<>();

        Integer addLStatus = 0;

        Integer upLStatus = 0;

        Info info = new Info();

        TypeRelation typeRelation = new TypeRelation();

//        UserMod userMod = new UserMod();

        List<LogInfo> logInfos = new ArrayList<>();

        LogInfo logInfo = new LogInfo();

        try {
            info = infoService.getInfoByInfoId(infoId);

            //如果用户登录后查看新闻信息
            if (request.getHeader("token") != null){
                logger.info("用户已经登录！");
                UserInfo userInfo = jwtTokenUtil.parse(request.getHeader("token"));

                Integer userId = userInfo.getUserId();

//                userMod = userModService.getUserModByUserId(userId);

                typeRelation = typeRelationMapper.getInfoByTRId(infoId);

                logInfos = logInfoService.getLogInfoByUserId(userId);

                for (LogInfo log:logInfos) {
                    infoIdList.add(log.getInfoId());
                }

                if (infoIdList.contains(infoId)){
                    logInfo.setLookTime(new Date());
                    logInfo.setEndTime(new Date());
                    upLStatus = logInfoService.updateLogInfo(logInfo);
                    if (upLStatus==1){
                        logger.info("浏览日志>>>>>修改成功");
                    }
                }else {
                    logInfo.setUserId(userId);
                    logInfo.setInfoId(infoId);
                    logInfo.setLookTime(new Date());
                    logInfo.setEndTime(new Date());

                    //通过日志 判断文章是否已读
                    boolean isRead = logInfoService.isReadInfo(userId, infoId);

                    info.setRead(isRead);

                    //日志插入
                    addLStatus = logInfoService.addLogInfo(logInfo);
                    if (addLStatus==1){
                        logger.info("浏览日志>>>>>插入成功");
                    }
                }

//                userMod = checkType.checkInfoType(userMod, typeRelation.getTypeId());
//
//                //用户模型修改
//                userModService.updateUserMod(userMod);

                info.setReads(info.getReads() + 1);

                //文章浏览数更新
                infoService.updateInfo(info);

            }

            if (info != null){
                return new Message(Message.SUCCESS,"获取资讯--成功",info);
            } else {
                return new Message(Message.FAILURE,"获取资讯--失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯异常！",e);
        }
    }

    /**
     * 根据资讯发布时间获取资讯（ 20 条）
     * @return
     */
    @RequestMapping(value = "/findInfoByDate/{page}",method = RequestMethod.GET)
    @ResponseBody
    public Message findInfoByDate(@PathVariable(value = "page") int page){

        List<Info> dateInfoList = new ArrayList<>();
        try {
            dateInfoList = infoService.findInfoByDate(page);
            if (dateInfoList.size() < 1){
                return new Message(Message.FAILURE,"获取资讯--失败","检查数据源");
            }else {
                return new Message(Message.SUCCESS,"获取资讯--成功",dateInfoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯异常！",e);
        }
    }

    /**
     * 获取所有资讯信息
     * @return
     */
    @RequestMapping(value = "/findAllInfo",method = RequestMethod.GET)
    @ResponseBody
    public Message findAll(){
        List<Information> infoAllList = new ArrayList<>();
        try {
            infoAllList = informationService.findAll();
            if (infoAllList.size() < 1){
                return new Message(Message.FAILURE,"获取资讯--失败","检查数据源");
            }else {
                return new Message(Message.SUCCESS,"获取资讯--成功",infoAllList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯异常！",e);
        }
    }

    /**
     * 更新新闻 点赞数 浏览数
     * @param info
     * @return
     */
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    public Message updateInfo(@RequestBody Info info){

        try {
            Integer upStatus = infoService.updateInfo(info);

            if (upStatus == 1){
                return new Message(Message.SUCCESS,"修改新闻>>>>成功",upStatus);
            } else if (upStatus == 0){
                return new Message(Message.SUCCESS,"修改新闻>>>>失败",upStatus);
            }
            return new Message(Message.SUCCESS,"修改新闻>>>>异常",upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.SUCCESS,"修改新闻>>>>异常",e.getMessage());
        }
    }

    /**
     * 获取热词
     * @return
     */
    @RequestMapping(value = "/getHotWords", method = RequestMethod.GET)
    @ResponseBody
    public Message getHotWords(){

        String key = "hotWords_1";
        ValueOperations<String, List<String>> operations = redisTemplate.getRedisTemplate().opsForValue();
        List<String> hotWords = operations.get(key);

        logger.info("获取实时热词>>>" + hotWords.toString());

        return new Message(Message.SUCCESS,"获取实时热词>>>>成功",hotWords);

    }
    /**
     * 获取新闻总条数
     * @return
     */
    @RequestMapping(value = "/getAllCount", method = RequestMethod.GET)
    @ResponseBody
    public Message getAllCount(){

        int allNum = 0;
        try {
            allNum = infoService.allCount();
            return new Message(Message.SUCCESS,"获取资讯总数>>>>>成功",allNum);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯总数>>>>>>失败",allNum);
        }
    }
    /**
     * 点赞
     * @return
     */
    @RequestMapping(value = "/likeInformation", method = RequestMethod.POST)
    @ResponseBody
    public Message likeInformation(@RequestBody Info info){

        int allNum = 0;
        try {
            allNum = infoService.allCount();
            return new Message(Message.SUCCESS,"获取资讯总数>>>>>成功",allNum);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯总数>>>>>>失败",allNum);
        }
    }
    /**
     * 点踩
     * @return
     */
    @RequestMapping(value = "/dislikeInformation", method = RequestMethod.POST)
    @ResponseBody
    public Message dislikeInformation(@RequestBody Info info){
//        informationService.updateInformation();
        int allNum = 0;
        try {
            allNum = infoService.allCount();
            return new Message(Message.SUCCESS,"获取资讯总数>>>>>成功",allNum);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯总数>>>>>>失败",allNum);
        }
    }
}
