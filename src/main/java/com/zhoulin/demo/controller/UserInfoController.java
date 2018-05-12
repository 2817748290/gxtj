package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.LogInfoDTO;
import com.zhoulin.demo.bean.LogInfoDTO;
import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.service.HumanListenerService;
import com.zhoulin.demo.service.LogInfoService;
import com.zhoulin.demo.service.UserInfoService;
import com.zhoulin.demo.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * UserInfoController 控制类
 */
@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HumanListenerService humanListenerService;

    /**
     * 获取所有用户记录条数
     * @return
     */
    @RequestMapping(value = "/getAllUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Message getAllUserInfo(){
        List<UserInfo> userInfoList = new ArrayList<>();
        try {
            userInfoList = userInfoService.getAllUserInfo();
            if (userInfoList != null){
                return new Message(Message.SUCCESS,"获取用户列表--成功",userInfoList);
            } else {
                return new Message(Message.FAILURE,"获取用户列表--失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取用户列表异常！",null);
        }
    }

    /**
     * 根据【用户编号】获取对应用户信息
     * @param userId 用户编号
     * @return
     */
    @RequestMapping(value = "/getUserInfoById/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Message getUserInfoById(@PathVariable(value = "userId") Integer userId){
        UserInfo userInfo = new UserInfo();
        try {
            userInfo = userInfoService.getUserInfoById(userId);
            if (userInfo != null){
                return new Message(Message.SUCCESS,"获取用户信息--成功",userInfo);
            } else {
                return new Message(Message.FAILURE,"获取用户信息--失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取用户信息异常！",null);
        }
    }

    /**
     * 添加/注册功能
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Message addUserInfo(@RequestBody UserInfo userInfo){
        Integer addStatus = 0;
        try {
            addStatus = userInfoService.addUserInfo(userInfo);
            if (addStatus == 1){
                return new Message(Message.SUCCESS,"添加/注册用户信息--成功",addStatus);
            } else if(addStatus == 0){
                return new Message(Message.FAILURE,"添加/注册用户信息--失败",addStatus);
            } else {
                return new Message(Message.ERROR,"添加/注册用户信息--异常",addStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"添加/注册用户信息--异常",null);
        }
    }

    /**
     * 修改/编辑功能
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/updataUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Message updataUserInfo(@RequestBody UserInfo userInfo){
        UserInfo upUserInfo = new UserInfo();
        try {
            upUserInfo = userInfoService.updateUserInfo(userInfo);
            if (upUserInfo != null){
                return new Message(Message.SUCCESS,"修改/编辑用户信息--成功",upUserInfo);
            }

            return new Message(Message.FAILURE,"修改/编辑用户信息--失败",null);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改/编辑用户信息--异常",null);
        }
    }

    /**
     * 根据【用户编号】删除功能
     * @param userId 用户编号
     * @return
     */
    @RequestMapping(value = "/delUserInfo/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Message delUserInfo(@PathVariable(value = "userId") Integer userId){
        Integer delStatus = 0;
        try {
            delStatus = userInfoService.deleteUserInfoById(userId);
            if (delStatus == 1){
                return new Message(Message.SUCCESS,"删除用户信息--成功",delStatus);
            } else if(delStatus == 0){
                return new Message(Message.FAILURE,"删除用户信息--失败",delStatus);
            } else {
                return new Message(Message.ERROR,"删除用户信息--异常",delStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"删除用户信息--异常",null);
        }
    }

    /**
     * 查看用户个人信息
     * @return
     */
    @RequestMapping(value = "/getMyInfo", method = RequestMethod.POST)
    @ResponseBody
    public Message getMyInfo(){
        //token中取出userInfo
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        try {
            UserInfo userInfo1 = userInfoService.getUserInfoById(userInfo.getUserId());
            if (userInfo1 != null){
                return new Message(Message.SUCCESS,"获取用户信息--成功",userInfo);
            } else {
                return new Message(Message.FAILURE,"获取用户信息--失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取用户信息异常！",null);
        }
    }

    /**
     * 获取用户的资讯阅读时间
     * @return
     */
    @RequestMapping(value = "/getUserReadTime", method = RequestMethod.POST)
    @ResponseBody
    public Message getUserReadTime(){
        //token中取出userInfo
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<LogInfoDTO> logInfoDTOList = new ArrayList<>();
        HashMap<Integer, List<LogInfoDTO>> hashMap = new HashMap<>();
        try {
//            logInfoDTOList = humanListenerService.userReadTime(userInfo.getUserId());
            hashMap = humanListenerService.userReadTime(userInfo.getUserId());
            return new Message(Message.SUCCESS,"获取资讯阅读时间>>>成功",hashMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯阅读时间>>>异常！",null);
        }

    }


}
