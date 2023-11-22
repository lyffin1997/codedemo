package com.lyffin.security.distributed.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UaaServer {
    public static void main(String[] args) {
        SpringApplication.run(UaaServer.class, args);
    }
}
