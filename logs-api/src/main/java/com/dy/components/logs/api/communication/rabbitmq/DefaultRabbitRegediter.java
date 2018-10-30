package com.dy.components.logs.api.communication.rabbitmq;

import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.communication.IRegediter;
import com.dy.components.logs.api.communication.RegisterMeta;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public   class DefaultRabbitRegediter implements IRegediter {

    ConnectionFactory factory = new ConnectionFactory();

    int DEFAULT_MAX_CHANNEL = 20;

    int DEFAULT_POOL_SIZE = 5;

    RegisterMeta registerMeta = new RegisterMeta();

    Channel channel;

    Connection connection;
    @Override
    public void doRegedit(String host, int port,String user,String password) {

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setRequestedChannelMax(DEFAULT_MAX_CHANNEL);
        factory.setPassword(password);
        // 指定线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(DEFAULT_POOL_SIZE, 10, 2, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        factory.setSharedExecutor(executor);
        factory.setAutomaticRecoveryEnabled(true); //设置网络异常重连
        factory.setNetworkRecoveryInterval(2000);
        factory.setTopologyRecoveryEnabled(true);//设置重新声明交换器，队列等信息。

    }


    @Override
    public AbstractChannel getChannel() {
        if(connection!=null){
            try {
               channel = connection.createChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(channel!=null)
            return new RabbitChannel(channel);
        else
            return null;
    }

    @Override
    public void connect() {
        try {
            connection =  factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setReconnect(boolean reconnect) {

    }

    public RegisterMeta getRegisterMeta() {
        return registerMeta;
    }
}
