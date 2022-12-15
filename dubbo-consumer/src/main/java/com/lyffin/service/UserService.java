package com.lyffin.service;


import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //从注册中心拿到服务
    //方法一：在当前服务建一个相同名称的Service
    @DubboReference
    private TicketService ticketService;

    public void buyTicket() {
        String ticket = ticketService.getTicket();
        System.out.println(ticket);
    }
}
