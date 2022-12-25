package com.lyffin.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.pojo.Dept;
import org.springframework.web.bind.annotation.PathVariable;

public class HandlerFallback {

    //必须要static
    public static Result<Dept> handlerFallback(@PathVariable("id") Long id, Throwable e) {
        Dept dept = new Dept().setDeptno(id);
        Result<Dept> result = new Result<>("500", "自定义异常返回内容:"+e.getMessage(), dept);
        return result;
    }

    public static Result<Dept> blockHandler(@PathVariable("id") Long id, BlockException e) {
        Dept dept = new Dept().setDeptno(id);
        Result<Dept> result = new Result<>("500", "sentinel限流:"+e.getClass().getCanonicalName(), dept);
        return result;
    }
}
