package com.dy.components.logs.api.communication.netty;

import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.communication.IRegediter;
import com.dy.components.logs.api.communication.RegisterMeta;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.api.protocol.ProtocolEnum;
import com.dy.components.logs.utils.NamedThreadFactory;
import com.dy.components.logs.utils.ProtostuffDecoder;
import com.dy.components.logs.utils.ProtostuffEncoder;
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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


public abstract class DefaultNettyRegediter implements IRegediter {

    Logger logger = LoggerFactory.getLogger(DefaultNettyRegediter.class);

    int port= 0;

    String ip = null;

    String host = null;


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
    public void doRegedit(String ip, int port,String serviceProviderName) {
         doRegedit(ip,port,serviceProviderName,null,null);
    }
    public void doRegedit(String ip, int port,String user,String password) {
         doRegedit(ip,port,null,null,null);
    }

    public void doRegedit(String ip, int port, String serviceProviderName, String group, String version) {
        this.ip = ip;
        this.port = port;

        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        host = addr.getHostAddress().toString(); //获取本机ip
        regeditMeta = new RegisterMeta();
        regeditMeta.getServiceMeta().setGroup(group);
    }



    public AbstractChannel getChannel(){
        NettyChannel nettyChannel = new NettyChannel(channel);
        return nettyChannel;
    }

    protected ThreadFactory workerThreadFactory(String name) {
       return    new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }


    @Override
    public void setReconnect(boolean reconnect) {

    }


    public  void connect(){




        EventLoopGroup group = new NioEventLoopGroup();
        ThreadFactory workerFactory = workerThreadFactory(dongyuConnector);
        worker = new NioEventLoopGroup(nWorkers,workerFactory);
        bootstrap = new Bootstrap().group(worker);
        bootstrap.channel(NioSocketChannel.class);

        if(regeditMeta==null){
            logger.error("出现异常");
            return;
        }

        final Bootstrap boot = bootstrap;



        // 重连状态检测
        final IdleStateCheckHandler watchdog = new IdleStateCheckHandler(boot, timer, port,ip, true) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateCheckTrigger,
                        new ProtostuffDecoder(Message.class),
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

                future =  boot.remoteAddress(ip,port).connect();
            }
            // 以下代码在synchronized同步块外面是安全的
            future.sync();
            channel = future.channel();

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
            communiObject.setRegeditMeta(regeditMeta);
            ctx.writeAndFlush(communiObject);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }

    }


}
