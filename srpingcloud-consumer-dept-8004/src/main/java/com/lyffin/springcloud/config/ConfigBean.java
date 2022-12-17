package com.lyffin.springcloud.config;


import com.lyffin.myrule.MySelfRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@LoadBalancerClient(name = "SPRINGCLOUD-PROVIDER-DEPT", configuration = MySelfRule.class)
//若要对多个微服务负载均衡，使用
//@LoadBalancerClients(value = {
//        @LoadBalancerClient(name = "SPRINGCLOUD-PROVIDER-DEPT", configuration = MySelfRule.class),
//        @LoadBalancerClient(name = "SPRINGCLOUD-PROVIDER-DEPT2", configuration = MySelfRule2.class)
//})
public class ConfigBean {

    @Bean
    @LoadBalanced//不加的话使用集群时会报错
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
