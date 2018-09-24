package com.dy.components.logs.api.communication.netty;

import com.dy.components.logs.api.communication.DefaultClient;
import com.dy.components.logs.api.communication.IClient;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import io.netty.channel.nio.NioEventLoopGroup;

public class DefaultNettyClient extends DefaultClient implements IClient {

    @Override
    public IClient create(String host, int port) {
        return null;
    }

    @Override
    public void send(DefaultCollectLog defaultCollectLog) {

    }
}
