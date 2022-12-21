package com.lyffin.springcloud.service.impl;

import com.lyffin.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class MessageProviderImpl implements IMessageProvider {

    private final StreamBridge streamBridge;

    public MessageProviderImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        streamBridge.send("myChannel-out-0", MessageBuilder.withPayload(serial).build());
        System.out.println("发送消息: " + serial);
        return null;
    }
}
