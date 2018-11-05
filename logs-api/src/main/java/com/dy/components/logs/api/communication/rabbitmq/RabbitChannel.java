package com.dy.components.logs.api.communication.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.utils.ProtostuffUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitChannel  extends AbstractChannel {

    Channel channel;


    public RabbitChannel(Channel channel){
        this.channel = channel;
    }

    @Override
    public void send(Message message) {
        try {
            channel.queueDeclare(DEFAULT_RABBIT_QUEUE, true, false, false, null);
            byte[] bytes = ProtostuffUtil.serializer(message);
            channel.basicPublish("", DEFAULT_RABBIT_QUEUE, MessageProperties.TEXT_PLAIN, bytes);
            if(channel.isOpen())
             channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
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
