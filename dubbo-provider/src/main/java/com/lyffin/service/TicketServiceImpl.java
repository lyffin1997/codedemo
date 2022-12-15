package com.lyffin.service;

import org.apache.dubbo.config.annotation.DubboService;


@DubboService
public class TicketServiceImpl implements TicketService{
    @Override
    public String getTicket() {
        return "lyffin test ";
    }
}
