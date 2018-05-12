package com.zhoulin.demo.config.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ElasticSearch 配置类
 */
@Configuration
public class ESConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {

        Settings settings = Settings.builder()
                .put("cluster.name","info")
                .build();

        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName("127.0.0.1"), 9300
        );

        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(node);

        return client;
    }

    /**
     * Bean Util
     * @return
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
