package com.lyffin.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lyffin.springcloud.service.ChainLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {

    @Autowired
    private ChainLimitService chainLimitService;

    @GetMapping(value = "/testA")
    public String testA() {
        return chainLimitService.testA();
        //return "----------------------testA";
    }

    @GetMapping(value = "/testB")
    public String testB() {
        int i = 10/0;
        return chainLimitService.testA();
        //return "----------------------testB";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "block_hotKey")
    public String testHotKey(
            @RequestParam(value = "hot1",required = false) String hot1,
            @RequestParam(value = "hot2",required = false) String hot2,
            @RequestParam(value = "hot3",required = false) String hot3
    ){
        return "-----testHotKey";
    }

    public String block_hotKey(String hot1, String hot2, String hot3, BlockException blockException){
        return "系统繁忙请稍后再试";
    }

}
