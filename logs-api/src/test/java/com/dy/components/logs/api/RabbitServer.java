package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.rabbitmq.DefaultRabbitServer;

public class RabbitServer extends DefaultRabbitServer {
    public RabbitServer(String host, int port, String user, String password) {
        super(host, port, user, password);
    }

    public static void main(String[] s){
        RabbitServer rabbitServer = new RabbitServer("192.168.20.51",30702,"rabbit","123456");
        rabbitServer.startRegistryServer();
    }
}
