package com.lyffin.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class DeptNacosController {

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "consumer/dept/nacos/{id}")
    public String getDept(@PathVariable("id") Long id) {
        return restTemplate.getForObject(serverUrl+"/dept/nacos/"+id, String.class);
    }

}
