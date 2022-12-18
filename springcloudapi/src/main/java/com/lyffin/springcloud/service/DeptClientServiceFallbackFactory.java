package com.lyffin.springcloud.service;

import com.lyffin.springcloud.pojo.Dept;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

//服务降级,即当服务接口不能用时，采用降级处理，调用下面当降级方法
//服务熔断针对服务中对异常，服务降级是针对整个服务断开
//服务熔断是被动的保险机制，服务降级是针对资源不足的主动应用（关闭服务）
//服务熔断在服务端：服务提供者
//服务降级在客户端：服务消费者
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory {
    @Override
    public DeptClientService  create(Throwable cause) {
        return new DeptClientService() {
            @Override
            public Dept queryById(Long id) {
                return new Dept().setDeptno(id).setDb_source("没有数据").setDname("服务降级");
            }

            @Override
            public List<Dept> queryAll() {
                return null;
            }

            @Override
            public boolean addDept(Dept dept) {
                return false;
            }
        };
    }
}
