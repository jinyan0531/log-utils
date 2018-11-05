package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.communication.RegisterMeta;
import com.dy.components.logs.api.communication.netty.DefaultNettyRegediter;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.protocol.Message;

public class Client extends DefaultNettyRegediter {



    public static void main(String[] s){

//        Client client = new Client();
//        client.doRegedit("127.0.0.1",12345);
//        client.connect();
//        AbstractChannel channel = client.getChannel();
//        Message defaultCollectLog = new Message("ssf",null,null,null);
//        channel.send(defaultCollectLog);


    }


    @Override
    public RegisterMeta getRegisterMeta() {
        return null;
    }
}
