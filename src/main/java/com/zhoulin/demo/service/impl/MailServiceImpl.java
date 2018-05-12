package com.zhoulin.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //spring 提供的邮件发送类
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    private static final String TITLE_SIGN_UP = "[邮件标题]";

    private static final String CONTENT = "[邮件内容]";

    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        //创建简单邮件消息
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置发送人
        simpleMailMessage.setFrom(from);
        //设置收件人
        simpleMailMessage.setTo(to);

        /* String[] adds = {"xxx@qq.com","yyy@qq.com"}; //同时发送给多人
        message.setTo(adds);*/

        //设置主题
        simpleMailMessage.setSubject(subject);
        //设置内容
        simpleMailMessage.setText(content);
        try {
            mailSender.send(simpleMailMessage);
            logger.info("简单邮件已经发送");
        }catch (Exception e){
            logger.error("发送简单邮件时发生异常！", e);
        }

    }

    @Override
    public void sendHtmlMail(String to, String subject, String content){

        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }

    }

//    @Override
//    public void userValidate(UserInfo userInfo, String token) {
//        MimeMessage mailMessage = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
//            helper.setFrom(from);
//            helper.setTo(userInfo.getUserMail());
//            helper.setSubject("用户邮箱激活");
//            String link = "http://localhost:8000/validate/" + token;
//            String message = String.format(CONTENT, userInfo.getNickname(), link, userInfo.getUserMail());
//            helper.setText(message, true);
//            mailSender.send(mailMessage);
//        } catch (MessagingException e) {
//            logger.error("发送邮件失败：User:" + JSONObject.toJSONString(userInfo) + ", Token: " + token);
//        }
//
//    }


}
