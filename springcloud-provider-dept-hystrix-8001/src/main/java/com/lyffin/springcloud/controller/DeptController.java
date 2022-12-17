package com.lyffin.springcloud.controller;

import com.lyffin.springcloud.pojo.Dept;
import com.lyffin.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @HystrixCommand(fallbackMethod = "hystrixGetDept")
    @GetMapping("/dept/get/{id}")
    public Dept getDept(@PathVariable("id") Long id) {
        Dept dept = deptService.queryById(id);
        if (dept == null) {
            throw new RuntimeException("id=>"+id+",不存在该部门");
        }
        return dept;
    }

    //备选方案
    public Dept hystrixGetDept(Long id) {
        return new Dept("id=>"+id+",不存在该部门")
                .setDeptno(id)
                .setDb_source("no this database in mysql");
    }



}
