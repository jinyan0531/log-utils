package com.dy.components.logs.api.communication.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.utils.ProtostuffUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

public class RabbitChannel  extends AbstractChannel {

    Channel channel;


    public RabbitChannel(Channel channel){
        this.channel = channel;
    }

    @Override
    public void send(Message message) {
        try {
            channel.basicPublish("", DEFAULT_RABBIT_QUEUE, MessageProperties.TEXT_PLAIN, ProtostuffUtil.serializer(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
