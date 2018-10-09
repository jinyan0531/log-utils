package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.IChannel;
import com.dy.components.logs.api.communication.RegisterMeta;
import com.dy.components.logs.api.communication.netty.DefaultNettyRegedit;

public class Client extends DefaultNettyRegedit {


    @Override
    public IChannel getChannel() {
        return null;
    }

    public static void main(String[] s){

        Client client = new Client();
        client.doRegedit("127.0.0.1",12345);
        client.init();
        client.connect();


    }
}
