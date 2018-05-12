package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.bean.UserMod;
import com.zhoulin.demo.mapper.UserInfoMapper;
import com.zhoulin.demo.service.RegisterService;
import com.zhoulin.demo.service.UserInfoService;
import com.zhoulin.demo.service.UserModService;
import com.zhoulin.demo.utils.RedisTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * RegisterController 注册控制类
 */
@RestController
@RequestMapping(value = "/public")
public class RegisterController {

    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserModService userModService;

    @Autowired
    private RedisTokenManager tokenManager;

    @Autowired
    private UserInfoMapper userInfoMapper;

//    @Value("${server.link}")
//    private String link;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public Message UserRegister(@RequestBody UserInfo userInfo){

        String code = tokenManager.getTokenOfSignUp(userInfo);

        try {

            registerService.registerUserInfo(userInfo, code);
            return new Message(Message.SUCCESS,"注册用户信息--成功>>>>激活邮件发送中",code);

        } catch (Exception e) {

            e.printStackTrace();
            return new Message(Message.ERROR,"注册用户信息--异常",null);

        }

    }

    @RequestMapping(value = "/user/activate/{code}",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Message activate(@PathVariable(value = "code") String code){

        UserInfo userInfo = new UserInfo();

        UserMod userMod = new UserMod();

        int actStatus = 0;

        //建立用户模型的状态信息
        int modMsg = 0;

        try {
            userInfo = registerService.activationUserInfo(code);

            //用状态激活
            userInfo.setUserStatus(1);

            actStatus = userInfoService.addUserInfo(userInfo);

            userMod.setUserId(userInfo.getUserId());

            if (actStatus == 1){
                //建立用户模型
                modMsg = userModService.addUserModForRegister(userMod);
                if (modMsg == 1){
                    return new Message(Message.SUCCESS,"用户激活成功 && 用户模型建立成功",actStatus + "<><>" + modMsg);
                }else {
                    return new Message(Message.SUCCESS,"用户激活成功",actStatus);
                }
            } else if(actStatus == 0){
                return new Message(Message.FAILURE,"用户激活失败",actStatus);
            } else {
                return new Message(Message.ERROR,"用户激活异常",actStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"用户激活异常",null);
        }
    }

    @RequestMapping(value = "/user/findPassword", method = RequestMethod.POST)
    @ResponseBody
    public Message findPassword(@RequestParam(value = "username") String username, @RequestParam(value = "userMail") String userMail){

        UserInfo findUser = new UserInfo();

        try {
            findUser = userInfoService.findUserPW(username, userMail);
            if (findUser != null){
                StringBuilder rdPassword=new StringBuilder();
                Random random=new Random();
                //随机生成数字，并添加到字符串
                for(int i=0;i<8;i++){
                    rdPassword.append(random.nextInt(10));
                }
                logger.info("随机密码生成>>>>>" + rdPassword);
                registerService.findUserPassword(userMail, rdPassword.toString());
                findUser.setPassword(rdPassword.toString());
                int upStatus = userInfoMapper.updateUserInfo(findUser);
                if (upStatus == 1){
                    return new Message(Message.SUCCESS,"用户密码重置--成功>>>>重置邮件发送中",rdPassword);
                }
                return new Message(Message.SUCCESS,"用户密码重置--失败",rdPassword);
            }else {
                return new Message(Message.FAILURE,"用户密码重置--失败","未找到对应用户信息！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"用户密码重置--异常>>>>重置邮件发送中",e.getMessage());
        }
    }

}
