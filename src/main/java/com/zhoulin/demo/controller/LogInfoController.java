package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.mapper.InfoImageMapper;
import com.zhoulin.demo.service.InfoService;
import com.zhoulin.demo.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/logInfo")
public class LogInfoController {

    @Autowired
    private LogInfoService logInfoService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private InfoImageMapper infoImageMapper;

    /**
     * 获取用户对应浏览信息
     * @return
     */
    @RequestMapping(value = "/getLogInfos", method = RequestMethod.GET)
    @ResponseBody
    public Message getLogInfos(){

        List<LogInfo> infoList = new ArrayList<>();

        //token中取出用户id
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        int userId = userInfo.getUserId();

        try {
            infoList = logInfoService.getLogInfoByUserId(userId);

            return new Message(Message.SUCCESS, "获取用户浏览记录>>>>>成功", infoList);

        } catch (Exception e) {
            return new Message(Message.ERROR, "获取用户浏览记录>>>>>失败", e);
        }

    }

    /**
     * 添加浏览记录
     * @param logInfo
     * @return
     */
    @RequestMapping(value = "/addLogInfo", method = RequestMethod.POST)
    @ResponseBody
    public Message addLogInfo(@RequestBody LogInfo logInfo){

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        int userId = userInfo.getUserId();

        //创建浏览时间
        Date lookTime = new Date();

        logInfo.setUserId(userId);
        logInfo.setLookTime(lookTime);
        logInfo.setEndTime(lookTime);

        Integer addStatus = 0;
        try {
            addStatus = logInfoService.addLogInfo(logInfo);
            if (addStatus == 1){
                return new Message(Message.SUCCESS,"添加用户浏览记录>>>>>成功",addStatus);
            } else if(addStatus == 0){
                return new Message(Message.FAILURE,"添加用户浏览记录>>>>>失败",addStatus);
            } else {
                return new Message(Message.ERROR,"添加用户浏览记录>>>>>异常",addStatus);
            }
        } catch (Exception e) {
            return new Message(Message.ERROR,"添加用户浏览记录>>>>>异常",e);
        }

    }


    /**
     * 用户浏览的新闻
     * @return
     */
    @RequestMapping(value = "/getLogInfoNowadays", method = RequestMethod.POST)
    @ResponseBody
    public Message getLogInfoNowadays(){

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        int userId = userInfo.getUserId();

        Info info = new Info();

        InfoImage infoImage = new InfoImage();

        List<Info> infoList = new ArrayList<>();

        List<LogInfo> logInfos = new ArrayList<>();

        try {
            logInfos = logInfoService.getLogInfoNowadays(userId);

            for (LogInfo log : logInfos) {
                info = infoService.getInfoByInfoId(log.getInfoId());
                if (info != null ){
                    infoImage = infoImageMapper.getInfoImageByInfoId(log.getInfoId());
                    info.setInfoImage(infoImage);
                    infoList.add(info);
                }
            }

            if (infoList.size()<1){
                return new Message(Message.FAILURE,"获取用户浏览文章>>>>>成功","用户未浏览过文章！");
            }

            return new Message(Message.SUCCESS,"获取用户浏览文章>>>>>成功",infoList);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取用户浏览文章>>>>>异常",e.getMessage());
        }

    }

    /**
     * 实时更新阅读时间
     */
    @RequestMapping(value = "/getNewEndTime", method = RequestMethod.POST)
    @ResponseBody
    public Message getNewEndTime(@RequestBody LogInfo logInfo){
        int upStatus = 0;
        try {
            upStatus = logInfoService.updateLogInfo(logInfo);
            if (upStatus == 1){
                return new Message(Message.SUCCESS,"更新用户浏览文章>>>>>成功",upStatus);
            }else {
                return new Message(Message.FAILURE,"更新用户浏览文章>>>>>失败",upStatus);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"更新用户浏览文章>>>>>异常",upStatus);
        }

    }


}
