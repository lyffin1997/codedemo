package com.lyffin.springcloud.service.impl;

import com.lyffin.springcloud.dao.StorageDao;
import com.lyffin.springcloud.service.StorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageDao storageDao;


    @Override
    public void decrease(Long productId, Integer count) {
        log.info("===========》扣减库存开始");
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        storageDao.decrease(productId, count);
        log.info("===========》扣减库存结束");
    }
}
