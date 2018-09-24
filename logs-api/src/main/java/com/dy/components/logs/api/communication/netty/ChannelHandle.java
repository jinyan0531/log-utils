package com.dy.components.logs.api.communication.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChannelHandle implements ChannelPoolHandler {
    @Override
    public void channelReleased(Channel ch) throws Exception {
        ch.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelAcquired(Channel ch) throws Exception {

    }

    @Override
    public void channelCreated(Channel ch) throws Exception {
        NioSocketChannel channel = (NioSocketChannel) ch;

        // 客户端逻辑处理   ClientHandler这个也是咱们自己编写的，继承ChannelInboundHandlerAdapter，实现你自己的逻辑
        channel.pipeline().addLast(new ClientHandler());
    }
}
