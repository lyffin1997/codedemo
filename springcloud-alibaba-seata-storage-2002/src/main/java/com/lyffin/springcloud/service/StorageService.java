package com.lyffin.springcloud.service;

import com.lyffin.springcloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface StorageService {
    /**
     * 扣除存储数量
     */
    void decrease(Long productId, Integer count);
}
