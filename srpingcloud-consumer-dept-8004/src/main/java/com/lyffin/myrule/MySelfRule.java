package com.lyffin.myrule;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class MySelfRule {

    //IRule,路由规则接口
    // RoundRobinRule: 轮询（默认）
    // AvailabilityFilteringRule: 先过滤掉宕机或者故障的服务，对剩下的轮询
    // RandomRule: 随机
    // WeightedResponseTimeRule: 按权重比
    // RetryRule: 先按轮询获取服务，如果获取失败会在指定的时间内进行重试
//    @Bean
//    public IRule myRule() {
//        return new RandomRule();
//    }

    //上面的已经过期了，新版本只有轮询和随机两种
    //若要写自定义算法，同级目录新建个类，并写一个实现ReactorLoadBalancer接口的bean,然后再ConfigBean中声明
    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
                                                            LoadBalancerClientFactory loadBalancerClientFactory){
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(loadBalancerClientFactory
                .getLazyProvider(name,ServiceInstanceListSupplier.class),name);
    }

}
