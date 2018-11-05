package com.dy.components.logs.api;

import com.dy.components.logs.collect.server.netty.DefaultNettyServer;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;

public  abstract class Server extends DefaultNettyServer {
    public Server(int port) {
        super(port);
    }

    @Override
    public void startRegistryServer(RestHighLevelClient client) {
       // super.startRegistryServer(client);
    }

    @Override
    public List<String> listSubscriberAddresses() {
        return null;
    }

    @Override
    public List<String> listAddressesByService(String group, String serviceProviderName, String version) {
        return null;
    }
}
