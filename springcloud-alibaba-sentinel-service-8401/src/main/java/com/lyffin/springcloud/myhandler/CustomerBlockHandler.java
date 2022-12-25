package com.lyffin.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lyffin.springcloud.common.Result;

public class CustomerBlockHandler {
    public static Result handleException(BlockException e) {
        return new Result("404", e.getClass().getCanonicalName()+" 服务不可用");
    }
}
