package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AlibabaConsumerDept_9003 {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaConsumerDept_9003.class, args);
    }
}
