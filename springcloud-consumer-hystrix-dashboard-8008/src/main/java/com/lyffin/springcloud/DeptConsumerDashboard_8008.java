package com.lyffin.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

import javax.servlet.Servlet;

@SpringBootApplication
//开启监控
@EnableHystrixDashboard
//页面地址:localhost:8008/hystrix
public class DeptConsumerDashboard_8008 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumerDashboard_8008.class, args);
    }

}
