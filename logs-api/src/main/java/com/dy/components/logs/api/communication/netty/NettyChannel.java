package com.dy.components.logs.api.communication.netty;

import com.alibaba.fastjson.JSON;
import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.protocol.Content;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.api.protocol.ProtocolEnum;
import io.netty.channel.Channel;


public class NettyChannel extends AbstractChannel {
    Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(DefaultCollectLog defaultCollectLog) {
        if(channel!=null){
            Message message = new Message();
            Content content = new Content();
            //content.setClassName(clazz.getName());
            content.setMessageJson(JSON.toJSONString(defaultCollectLog));
            message.setContent(content);
            message.setType(ProtocolEnum.CONTENT);
            channel.writeAndFlush(message);
        }
    }
}
