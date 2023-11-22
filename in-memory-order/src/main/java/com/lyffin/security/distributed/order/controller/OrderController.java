package com.lyffin.security.distributed.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/r1")
    @PreAuthorize("hasAuthority('p1')")
    public String r1() {
        return "访问资源1";
    }
}
