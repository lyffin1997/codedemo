package com.lyffin.springcloud.controller;

import com.lyffin.springcloud.pojo.Dept;
import com.lyffin.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptConsumerController {

    //feign集成了ribbon默认为轮询，不用再配置负载均衡
    @Autowired
    private DeptClientService deptClientService;

    @GetMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return this.deptClientService.queryById(id);
    }

    @GetMapping("/consumer/dept/list")
    public List<Dept> getAll() {
        return this.deptClientService.queryAll();
    }

    @PostMapping("/consumer/dept/add")
    public boolean add(@RequestBody Dept dept) {
        //注意被调用方接收实体需要@RequestBody注解，否则实体类属性序列化后部分接收不到
        return this.deptClientService.addDept(dept);
    }

}
