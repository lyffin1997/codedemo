package com.lyffin.springcloud.controller;

import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class DeptController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Dept> hashMap = new HashMap<>();
    static {
        hashMap.put(1L, new Dept().setDeptno(1L).setDname("测试").setDb_source("mydb"));
        hashMap.put(2L, new Dept().setDeptno(2L).setDname("采购").setDb_source("mydb"));
        hashMap.put(3L, new Dept().setDeptno(3L).setDname("运维").setDb_source("mydb"));
    }

    @GetMapping(value = "/deptSQL/{id}")
    public Result<Dept> deptSQL(@PathVariable("id") Long id) {
        Dept dept = hashMap.get(id);
        Result<Dept> result = new Result<Dept>("200", "serverPort: "+ serverPort, dept);
        return result;
    }

}
