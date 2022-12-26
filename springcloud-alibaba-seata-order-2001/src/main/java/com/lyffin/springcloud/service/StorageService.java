package com.lyffin.springcloud.service;

import com.lyffin.springcloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "springcloud-alibaba-seata-storage")
public interface StorageService {
    @GetMapping(value = "/storage/decrease")
    public Result decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count );
}
