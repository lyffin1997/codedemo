package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class FeignDeptConsumer_8007 {
    public static void main(String[] args) {
        SpringApplication.run(FeignDeptConsumer_8007.class, args);
    }
}
