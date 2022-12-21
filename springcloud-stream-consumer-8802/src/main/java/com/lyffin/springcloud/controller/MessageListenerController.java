package com.lyffin.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    @Bean
        //这里接收rabbitmq的条件是参数为Consumer 并且 方法名和supplier方法名相同
        //这里的返回值是一个匿名函数 返回类型是consumer 类型和提供者的类型一致
        //supplier发送的exchange是 send-in-0 这里只需要用send方法名即可
    public Consumer<String> myChannel() {
        return message -> System.out.println("我是消费者"+serverPort+"，我收到了消息："+message);
    }
}
