package com.lyffin.springcloud.service.impl;

import com.lyffin.springcloud.dao.AccountDao;
import com.lyffin.springcloud.pojo.Account;
import com.lyffin.springcloud.service.AccountService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("=============>账户开始扣钱");
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        int i = 10/0;
        accountDao.decrease(userId, money);
        log.info("=============>账户扣钱结束");
    }
}
