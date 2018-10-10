package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.communication.netty.DefaultNettyRegedit;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

public class Client extends DefaultNettyRegedit {



    public static void main(String[] s){

        Client client = new Client();
        client.doRegedit("127.0.0.1",12345);
        client.connect();
        AbstractChannel channel = client.getChannel();
        DefaultCollectLog defaultCollectLog = new DefaultCollectLog("ssf",null,null,null);
        channel.send(defaultCollectLog);


    }
}
