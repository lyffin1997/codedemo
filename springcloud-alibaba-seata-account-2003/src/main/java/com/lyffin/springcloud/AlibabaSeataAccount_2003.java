package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AlibabaSeataAccount_2003 {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaSeataAccount_2003.class, args);
    }
}
