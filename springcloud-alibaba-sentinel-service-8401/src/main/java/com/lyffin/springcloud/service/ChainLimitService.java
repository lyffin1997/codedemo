package com.lyffin.springcloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ChainLimitService {
    //  指定资源名，并指定降级方法
    @SentinelResource(value = "testA", blockHandler = "testAFallback")
    public String testA() {
        return "testA";
    }

    public String testAFallback(BlockException ex) {
        return "ex testA";
    }
}
