package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.UserInfo;

public interface MailService {

    /**
     * text简单文本邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @throws Exception
     */
    public void sendSimpleMail(String to, String subject, String content) throws Exception;

    /**
     * html格式邮件
     *
     * @param to
     * @param subject
     * @param content
     * @throws Exception
     */
    public void sendHtmlMail(String to, String subject, String content) throws Exception;

//    /**
//     *
//     * @param userInfo
//     * @param token
//     */
//    public void userValidate(UserInfo userInfo, String token);

}
