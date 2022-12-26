package com.lyffin.springcloud.controller;

import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.pojo.Order;
import com.lyffin.springcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StorageController {
    @Autowired
    private StorageService storageService;

        @GetMapping(value = "/storage/decrease")
    public Result decrease(@RequestParam("productId")Long productId, @RequestParam("count")Integer count) {
        storageService.decrease(productId, count);
        return new Result("200", "库存更新成功成功", null);
    }
}
