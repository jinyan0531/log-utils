package com.dy.components.logs.collect.server.rabbitmq;

import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.collect.server.IRegeditServer;
import com.dy.components.logs.utils.ProtostuffUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class DefaultRabbitServer implements IRegeditServer {
    ConnectionFactory factory = new ConnectionFactory();

    String host;
    int port;
    String user;
    String password;

    int DEFAULT_MAX_CHANNEL = 1;

    int DEFAULT_POOL_SIZE = 2;

    Connection connection;

    Channel channel;

    public DefaultRabbitServer(String host,int port,String user,String password){
        this.host=host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    @Override
    public void startRegistryServer() {
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setRequestedChannelMax(DEFAULT_MAX_CHANNEL);
        factory.setPassword(password);
        // 指定线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(DEFAULT_POOL_SIZE, 5, 2, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        factory.setSharedExecutor(executor);
        factory.setAutomaticRecoveryEnabled(true); //设置网络异常重连
        factory.setNetworkRecoveryInterval(2000);
        factory.setTopologyRecoveryEnabled(true);//设置重新声明交换器，队列等信息。
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare("DEFAULT_RABBIT_QUEUE", true, false, false, null);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        ConsumerHandler consumerHandler = new ConsumerHandler();
        ConsumerThread consumerThread = new ConsumerThread(consumerHandler,channel);
        consumerThread.start();
       // channel.basicConsume()

    }

    @Override
    public List<String> listSubscriberAddresses() {
        return null;
    }

    @Override
    public List<String> listAddressesByService(String group, String serviceProviderName, String version) {
        return null;
    }

    class ConsumerThread extends Thread{
        ConsumerHandler consumerHandler;
        Channel channel;
        public ConsumerThread(final ConsumerHandler consumerHandler,final Channel channel){
            this.consumerHandler = consumerHandler;
            this.channel = channel;
        }

        @Override
        public void run() {
            try {
                channel.basicConsume("DEFAULT_RABBIT_QUEUE", false, consumerHandler);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    class ConsumerHandler implements Consumer{

        @Override
        public void handleConsumeOk(String s) {

        }

        @Override
        public void handleCancelOk(String s) {

        }

        @Override
        public void handleCancel(String s) throws IOException {

        }

        @Override
        public void handleShutdownSignal(String s, ShutdownSignalException e) {

        }

        @Override
        public void handleRecoverOk(String s) {

        }

        @Override
        public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
            System.out.println(ProtostuffUtil.deserializer(bytes, Message.class).toString());
        }
    }
}
