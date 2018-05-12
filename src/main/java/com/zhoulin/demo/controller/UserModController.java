package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.UserModService;
import com.zhoulin.demo.utils.CheckType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userMod")
public class UserModController {

    @Autowired
    private UserModService userModService;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private CheckType checkType;

    @RequestMapping(value = "/getUserMod", method = RequestMethod.GET)
    @ResponseBody
    public Message getUserModById(){
        //token中取出userInfo
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        UserMod userMod = new UserMod();

        try {
            userMod = userModService.getUserModByUserId(userInfo.getUserId());
            if (userMod != null){
                return new Message(Message.SUCCESS, "获取对应用户模型>>>成功", userMod);
            }
            return new Message(Message.FAILURE, "获取对应用户模型>>>失败", "请核对数据源");
        } catch (Exception e) {
            return new Message(Message.ERROR, "获取对应用户模型>>>异常", e);
        }
    }

    @RequestMapping(value = "/updateUserMod", method = RequestMethod.POST)
    @ResponseBody
    public Message updateUserMod(@RequestBody UserMod userMod){
        //token中取出userInfo
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Integer upStatus = 0;
        userMod.setUserId(userInfo.getUserId());
        try {
            upStatus = userModService.updateUserMod(userMod);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "更新对应用户模型>>>成功", upStatus);
            }else if(upStatus == 0){
                return new Message(Message.FAILURE, "修改对应用户模型>>>失败", "请核对数据源");
            }
            return new Message(Message.ERROR, "获取对应用户模型>>>异常", upStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取对应用户模型>>>异常", e);
        }
    }

    @RequestMapping(value = "/addUserMod", method = RequestMethod.POST)
    @ResponseBody
    public Message addUserMod(@RequestBody UserMod userMod){
        //token中取出userInfo
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Integer addStatus = 0;
        userMod.setUserId(userInfo.getUserId());
        try {
            addStatus = userModService.addUserModForRegister(userMod);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "新建对应用户模型>>>成功", addStatus);
            }else if(addStatus == 0){
                return new Message(Message.FAILURE, "新建对应用户模型>>>失败", "请核对数据源");
            }
            return new Message(Message.ERROR, "新建对应用户模型>>>异常", addStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "新建对应用户模型>>>异常", e);
        }
    }

    /**
     * 前端达到一定时间后请求该接口
     * 仔细阅读
     * @param userId
     * @param infoId
     * @return
     */
    @RequestMapping(value = "/updateUserModForRead", method = RequestMethod.POST)
    @ResponseBody
    public Message updateUserModForRead(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "infoId") Integer infoId){
        UserMod userMod = new UserMod();
        TypeRelation typeRelation = new TypeRelation();
        try {
            userMod = userModService.getUserModByUserId(userId);
            typeRelation = typeRelationMapper.getInfoByTRId(infoId);
            userMod = checkType.checkInfoType(userMod, typeRelation.getTypeId());
            //用户模型修改
            int upStatus = userModService.updateUserMod(userMod);
            if (upStatus == 1){
                return new Message(Message.SUCCESS, "更新用户模型>>>成功", upStatus);
            }else {
                return new Message(Message.FAILURE, "更新用户模型>>>失败", upStatus);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "更新用户模型>>>异常", e.getMessage());
        }
    }

}
