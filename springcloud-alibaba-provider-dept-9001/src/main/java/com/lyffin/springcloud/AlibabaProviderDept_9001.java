package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AlibabaProviderDept_9001 {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaProviderDept_9001.class, args);
    }
}
