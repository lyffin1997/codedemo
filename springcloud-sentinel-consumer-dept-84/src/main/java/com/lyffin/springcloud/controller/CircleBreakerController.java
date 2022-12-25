package com.lyffin.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.feign.DeptService;
import com.lyffin.springcloud.handler.HandlerFallback;
import com.lyffin.springcloud.pojo.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//sentinel熔断控制器
@RestController
@Slf4j
public class CircleBreakerController {

    @Value("${service-url.nacos-user-service}")
    private String serviceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DeptService deptService;

    @GetMapping(value = "/consumer/fallback/{id}")
    @SentinelResource(value = "fallback", fallbackClass = HandlerFallback.class, fallback = "handlerFallback",
    blockHandlerClass = HandlerFallback.class, blockHandler = "blockHandler",
    exceptionsToIgnore = {IllegalArgumentException.class})//注意这里只能忽略fallback的异常，忽略降级的BlockException不生效
    public Result<Dept> fallback(@PathVariable("id") Long id) {
        Result<Dept> result = restTemplate.getForObject(serviceUrl + "/deptSQL/" + id, Result.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("非法参数");
        }else if (result.getData() == null) {
            throw new NullPointerException("id没有对应数据，空指针异常");
        }
        return result;
    }

    @GetMapping(value = "/consumer/deptSQL/{id}")
    public Result<Dept> deptSQL(@PathVariable("id") Long id) {
        return deptService.deptSQL(id);
    }
}
