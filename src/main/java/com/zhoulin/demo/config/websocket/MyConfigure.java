package com.zhoulin.demo.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 16:40 2018/3/21
 */
@Configuration
public class MyConfigure
{

    @Bean
    public MyEndpointConfigure newConfigure()
    {
        return new MyEndpointConfigure();
    }
}