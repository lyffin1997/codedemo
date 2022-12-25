package com.lyffin.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping(value = "/byResource")
    //SentinelResource不支持private方法
    @SentinelResource(value = "byResource", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handleException")
    public Result<String> byResource() {
        return Result.success("按资源名称限流测试成功");
    }

}
