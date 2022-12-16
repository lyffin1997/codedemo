package com.lyffin.springcloud.controller;

import com.lyffin.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    //这里是不需要eureka直接调用，请求的是provider的地址
    //private static final String REST_URL_PREFIX = "http://localhost:8003";
    //这里是使用eureka后调用的注册的微服务名
    private static final String REST_URL_PREFIX = "http://SPRINGCLOUD-PROVIDER-DEPT";

    @GetMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id, Dept.class);
    }

    @GetMapping("/consumer/dept/list")
    public List<Dept> getAll() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/list", List.class);
    }

    @PostMapping("/consumer/dept/add")
    public boolean add(Dept dept) {
        //注意被调用方接收实体需要@RequestBody注解，否则实体类属性序列化后部分接收不到
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Boolean .class);
    }

}
