package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SentinelProviderDept_9004 {
    public static void main(String[] args) {
        SpringApplication.run(SentinelProviderDept_9004.class, args);
    }
}
