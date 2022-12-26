package com.lyffin.springcloud.service.impl;

import com.lyffin.springcloud.dao.OrderDao;
import com.lyffin.springcloud.pojo.Order;
import com.lyffin.springcloud.service.AccountService;
import com.lyffin.springcloud.service.OrderService;
import com.lyffin.springcloud.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AccountService accountService;

    @Override
    @GlobalTransactional
    public void create(Order order) {
        log.info("=============>创建订单");
        orderDao.create(order);
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        log.info("===========>订单微服务开始调用库存");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("===========>订单微服务开始调用账户");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("===========>更改订单状态");
        orderDao.update(order.getUserId(), 0);
        log.info("===========>下单结束");
    }
}
