package com.dy.components.logs.api.communication.netty;

import com.dy.components.logs.api.communication.IRegeditServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class DefaultNettyServer implements IRegeditServer {

    int port;

    private final MessageHandler handler = new MessageHandler();


    final IdleStateCheckTrigger idleStateCheckTrigger = new IdleStateCheckTrigger();

    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public DefaultNettyServer(int port) {
        this.port = port;
    }

    public void startRegistryServer() {

    }


    public ChannelFuture bind() {

        ThreadFactory bossFactory = bossThreadFactory("jupiter.acceptor.boss");
        ThreadFactory workerFactory = workerThreadFactory("jupiter.acceptor.worker");


        boss = new NioEventLoopGroup(2, bossFactory);
        worker = new NioEventLoopGroup(4, workerFactory);

        bootstrap = new ServerBootstrap().group(boss, worker);

        bootstrap.childHandler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(
                        this,
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateCheckTrigger,
                        new ProtostuffDecoder(),
                        new ProtostuffEncoder(),
                        handler);
            }
        });


        return bootstrap.bind(port);
    }

    protected ThreadFactory workerThreadFactory(String name) {
        return new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }

    protected ThreadFactory bossThreadFactory(String name) {
        return new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }

    class MessageHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        }

        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            Channel ch = ctx.channel();
            ChannelConfig config = ch.config();


        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            Channel ch = ctx.channel();

        }
    }
}