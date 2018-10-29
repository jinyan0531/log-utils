package com.dy.components.logs.collect.server;

import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;

public interface IRegeditServer {

    void startRegistryServer(RestHighLevelClient client);


    List<String> listSubscriberAddresses();


    List<String> listAddressesByService(String group, String serviceProviderName, String version);



}
