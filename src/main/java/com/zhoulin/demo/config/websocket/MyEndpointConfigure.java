package com.zhoulin.demo.config.websocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 16:34 2018/3/21
 */
public class MyEndpointConfigure extends ServerEndpointConfig.Configurator implements ApplicationContextAware
{
    private static volatile BeanFactory context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException
    {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        System.out.println("auto load"+this.hashCode());
        MyEndpointConfigure.context = applicationContext;
    }
}