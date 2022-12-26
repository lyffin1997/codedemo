package com.lyffin.springcloud.controller;

import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.pojo.Order;
import com.lyffin.springcloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/create")
    public Result create(Order order) {
        orderService.create(order);
        return new Result("200", "订单创建成功", null);
    }
}
