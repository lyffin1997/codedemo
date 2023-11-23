package com.lyffin.security.distributed.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InDbUaaServer {
    public static void main(String[] args) {
        SpringApplication.run(InDbUaaServer.class, args);
    }
}
