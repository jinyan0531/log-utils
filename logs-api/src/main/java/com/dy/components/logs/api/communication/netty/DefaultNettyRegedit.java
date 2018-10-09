package com.dy.components.logs.api.communication.netty;

import com.dy.components.logs.api.communication.IRegedit;
import com.dy.components.logs.api.communication.RegisterMeta;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.api.protocol.ProtocolEnum;
import com.dy.components.logs.utils.NamedThreadFactory;
import com.dy.components.logs.utils.SocketChannelProvider;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
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




    String dongyuConnector = "dongyu.connector";
    protected final HashedWheelTimer timer = new HashedWheelTimer(new NamedThreadFactory("connector.timer", true));

    private ChannelFuture future;
    // 每个Client只保留一个有效channel
    private volatile Channel channel;

    private Bootstrap bootstrap;
    private EventLoopGroup worker;
    private int nWorkers = 2;
    final IdleStateCheckTrigger idleStateCheckTrigger = new IdleStateCheckTrigger();
    RegisterMeta regeditMeta;
    private final MessageHandler handler = new MessageHandler();
    public RegisterMeta doRegedit(String host, int port,String serviceProviderName) {
        return doRegedit(host,port,serviceProviderName,null,null);
    }
    public RegisterMeta doRegedit(String host, int port) {
        return doRegedit(host,port,null,null,null);
    }

    public RegisterMeta doRegedit(String host, int port, String serviceProviderName, String group, String version) {
        regeditMeta = new RegisterMeta();

        regeditMeta.getServiceMeta().setGroup(group);
        regeditMeta.setHost(host);
        regeditMeta.setPort(port);
        regeditMeta.setServiceProviderName(serviceProviderName);
        regeditMeta.setVersion(version);
        return regeditMeta;
    }

    public void init(){
        ThreadFactory workerFactory = workerThreadFactory(dongyuConnector);
        worker = new NioEventLoopGroup(nWorkers,workerFactory);
        bootstrap = new Bootstrap().group(worker);
        bootstrap.channelFactory(SocketChannelProvider.JAVA_NIO_ACCEPTOR);

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



        // 重连状态检测
        final IdleStateCheckHandler watchdog = new IdleStateCheckHandler(boot, timer, regeditMeta.getPort(),regeditMeta.getHost(), true) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateCheckTrigger,
                        new ProtostuffDecoder(),
                        new ProtostuffEncoder(),
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

                future = boot.connect(regeditMeta.getHost(),regeditMeta.getPort());
            }
            channel = future.channel();
            // 以下代码在synchronized同步块外面是安全的
            future.sync();
        } catch (Throwable t) { t.printStackTrace();}
    }

    @ChannelHandler.Sharable
    class MessageHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        }

        /**
         * 连接时创建进程第一个日志,发送一条注册信息
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            Message communiObject = new Message();
            communiObject.setType(ProtocolEnum.REGEDIT);
            ctx.writeAndFlush("sss");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }

    }


}
