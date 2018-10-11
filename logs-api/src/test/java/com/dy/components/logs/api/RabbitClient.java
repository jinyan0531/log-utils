package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.communication.rabbitmq.DefaultRabbitRegediter;
import com.dy.components.logs.api.protocol.Content;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.api.protocol.ProtocolEnum;

public class RabbitClient extends DefaultRabbitRegediter {
    public static void main(String[] s){
        RabbitClient rabbitClient = new RabbitClient();
        rabbitClient.doRegedit("192.168.20.51",30702,"rabbit","123456");
        rabbitClient.connect();
        AbstractChannel channel = rabbitClient.getChannel();

        Message message = new Message();
        message.setType(ProtocolEnum.CONTENT);
        Content content = new Content();
        content.setMessageJson("cecece");

        message.setContent(content);
        channel.send(message);
    }
}
