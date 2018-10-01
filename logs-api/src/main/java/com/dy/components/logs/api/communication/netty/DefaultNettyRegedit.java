package com.dy.components.logs.api.communication.netty;

import com.dy.components.logs.api.communication.IChannel;
import com.dy.components.logs.api.communication.IRegedit;
import com.dy.components.logs.api.communication.RegeditMeta;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.utils.NamedThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


public abstract class DefaultNettyRegedit implements IRegedit {

    Logger logger = LoggerFactory.getLogger(DefaultNettyRegedit.class);
    /**
     * 地址
     */
     String host;

    /**
     * 端口
     */
    int port;

    /**
     * 组队
     */
    String group;

    /**
     * 服务名
     */
    String serviceProviderName;

    /**
     * 版本
     */
    String version;

    Class<? extends DefaultCollectLog> byteClazz;

    public DefaultNettyRegedit(Class<? extends DefaultCollectLog> clazz){
        byteClazz = clazz;
    }

    String dongyuConnector = "dongyu.connector";
    protected final HashedWheelTimer timer = new HashedWheelTimer(new NamedThreadFactory("connector.timer", true));

    private ChannelFuture future;
    // 每个Client只保留一个有效channel
    private volatile Channel channel;

    private Bootstrap bootstrap;
    private EventLoopGroup worker;
    private int nWorkers;
    final IdleStateCheckTrigger idleStateCheckTrigger = new IdleStateCheckTrigger();
    RegeditMeta regeditMeta;
    private final MessageHandler handler = new MessageHandler(byteClazz);
    public RegeditMeta doRegedit(String host, int port,String serviceProviderName) {
        return doRegedit(host,port,serviceProviderName,null,null);
    }


    public RegeditMeta doRegedit(String host, int port,String serviceProviderName,String group,String version) {
        regeditMeta = new RegeditMeta();
        regeditMeta.setGroup(group);
        regeditMeta.setHost(host);
        regeditMeta.setPort(port);
        regeditMeta.setServiceProviderName(serviceProviderName);
        regeditMeta.setVersion(version);
        return regeditMeta;
    }

    public void init(){
        ThreadFactory workerFactory = workerThreadFactory(dongyuConnector);
        worker = new NioEventLoopGroup(3,workerFactory);
        bootstrap = new Bootstrap().group(worker);
    }


    protected ThreadFactory workerThreadFactory(String name) {
       return    new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }


    @Override
    public void setReconnect(boolean reconnect) {

    }


    public  void connect(){
        if(regeditMeta==null){
            logger.error("出现异常");
            return;
        }

        final Bootstrap boot = bootstrap;

        boot.group(worker).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO));


        // 重连状态检测
        final IdleStateCheckHandler watchdog = new IdleStateCheckHandler(boot, timer, port,host, true) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        this,
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateCheckTrigger,
                        new ProtostuffDecoder(byteClazz),
                        new ProtostuffEncoder(byteClazz),
                        handler
                };
            }
        };


        //进行连接
        try {
            synchronized (boot) {
                boot.handler(new ChannelInitializer<Channel>() {

                    //初始化channel
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(watchdog.handlers());
                    }
                });

                future = boot.connect(host,port);
            }
            channel = future.channel();
            // 以下代码在synchronized同步块外面是安全的
            future.sync();
        } catch (Throwable t) { }
    }

    @ChannelHandler.Sharable
    class MessageHandler<T extends DefaultCollectLog> extends ChannelInboundHandlerAdapter {

        Class<? extends DefaultCollectLog> logClass;

        public MessageHandler(Class<? extends DefaultCollectLog> clazz){
                logClass = clazz;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }

    }
}
