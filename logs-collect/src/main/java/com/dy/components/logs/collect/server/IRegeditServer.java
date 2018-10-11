package com.dy.components.logs.collect.server;

import java.util.List;

public interface IRegeditServer {

    void startRegistryServer();


    List<String> listSubscriberAddresses();


    List<String> listAddressesByService(String group, String serviceProviderName, String version);



}
