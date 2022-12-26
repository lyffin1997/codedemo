package com.lyffin.springcloud.controller;

import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/decrease")
    public Result decrease(@RequestParam("userId") Long userId, @RequestParam("money")BigDecimal money) {

        accountService.decrease(userId, money);
        return new Result("200", "账户扣款成功", null);
    }
}
