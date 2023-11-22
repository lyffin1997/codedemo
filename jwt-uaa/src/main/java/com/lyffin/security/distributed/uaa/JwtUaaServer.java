package com.lyffin.security.distributed.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtUaaServer {
    public static void main(String[] args) {
        SpringApplication.run(JwtUaaServer.class, args);
    }
}
