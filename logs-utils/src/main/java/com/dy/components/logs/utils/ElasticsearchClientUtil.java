package com.dy.components.logs.utils;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticsearchClientUtil {
    private Client client;
    private String ip;
    private String port;
    public ElasticsearchClientUtil(String ip,String port) {
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                   .addTransportAddress(new TransportAddress(InetAddress.getByName(ip), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return client;
    }
}
