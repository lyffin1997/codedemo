package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SentinelConsumerDept_84 {
    public static void main(String[] args) {
        SpringApplication.run(SentinelConsumerDept_84.class, args);
    }
}
