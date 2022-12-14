package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class DeptConsumer_8004 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer_8004.class, args);
    }
}
