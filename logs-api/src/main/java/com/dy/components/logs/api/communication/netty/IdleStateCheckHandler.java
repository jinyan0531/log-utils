package com.dy.components.logs.api.communication.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 连接检测
 * @author fufeijian
 */
public class IdleStateCheckHandler extends ChannelDuplexHandler {
    private boolean firstWriterIdleEvent = true;
    private boolean firstAllIdleEvent = true;
    private final ChannelFutureListener writeListener = new ChannelFutureListener() {
    private long lastWriteTime;
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            firstWriterIdleEvent = firstAllIdleEvent = true;
            lastWriteTime = System.currentTimeMillis();
        }
    };


}
