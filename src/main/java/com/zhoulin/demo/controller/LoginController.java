package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.bean.UserMod;
import com.zhoulin.demo.service.LoginService;
import com.zhoulin.demo.service.UserInfoService;
import com.zhoulin.demo.service.UserModService;
import com.zhoulin.demo.utils.IpUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserModService userModService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Message login(@RequestBody UserInfo user, HttpSession session, HttpServletResponse response){

        Message message = loginService.login(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
        }
        return message;
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
        UserMod userMod = new UserMod();
        //建立用户模型的状态信息
        int modMsg = 0;
        try {
            addStatus = userInfoService.addUserInfo(userInfo);
            List<UserInfo> userInfoList = userInfoService.getAllUserInfo();
            for(UserInfo u : userInfoList){
                if(u.getUsername().equals(userInfo.getUsername())){
                    userMod.setUserId(u.getUserId());
                }
            }
            modMsg = userModService.addUserModForRegister(userMod);

            if (addStatus == 1){
                if (modMsg == 1){
                    return new Message(Message.SUCCESS,"添加/注册用户信息--成功&& 用户模型建立成功",addStatus + "<><>" + modMsg);
                }else {
                    return new Message(Message.SUCCESS,"添加/注册用户信息--成功",addStatus);
                }
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
     * 获取用户IP地址直接注册/登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/getIp", method = RequestMethod.POST)
    @ResponseBody
    public Message getIp(HttpServletRequest request) {

        //获取用户IP
        String userIp = IpUtil.getIpAddr(request);

        //通过用户IP直接注册
        UserInfo userInfo = new UserInfo(userIp, userIp, userIp, 1, 1, new Date(), new Date());

        try {
            //判断是否之前注册过
            UserInfo isExistUser = userInfoService.getUserByUsername(userIp);
            if (isExistUser != null){
                return new Message(Message.SUCCESS, "IP已存在 >>>> IP :" + userIp , isExistUser);
            }
            else {
                int addStatus = userInfoService.addUserInfo(userInfo);

                if (addStatus == 1 ){
                    int userId = userInfoService.getUserByUsername(userInfo.getUsername()).getUserId();
                    UserMod userMod = new UserMod();
                    userMod.setUserId(userId);
                    int addModStatus = userModService.addUserModForRegister(userMod);
                    if (addModStatus == 1){
                        return new Message(Message.SUCCESS, "IP注册 && 用户模型 >>>> 成功 >>>> IP :" + userIp , userInfo);
                    }
                }
                return new Message(Message.FAILURE, "IP注册 >>>> 失败 >>>> IP :" + userIp , addStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "IP注册 >>>> 异常 >>>> IP :" + userIp , e.getMessage());
        }


    }




}
