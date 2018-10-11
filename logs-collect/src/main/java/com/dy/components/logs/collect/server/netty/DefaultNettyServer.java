package com.dy.components.logs.collect.server.netty;

import com.dy.components.logs.api.communication.RegisterMeta;
import com.dy.components.logs.api.communication.netty.IdleStateCheckTrigger;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.collect.server.IRegeditServer;
import com.dy.components.logs.utils.ConcurrentSet;
import com.dy.components.logs.utils.ProtostuffDecoder;
import com.dy.components.logs.utils.ProtostuffEncoder;
import com.dy.components.logs.utils.SocketChannelProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public abstract class DefaultNettyServer implements IRegeditServer {

    Logger logger = LoggerFactory.getLogger(DefaultNettyServer.class);

    int port = 8086;

    private final MessageHandler handler = new MessageHandler();

    ConcurrentSet concurrentSet = new ConcurrentSet();
    final IdleStateCheckTrigger idleStateCheckTrigger = new IdleStateCheckTrigger();

    private final ChannelGroup logchannels = new DefaultChannelGroup("logchannels", GlobalEventExecutor.INSTANCE);
    private static final AttributeKey<ConcurrentSet<RegisterMeta>> S_PUBLISH_KEY =
            AttributeKey.valueOf("server.logchannel");

    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public DefaultNettyServer(int port) {
        this.port = port;
    }

    public void startRegistryServer() {

    }


    public ChannelFuture bind() {

        ThreadFactory bossFactory = bossThreadFactory("acceptor.boss");
        ThreadFactory workerFactory = workerThreadFactory("acceptor.worker");


        boss = new NioEventLoopGroup(2, bossFactory);
        worker = new NioEventLoopGroup(4, workerFactory);

        bootstrap = new ServerBootstrap().group(boss, worker);
        bootstrap.channelFactory(SocketChannelProvider.JAVA_NIO_ACCEPTOR);

        bootstrap.childHandler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateCheckTrigger,
                        new ProtostuffDecoder(Message.class),
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
    @ChannelHandler.Sharable
    class MessageHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Channel ch = ctx.channel();
            if(msg instanceof Message){
                Message obj = (Message)msg;
                switch (obj.getType()){
                    case REGEDIT:
                        System.out.println(obj.getRegeditMeta().toString());
                        regeditLog(ch,obj.getRegeditMeta());
                        break;
                    case CONTENT:
                        System.out.println(obj.toString());
                        break;
                    case DESTROY:
                        unregeditLog(ch,obj.getRegeditMeta());
                        break;
                    case HEARTBEAT:
                        break;
                        default:
                }
            }
        }



        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            Channel ch = ctx.channel();

            // 取消之前发布的所有服务
            ConcurrentSet<RegisterMeta> registerMetaSet = ch.attr(S_PUBLISH_KEY).get();

            if (registerMetaSet == null || registerMetaSet.isEmpty()) {
                return;
            }

            RegisterMeta.Address address = null;
            for (RegisterMeta meta : registerMetaSet) {
                if (address == null) {
                    address = meta.getAddress();
                }
                regeditLog(ch,meta);
            }

            if (address != null) {
                logger.info("OfflineNotice on {}.", address);
            }


        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            Channel ch = ctx.channel();
            ChannelConfig config = ch.config();

            // 高水位线: ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK
            // 低水位线: ChannelOption.WRITE_BUFFER_LOW_WATER_MARK
            if (!ch.isWritable()) {
                // 当前channel的缓冲区(OutboundBuffer)大小超过了WRITE_BUFFER_HIGH_WATER_MARK
                if (logger.isWarnEnabled()) {
                    System.out.println("{} is not writable, high water mask: {}, the number of flushed entries that are not written yet: {}.");
                    logger.warn("{} is not writable, high water mask: {}, the number of flushed entries that are not written yet: {}.",
                            ch, config.getWriteBufferHighWaterMark(), ch.unsafe().outboundBuffer().size());
                }
                config.setAutoRead(false);
            } else {
                // 曾经高于高水位线的OutboundBuffer现在已经低于WRITE_BUFFER_LOW_WATER_MARK了
                if (logger.isWarnEnabled()) {
                    System.out.println("{} is not writable, high water mask: {}, the number of flushed entries that are not written yet: {}.");

                    logger.warn("{} is writable(rehabilitate), low water mask: {}, the number of flushed entries that are not written yet: {}.",
                            ch, config.getWriteBufferLowWaterMark(), ch.unsafe().outboundBuffer().size());
                }
                config.setAutoRead(true);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            Channel ch = ctx.channel();

        }

        //将通道注册到日志中心
        private void regeditLog(Channel channel,RegisterMeta obj){
            //将客户端信息放入到队列属性中
            Attribute<ConcurrentSet<RegisterMeta>> attr = channel.attr(S_PUBLISH_KEY);
            ConcurrentSet<RegisterMeta> serviceMetaSet = attr.get();
            if (serviceMetaSet == null) {
                ConcurrentSet<RegisterMeta> newServiceMetaSet = new ConcurrentSet<>();
                serviceMetaSet = attr.setIfAbsent(newServiceMetaSet);
                if (serviceMetaSet == null) {
                    serviceMetaSet = newServiceMetaSet;
                }
            }

            serviceMetaSet.add(obj);

            //将队列结合
            logchannels.add(channel);
        }
        //卸载日志通道
        private void unregeditLog(Channel channel,RegisterMeta obj){
            Attribute<ConcurrentSet<RegisterMeta>> attr = channel.attr(S_PUBLISH_KEY);
            ConcurrentSet<RegisterMeta> registerMetaSet = attr.get();
            if (registerMetaSet == null) {
                ConcurrentSet<RegisterMeta> newRegisterMetaSet = new ConcurrentSet<>();
                registerMetaSet = attr.setIfAbsent(newRegisterMetaSet);
                if (registerMetaSet == null) {
                    registerMetaSet = newRegisterMetaSet;
                }
            }
            registerMetaSet.remove(obj);
        }
    }
}