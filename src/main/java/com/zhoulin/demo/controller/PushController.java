package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;
import com.zhoulin.demo.mapper.TypeMapper;
import com.zhoulin.demo.service.*;
import com.zhoulin.demo.service.impl.LogInfoServiceImpl;
import com.zhoulin.demo.service.search.SearchService;
import com.zhoulin.demo.utils.CheckType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
//@RequestMapping("/api/push")
public class PushController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushController.class);
    @Autowired
    private PushService pushService;

    @Autowired
    private ModService modService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private LogInfoService logInfoService;

    @Autowired
    private HumanListenerService humanListenerService;

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 推送功能
     * @param id 资讯id
     * @return
     */
    @RequestMapping(value = "/api/push/pushInfoByKeyword", method = RequestMethod.POST)
    @ResponseBody
    public Message pushInfo(@RequestParam(value = "id") long id){

        List<Info> informationList = new ArrayList<>();

        long pushId = 0;

        try {

            //获得用户感兴趣信息（优质）
            informationList = pushService.pushInformation(id);

            if (informationList.size() > 0) {
                return new Message(Message.SUCCESS, "推送用户感兴趣资讯>>>>>成功", informationList);
            }

            return new Message(Message.FAILURE, "推送用户感兴趣资讯>>>>>失败", "未找到感兴趣信息");

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "推送用户感兴趣资讯>>>>>异常", "未知错误！");
        }

    }

    /**
     * 根据浏览日志抓取
     * 登录即可推送
     * 加入递进式推送终止机制
     * @return
     */
    @RequestMapping(value = "/api/push/pushUserByLogInfo", method = RequestMethod.POST)
    @ResponseBody
    public Message pushUserByLogInfo(HttpServletRequest request){

        int page = Integer.valueOf(request.getHeader("page"));

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<Info> informationList = new ArrayList<>();
        HashMap hashMap = new HashMap<Integer, List<LogInfoDTO>>();
        //hash<类型Id, 是否低于时间阈值>
        HashMap isThresholdMap = new HashMap<Integer, Boolean>();

        boolean isWantThreshold = false;
        try {

            hashMap = humanListenerService.userReadTime(userInfo.getUserId());

            Iterator<Map.Entry<Integer, List<LogInfoDTO>>> iterator = hashMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Integer, List<LogInfoDTO>> entry = iterator.next();
                long timeSum = 0;
                List<LogInfoDTO> list = entry.getValue();
                //达到5条以上才进行阈值判断
                if (list.size() >= 5){
                    for (int i=0;i<list.size();i++) {
                        //获取最新5次记录作为终止依据
                        if (i < 6){
                            timeSum = timeSum + list.get(i).getTimeDifference();
                            continue;
                        }
                    }
                    //获得平均时间
                    long averageTime = timeSum/5;
                    LOGGER.info("averageTime >>> " + averageTime);
                    boolean isThreshold = false;
                    //计算平均时间&&是否达到阈值
                    if (averageTime <= 300000){
                        isThreshold = true;
                        isWantThreshold = true;
                    }
                    isThresholdMap.put(entry.getKey(), isThreshold);
                    LOGGER.info("isThreshold >>> " + isThreshold);
                }
                LOGGER.info("isWantThreshold >>> " + isWantThreshold);
            }

            //加入递进式推送终止机制
            if(logInfoService.getLogInfoByUserId(userInfo.getUserId()).size()<1 || isWantThreshold == true){
                //最新的20条
                informationList = infoService.findInfoByDate(page);
                return new Message(Message.SUCCESS, "实时热点>>>>>推送>>>>>成功", informationList);
            }
            informationList = pushService.logAnalyzForPush(userInfo.getUserId());

            if (informationList.size()>20){
                List<Info> finalList  = new ArrayList<>();
                int maxNum = page*20;
                int minNum = (page-1) * 20;
                if ((informationList.size() - 20*page) < 20){
                    finalList = informationList.subList(minNum, informationList.size()-1);
                    return new Message(Message.SUCCESS, "日志兴趣点抓取成功>>>>>推送>>>>>成功>>>>>"+finalList.size(), finalList);
                }
                finalList = informationList.subList(minNum, maxNum);
                return new Message(Message.SUCCESS, "日志兴趣点抓取成功>>>>>推送>>>>>成功"+finalList.size(), finalList);

            }else {
                return new Message(Message.SUCCESS, "日志兴趣点抓取成功>>>>>推送>>>>>成功"+informationList.size(), informationList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "日志兴趣点抓取成功>>>>>推送>>>>>失败", e);
        }
    }

    /**
     * 根据新闻类型推送
     * 无需登录
     */
    @RequestMapping(value = "/public/push/pushInfoByTypeId/{typeId}", method = RequestMethod.GET)
    @ResponseBody
    public Message pushInfoByTypeId(@PathVariable(value = "typeId") Integer typeId){

        List<Info> infoList = new ArrayList<>();

        List<Long> infoIdList = new ArrayList<>();

        try {
            String typeName = typeMapper.getTypeByTypeId(typeId).getTypeName();

//            infoList = pushService.pushInfoByTypeId(typeId);
            InfoSearch infoSearch = new InfoSearch();
            infoSearch.setTypeName(typeName);
            ServiceMultiResult<Long> multiResult2 = modService.queryTypeName(infoSearch);
            infoIdList = multiResult2.getResult();

            for (Long infoId:infoIdList) {
                Info info = infoService.getInfoByInfoIdForImage(infoId);
                infoList.add(info);
            }

            return new Message(Message.SUCCESS, typeName + " 类型资讯成功>>>>>推送>>>>>成功>>>>> " + infoList.size(), infoList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "类型资讯成功>>>>>推送>>>>>失败", e);
        }

    }

    /**
     * 用户隐性分组
     * 资讯推送
     */
    @RequestMapping(value = "/api/push/pushInfoByUserGroup", method = RequestMethod.POST)
    @ResponseBody
    public Message pushInfoByUserGroup(){

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        try {
            List<Info> infos = pushService.pushInfoByUserGroup(userInfo.getUserId());
            if (infos.size()!=0){
                return new Message(Message.SUCCESS," 用户隐性分组>>>>>推送>>>>>成功>>>>> " + infos.size(), infos);
            }else{
                List<Info> infoList = infoService.findInfoByDate(1);
                return new Message(Message.SUCCESS," 用户隐性分组>>>>>热点推送>>>>>成功>>>>> " + infoList.size(), infoList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"用户隐性分组>>>>>推送>>>>>异常", e);
        }

    }


}
