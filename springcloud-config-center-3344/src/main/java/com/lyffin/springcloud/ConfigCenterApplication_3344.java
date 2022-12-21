package com.lyffin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigCenterApplication_3344 {
    //可以在localhost:3344/master/config-dev.yml直接访问github的配置文件，格式：/分支名/文件名
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterApplication_3344.class, args);
    }
}
