package com.dy.components.logs.collect.server.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.dy.components.logs.api.communication.RegisterMeta;
import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.collect.es.index.IndexTools;
import com.dy.components.logs.collect.server.IRegeditServer;
import com.dy.components.logs.utils.ProtostuffUtil;
import com.rabbitmq.client.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


    RestHighLevelClient client;

    int DEFAULT_MAX_CHANNEL = 1;

    int DEFAULT_POOL_SIZE = 2;

    Connection connection;
    final static String DEFAULT_RABBIT_QUEUE = "LOGS_DEFAULT_RABBIT_QUEUE";

    Channel channel;

    public DefaultRabbitServer(String host,int port,String user,String password){
        this.host=host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    @Override
    public void startRegistryServer(RestHighLevelClient client) {
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
            this.channel = connection.createChannel();
            this.channel.queueDeclare(DEFAULT_RABBIT_QUEUE, true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        ConsumerHandler consumerHandler = new ConsumerHandler(this.channel,client);
        ConsumerThread consumerThread = new ConsumerThread(consumerHandler,channel);
        consumerThread.start();
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
                this.channel.basicConsume(DEFAULT_RABBIT_QUEUE, false, consumerHandler);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    class ConsumerHandler implements Consumer{

        Channel channel;

        RestHighLevelClient client;

        public ConsumerHandler(Channel channel,RestHighLevelClient client){
            this.channel =channel;
            this.client = client;
        }

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
            try {
                Message message = ProtostuffUtil.deserializer(bytes, Message.class);

                switch (message.getType()){
                    case REGEDIT:
                        LogRegedit(message,client);

                    case CONTENT:

                        LogSave(message,client);

                        System.out.println(message.getContent().getClassName());
                        System.out.println(ProtostuffUtil.deserializer(message.getContent().getCollectLog(),Class.forName(message.getContent().getClassName())));

                }
                this.channel.basicAck(envelope.getDeliveryTag(),false);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


        }
        //日志保存
        private void LogSave(Message message, RestHighLevelClient client) throws ClassNotFoundException {
            IndexTools indexTools = new IndexTools(client);

            IndexRequest indexRequest = indexTools.getIndexRequest(message.getContent().getClassName(),String.valueOf(message.getContent().getIndexId()));
            String json = JSONObject.toJSONString(ProtostuffUtil.deserializer(message.getContent().getCollectLog(),Class.forName(message.getContent().getClassName())));

            indexRequest.source(json, XContentType.JSON);


        }
        //日志接口注册
        private void LogRegedit(Message message, RestHighLevelClient client) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            IndexTools indexTools = new IndexTools(client);


//            Class clazz = Class.forName(message.getContent().getClassName());
//
//            Method method=clazz.getMethod("getXConBuilder");
//
//            XContentBuilder xContentBuilder = (XContentBuilder) method.invoke( ProtostuffUtil.deserializer(message.getContent().getCollectLog(),Class.forName(message.getContent().getClassName())));
//
//
//            indexTools.LogIndexBuilder(xContentBuilder,message.getContent().getClassName(),String.valueOf(message.getContent().getIndexId()));
            RegisterMeta rm = message.getRegeditMeta();
            RegisterMeta.ServiceMeta serviceMeta = rm.getServiceMeta();
            indexTools.LogIndexBuilder(serviceMeta.getxContentBuilder(),serviceMeta.getType(),serviceMeta.getIndex());
        }
    }
}
