package com.lyffin.springcloud.feign;

import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.pojo.Dept;
import org.springframework.stereotype.Component;

@Component
public class DeptFallbackService implements DeptService{

    @Override
    public Result<Dept> deptSQL(Long id) {
        return new Result<>("500", "feign服务降级返回", new Dept().setDeptno(id));
    }
}
