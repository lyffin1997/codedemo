package com.lyffin.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {

    @Bean
    @LoadBalanced//不加的话使用集群时会报错
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
