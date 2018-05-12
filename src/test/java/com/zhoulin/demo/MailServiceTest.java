package com.zhoulin.demo;

import com.zhoulin.demo.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("2817748290@qq.com","test simple mail"," hello this is simple mail");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content=
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n";
        mailService.sendHtmlMail("2817748290@qq.com","this is html mail",content);
    }

    @Test
    public void sendTemplateMail() throws Exception {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("TokenLink", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail("2817748290@qq.com","主题：这是模板邮件",emailContent);
    }
}
