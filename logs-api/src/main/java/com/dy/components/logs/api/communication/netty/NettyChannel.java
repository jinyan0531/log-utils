package com.dy.components.logs.api.communication.netty;

import com.alibaba.fastjson.JSON;
import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.protocol.Content;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.api.protocol.ProtocolEnum;
import io.netty.channel.Channel;

import java.io.IOException;


public class NettyChannel extends AbstractChannel {
    Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(Message message) {
        if(channel!=null){

            channel.writeAndFlush(message);
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
