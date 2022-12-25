package com.lyffin.springcloud.feign;

import com.lyffin.springcloud.common.Result;
import com.lyffin.springcloud.pojo.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "springcloud-sentinel-provider-dept", fallback = DeptFallbackService.class)
public interface DeptService {

    @GetMapping(value = "/deptSQL/{id}")
    public Result<Dept> deptSQL(@PathVariable("id") Long id);
}
