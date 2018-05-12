package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.bean.UserRead;
import com.zhoulin.demo.service.UserReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/userRead")
public class UserReadController {

    @Autowired
    private UserReadService userReadService;

    /**
     * 更新 OR 添加 用户仔细阅读记录
     * @param infoId
     * @return
     */
    @RequestMapping(value = "/makeUserRead", method = RequestMethod.POST)
    @ResponseBody
    public Message makeUserRead(@RequestParam(value = "infoId") long infoId){

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        UserRead userRead = new UserRead();

        Integer addStatus = 0;
        UserRead findUserRead = new UserRead();
        try {
            findUserRead = userReadService.findUserReadExist(userInfo.getUserId(), infoId);
            if (findUserRead != null){
                findUserRead.setLookTime(new Date());
                int upStatus = userReadService.updateUserRead(findUserRead);
                if (upStatus==1){
                   return new Message(Message.SUCCESS, "更新用户仔细阅读记录>>>>成功", upStatus);
                }
                return new Message(Message.FAILURE, "更新用户仔细阅读记录>>>>失败", upStatus);
            }else {
                userRead.setInfoId(infoId);
                userRead.setUserId(userInfo.getUserId());
                userRead.setLookTime(new Date());
                addStatus = userReadService.addUserRead(userRead);
                if (addStatus==1){
                    return new Message(Message.SUCCESS, "插入用户仔细阅读记录>>>>成功", addStatus);
                }
                return new Message(Message.FAILURE, "更新用户仔细阅读记录>>>>失败", addStatus);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "更新用户仔细阅读记录>>>>失败", e.getMessage());
        }
    }

}
