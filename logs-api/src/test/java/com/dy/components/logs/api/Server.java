package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.netty.DefaultNettyServer;

import java.util.List;

public class Server extends DefaultNettyServer {
    public Server(int port) {
        super(port);
    }

    @Override
    public List<String> listSubscriberAddresses() {
        return null;
    }

    @Override
    public List<String> listAddressesByService(String group, String serviceProviderName, String version) {
        return null;
    }

    public static void main(String[] s){

        Server server = new Server(12345);
        server.bind();

    }
}
