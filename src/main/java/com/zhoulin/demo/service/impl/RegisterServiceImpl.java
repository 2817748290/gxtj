package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.service.RegisterService;
import com.zhoulin.demo.service.MailService;
import com.zhoulin.demo.utils.RedisTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void registerUserInfo(UserInfo userInfo, String token) throws Exception {

        String subject = "用户邮箱激活";

        Context context = new Context();

        context.setVariable("code", token);

        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail(userInfo.getUserMail(), subject, emailContent);

    }

    @Override
    public UserInfo activationUserInfo(String code) throws Exception {

        ValueOperations<String, UserInfo> operations = redisTemplate.opsForValue();

        UserInfo userInfo =  operations.get(code);

        //一个激活码只有能使用，使用后会从redis清除缓存
        redisTemplate.delete(code);

        return userInfo;
    }

    @Override
    public void findUserPassword(String emailUrl, String token) throws Exception {

        String subject = "用户密码找回";

        Context context = new Context();

        context.setVariable("newPw", token);

        String emailContent = templateEngine.process("findPassword", context);

        mailService.sendHtmlMail(emailUrl, subject, emailContent);

    }

}
